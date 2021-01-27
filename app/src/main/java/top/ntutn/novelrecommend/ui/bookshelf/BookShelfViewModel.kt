package top.ntutn.novelrecommend.ui.bookshelf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookShelfViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "书架"
    }
    val text: LiveData<String> = _text
}