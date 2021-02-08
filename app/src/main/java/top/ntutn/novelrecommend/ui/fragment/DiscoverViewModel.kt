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

    private val _text = MutableLiveData<String>().apply {
        value = "发现"
    }
    private val _novelList = MutableLiveData<List<NovelModel>>()

    val text: LiveData<String> = _text
    val novelList: LiveData<List<NovelModel>> = _novelList

    private suspend fun getNovel(): List<NovelModel> {
        return RetrofitUtil.create<NovelService>().getNovel().await()
    }

    fun loadMore() {
        viewModelScope.launch {
            _novelList.value = withContext(Dispatchers.IO) {
                try {
                    _novelList.value ?: listOf<NovelModel>().plus(getNovel())
                } catch (e: Exception) {
                    _novelList.value
                }
            }
        }
    }
}