package top.ntutn.novelrecommend.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.ntutn.novelrecommend.model.NovelModel

class BookShelfViewModel : ViewModel() {
    private val _books = MutableLiveData<List<NovelModel>>().apply { value = listOf() }

    val books: LiveData<List<NovelModel>> = _books

    fun addBook(book: NovelModel) {
        // TODO 持久化
        viewModelScope.launch {
            val result: Pair<Boolean, List<NovelModel>?> = withContext(Dispatchers.IO) {
                val isExists = (_books.value?.find { it.id == book.id }) != null
                if (isExists) {
                    return@withContext false to null
                }
                val result = mutableListOf(book)
                _books.value?.let { result.addAll(it) }
                true to result
            }
            if (result.first) {
                _books.value = result.second
            }
        }
    }

    fun removeBook(book: NovelModel) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                val targetBook = _books.value?.toMutableList()?.find { it.id == book.id }
                val result = _books.value?.toMutableList()
                targetBook?.let { result?.remove(it) }
                result
            }
            _books.value = result
        }
    }
}