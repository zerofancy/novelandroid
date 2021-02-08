package top.ntutn.novelrecommend.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await
import top.ntutn.novelrecommend.NovelService
import top.ntutn.novelrecommend.model.NovelModel
import top.ntutn.novelrecommend.utils.RetrofitUtil

class DiscoverViewModel : ViewModel() {
    private val _novelList = MutableLiveData<List<NovelModel>>().apply { value = listOf() }
    private val _currentPosition = MutableLiveData<Int>()

    val novelList: LiveData<List<NovelModel>> = _novelList
    val currentPosition: LiveData<Int> = _currentPosition

    private suspend fun getNovel(): List<NovelModel> {
        return RetrofitUtil.create<NovelService>().getNovel().await()
            .map { it.copy(localId = (0..Long.MAX_VALUE).random()) }  //TODO 解决id重复的问题
    }

    fun loadMore() {
        viewModelScope.launch {
            _novelList.value = withContext(Dispatchers.IO) {
                try {
                    (_novelList.value ?: listOf()).plus(getNovel())
                } catch (e: Exception) {
                    _novelList.value
                }
            }
        }
    }

    fun tryLoadMore() {
        val currentPosition = _currentPosition.value ?: 0
        val novelCount = _novelList.value?.size ?: 0
        if (currentPosition + 3 >= novelCount) loadMore()
    }

    fun scrollTo(position: Int) {
        _currentPosition.value = position
    }
}