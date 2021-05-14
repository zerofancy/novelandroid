package top.ntutn.novelrecommend.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await
import timber.log.Timber
import top.ntutn.commonui.common.InitedLiveData
import top.ntutn.commonutil.AppUtil
import top.ntutn.commonutil.RetrofitUtil
import top.ntutn.commonutil.showToast
import top.ntutn.novelrecommend.NovelService
import top.ntutn.novelrecommend.R
import top.ntutn.novelrecommend.model.NovelModel

class NovelReadViewModel : ViewModel() {
    private val _title = InitedLiveData {
        AppUtil.getApplicationContext().getString(R.string.title_activity_novel_read)
    }
    private val _bookInfo = MutableLiveData<NovelModel?>()

    val title: LiveData<String> = _title
    val bookInfo: LiveData<NovelModel?> = _bookInfo

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
            } ?: kotlin.run {
                "小说信息获取失败，请稍后重试".showToast()
            }
        }
    }
}