package top.ntutn.novelrecommend.ui.viewmodel.main

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smile.analytics.MetricsServiceDelegate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await
import timber.log.Timber
import top.ntutn.commonutil.DeviceUtil
import top.ntutn.novelrecommend.NovelService
import top.ntutn.novelrecommend.common.CheckedLiveData
import top.ntutn.novelrecommend.common.InitedLiveData
import top.ntutn.novelrecommend.model.NovelModel
import top.ntutn.commonutil.RetrofitUtil

class DiscoverViewModel : ViewModel() {
    private val _novelList =
        InitedLiveData<MutableList<NovelModel>> { mutableListOf() }
    private val _currentPosition = InitedLiveData { 0 }

    private val _pageCount = InitedLiveData { 1 }
    private val _currentPage = InitedLiveData { 0 }

    val novelList: CheckedLiveData<MutableList<NovelModel>> = _novelList
    val currentPosition: CheckedLiveData<Int> = _currentPosition

    private suspend fun getNovel(): List<NovelModel> {
        return try {
            RetrofitUtil.create<NovelService>()
                .getNovel(deviceInfo = DeviceUtil.getDeviceInfoMap())
                .await()
                .map { it.copy(localId = (0..Long.MAX_VALUE).random()) }  //TODO 解决id重复的问题
        } catch (e: Exception) {
            Timber.e(e, "获取小说失败")
            throw e
        }
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun loadMore() {
        viewModelScope.launch {
            _novelList.value = withContext(Dispatchers.IO) {
                try {
                    _novelList.value.addAll(getNovel())
                } catch (e: Exception) {
                    Timber.e(e, "获取小说失败")
                }
                _novelList.value
            }
        }
    }

    fun tryLoadMore() {
        val currentPosition = _currentPosition.value
        val novelCount = _novelList.value.size
        if (currentPosition + 3 >= novelCount) loadMore()
    }

    fun scrollTo(position: Int) {
        val previousBook = _novelList.value[_currentPosition.value]
        MetricsServiceDelegate.onEvent(
            "switch_book", mapOf(
                "id" to (previousBook.id ?: -1L),
                "liked" to previousBook.isLiked,
                "stared" to previousBook.isStared,
                "page_ount" to _pageCount.value,
                "current_page_index" to _currentPage.value
            )
        )

        _currentPosition.value = position
    }

    fun bookLikedChange(liked: Boolean) {
        _novelList.value[currentPosition.value].isLiked = liked
        _novelList.value = _novelList.value
    }

    fun bookStaredChange(stared: Boolean) {
        _novelList.value[currentPosition.value].isStared = stared
        _novelList.value = _novelList.value
    }

    fun switchPage(pageCount: Int, currentPage: Int) {
        _pageCount.value = pageCount
        _currentPage.value = currentPage
    }
}