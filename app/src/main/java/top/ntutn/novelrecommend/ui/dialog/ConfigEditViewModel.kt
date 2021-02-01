package top.ntutn.novelrecommend.ui.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConfigEditViewModel : ViewModel() {
    private val _key = MutableLiveData<String>()
    private val _value = MutableLiveData<String>()

    val key: LiveData<String> = _key
    val value: LiveData<String> = _value

    fun setData(key: String, value: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _key.value = key
                _value.value = value
            }
        }
    }
}