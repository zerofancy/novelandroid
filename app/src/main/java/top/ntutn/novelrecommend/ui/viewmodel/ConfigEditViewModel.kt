package top.ntutn.novelrecommend.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.ntutn.zeroconfigutil.ZeroConfigHelper

class ConfigEditViewModel : ViewModel() {
    private val _key = MutableLiveData<String>()
    private val _value = MutableLiveData<String>()

    val key: LiveData<String> = _key
    val value: LiveData<String> = _value

    private suspend fun prepareData(key: String): String? {
        return withContext(Dispatchers.IO) {
            ZeroConfigHelper.readRawConfig(key)
        }
    }

    fun setData(key: String) {
        if (_key.value == key) return
        viewModelScope.launch {
            _key.value = key
            _value.value = withContext(Dispatchers.IO) { prepareData(key) }
        }
    }

    fun editValue(value: String) {
        if (_value.value != value) _value.value = value
    }
}