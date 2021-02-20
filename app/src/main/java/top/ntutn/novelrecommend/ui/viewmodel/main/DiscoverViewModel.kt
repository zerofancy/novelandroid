package top.ntutn.novelrecommend.ui.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await
import timber.log.Timber
import top.ntutn.commonutil.DeviceUtil
import top.ntutn.novelrecommend.NovelService
import top.ntutn.novelrecommend.arch.CheckedLiveData
import top.ntutn.novelrecommend.arch.InitedLiveData
import top.ntutn.novelrecommend.model.NovelModel
import top.ntutn.novelrecommend.utils.RetrofitUtil

class DiscoverViewModel : ViewModel() {
    private val _novelList =
        InitedLiveData<MutableList<NovelModel>> { mutableListOf() }
    private val _currentPosition = InitedLiveData { 0 }

    val novelList: CheckedLiveData<MutableList<NovelModel>> = _novelList
    val currentPosition: CheckedLiveData<Int> = _currentPosition

    private suspend fun getNovel(): List<NovelModel> {
        return RetrofitUtil.create<NovelService>()
            .getNovel(deviceInfo = DeviceUtil.getDeviceInfoMap())
            .await()
            .map { it.copy(localId = (0..Long.MAX_VALUE).random()) }  //TODO 解决id重复的问题
    }

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
        val currentPosition = _currentPosition.value ?: 0
        val novelCount = _novelList.value.size
        if (currentPosition + 3 >= novelCount) loadMore()
    }

    fun scrollTo(position: Int) {
        _currentPosition.value = position
    }

    fun likeBook(bookId: Long) {

    }

    fun starBook(bookId: Long) {

    }
}