package top.ntutn.novelrecommend.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.ntutn.novelrecommend.arch.CheckedLiveData
import top.ntutn.novelrecommend.arch.InitedLiveData
import top.ntutn.novelrecommend.model.DebugConfigListModel
import top.ntutn.zeroconfigutil.ZeroConfigHelper

class DebugConfigViewModel : ViewModel() {
    private val _configList = InitedLiveData<MutableList<DebugConfigListModel>> { mutableListOf() }

    val configList: CheckedLiveData<MutableList<DebugConfigListModel>> = _configList

    private suspend fun getList(): MutableList<DebugConfigListModel> {
        return withContext(Dispatchers.IO) {
            ZeroConfigHelper.getAllDefinedConfigs()
                .map { DebugConfigListModel(DebugConfigListModel.TYPE_CONFIG_ITEM, "默认分组", it) }
                .toMutableList()
        }
    }

    fun initList() {
        viewModelScope.launch { _configList.value = getList() }
    }

    fun updateConfig(key: String, value: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                ZeroConfigHelper.saveRawConfig(key, value)
            }
        }
    }
}