package top.ntutn.novelrecommend.ui.viewmodel

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await
import timber.log.Timber
import top.ntutn.commonui.common.InitedLiveData
import top.ntutn.commonutil.AppUtil
import top.ntutn.commonutil.RetrofitUtil
import top.ntutn.novelrecommend.NovelService
import top.ntutn.novelrecommend.R
import top.ntutn.novelrecommend.model.ChapterModel
import top.ntutn.novelrecommend.model.NovelModel
import top.ntutn.novelrecommend.model.ReadStatus

class NovelReadViewModel : ViewModel() {
    private val gson by lazy { Gson() }
    private val _title = InitedLiveData {
        AppUtil.getApplicationContext().getString(R.string.title_activity_novel_read)
    }
    private val _bookInfo = MutableLiveData<NovelModel?>()
    private val _readStatus = InitedLiveData { ReadStatus() }
    private val _currentChapter = MutableLiveData<ChapterModel>()

    private val cachedChapters = mutableMapOf<Int, ChapterModel>()

    val title: LiveData<String> = _title
    val bookInfo: LiveData<NovelModel?> = _bookInfo
    val readStatus: LiveData<ReadStatus> = _readStatus
    val currentChapter: LiveData<ChapterModel> = _currentChapter

    /**
     * 获取小说信息
     */
    fun fetchNovelInfo(id: Long) {
        viewModelScope.launch {
            val tmpBookInfo = withContext(Dispatchers.IO) {
                try {
                    RetrofitUtil.create<NovelService>().getNovelInfo(id).await()
                } catch (tr: Throwable) {
                    Timber.e(tr)
                    null
                }
            }
            tmpBookInfo?.let {
                _title.value = "《${it.title}》"
                _bookInfo.value = it
                initReadStatus(it.id ?: 0)
                fetchChapter(it.id ?: 0, _readStatus.value.currentChapter)?.let {
                    _currentChapter.value = it
                }
            }
        }
    }

    private suspend fun initReadStatus(currentBookNumber: Long) {
        val readStatus = withContext(Dispatchers.IO) {
            val sp = AppUtil.getApplicationContext()
                .getSharedPreferences("read_status", Context.MODE_PRIVATE)
            val jsonString = sp.getString(currentBookNumber.toString(), "{}")
            val status = try {
                gson.fromJson(jsonString, ReadStatus::class.java)
            } catch (tr: Throwable) {
                null
            }
            status
        }
        readStatus?.let {
            _readStatus.value = it
        }
    }

    private suspend fun saveReadStatus(currentBookNumber: Long) {
        withContext(Dispatchers.IO) {
            val sp = AppUtil.getApplicationContext()
                .getSharedPreferences("read_status", Context.MODE_PRIVATE)
            val jsonString = gson.toJson(_readStatus.value)
            sp.edit {
                putString("read_status", jsonString)
            }
        }
    }

    private suspend fun fetchChapter(currentBookNumber: Long, chapterNumber: Int): ChapterModel? {
        if (cachedChapters.containsKey(chapterNumber)) {
            return cachedChapters[chapterNumber]
        }
        val chapter = withContext(Dispatchers.IO) {
            try {
                RetrofitUtil.create<NovelService>().getChapter(currentBookNumber, chapterNumber)
                    .await()
            } catch (tr: Throwable) {
                Timber.e(tr)
                null
            }
        }
        chapter?.let {
            cachedChapters[chapterNumber] = it
        }
        return chapter
    }

    fun goPerviousChapter() {

    }

    fun goNextChapter() {

    }
}