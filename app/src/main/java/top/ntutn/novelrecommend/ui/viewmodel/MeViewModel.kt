package top.ntutn.novelrecommend.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "我的"
    }
    val text: LiveData<String> = _text
}