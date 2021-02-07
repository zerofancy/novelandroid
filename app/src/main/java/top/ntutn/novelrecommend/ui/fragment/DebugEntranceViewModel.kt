package top.ntutn.novelrecommend.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import top.ntutn.novelrecommend.utils.showToast

class DebugEntranceViewModel : ViewModel() {
    private val _debugEntranceList = MutableLiveData<List<DebugEntrance>>()
    val debugEntranceList: LiveData<List<DebugEntrance>> = _debugEntranceList

    fun initData() {
        _debugEntranceList.value = listOf(
            DebugEntrance(title = "小说浏览Demo", "liuhaixin.zero") {
                "应该跳转到Demo".showToast()
            }
        )
    }
}
