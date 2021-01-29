package top.ntutn.novelrecommend.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import top.ntutn.novelrecommend.utils.ZeroConfigHelper

class DebugConfigViewModel : ViewModel() {
    private val _configList = MutableLiveData<List<String>>()

    val configList: LiveData<List<String>> = _configList

    private fun getList(): List<String> {
        return ZeroConfigHelper.getAllDefinedConfigs().map { it.simpleName }
    }

    fun initList() {
        _configList.value = getList()
    }
}