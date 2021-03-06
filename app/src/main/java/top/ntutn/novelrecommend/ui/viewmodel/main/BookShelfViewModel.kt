package top.ntutn.novelrecommend.ui.viewmodel.main

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.ntutn.commonutil.AppUtil
import top.ntutn.novelrecommend.arch.CheckedLiveData
import top.ntutn.novelrecommend.arch.InitedLiveData
import top.ntutn.novelrecommend.model.NovelModel
import java.lang.reflect.Type

class BookShelfViewModel : ViewModel() {
    private val gson = Gson()
    private val _books = InitedLiveData<MutableList<NovelModel>> { mutableListOf() }

    val books: CheckedLiveData<MutableList<NovelModel>> = _books

    fun addBook(book: NovelModel) {
        viewModelScope.launch {
            val result: Pair<Boolean, MutableList<NovelModel>> = withContext(Dispatchers.IO) {
                val isExists = (_books.value.find { it.id == book.id }) != null
                if (isExists) {
                    return@withContext false to mutableListOf()
                }
                val result = mutableListOf(book)
                result.addAll(_books.value)
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
                val targetBook = _books.value.toMutableList().find { it.id == book.id }
                val result = _books.value.toMutableList()
                targetBook?.let { result.remove(it) }
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

    private fun loadBookShelfFromLocal(): MutableList<NovelModel>? {
        val json =
            AppUtil.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                .getString(
                    SP_KEY, "{}"
                )
        val listType: Type = object : TypeToken<List<NovelModel>?>() {}.type
        return try {
            gson.fromJson(json, listType)
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    companion object {
        const val SP_NAME = "bookShelf"
        const val SP_KEY = "json"
    }
}