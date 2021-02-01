package top.ntutn.novelrecommend.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.ntutn.novelrecommend.model.DebugConfigListModel
import top.ntutn.zeroconfigutil.ZeroConfigHelper

class DebugConfigViewModel : ViewModel() {
    private val _configList = MutableLiveData<List<DebugConfigListModel>>()

    val configList: LiveData<List<DebugConfigListModel>> = _configList

    private suspend fun getList(): List<DebugConfigListModel> {
        return withContext(Dispatchers.IO) {
            listOf(DebugConfigListModel(DebugConfigListModel.TYPE_SCOPE, "默认分组")).plus(
                ZeroConfigHelper.getAllDefinedConfigs()
                    .map { DebugConfigListModel(DebugConfigListModel.TYPE_CONFIG_ITEM, "默认分组", it) }
            )
        }
    }

    fun initList() {
        viewModelScope.launch { _configList.value = getList() }
    }
}