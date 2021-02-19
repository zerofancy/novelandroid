package top.ntutn.novelrecommend.ui.viewmodel

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.ntutn.commonutil.AppUtil
import top.ntutn.novelrecommend.model.NovelModel
import java.lang.reflect.Type

class BookShelfViewModel : ViewModel() {
    private val gson = Gson()
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
                val resultString = gson.toJson(result)
                AppUtil.getApplicationContext()
                    .getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit {
                        putString(SP_KEY, resultString)
                    }
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
                val resultString = gson.toJson(result)
                AppUtil.getApplicationContext()
                    .getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit {
                        putString(SP_KEY, resultString)
                    }
                result
            }
            _books.value = result
        }
    }

    fun initBookShelf() {
        if (_books.value.isNullOrEmpty()) {
            viewModelScope.launch {
                val result = withContext(Dispatchers.IO) {
                    loadBookShelfFromLocal()
                }
                result?.let {
                    _books.value = it
                }
            }
        }
    }

    private fun loadBookShelfFromLocal(): List<NovelModel>? {
        val json =
            AppUtil.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                .getString(
                    SP_KEY, "{}"
                )
        val listType: Type = object : TypeToken<List<NovelModel>?>() {}.type
        return gson.fromJson(json, listType)
    }

    companion object {
        const val SP_NAME = "bookShelf"
        const val SP_KEY = "json"
    }
}