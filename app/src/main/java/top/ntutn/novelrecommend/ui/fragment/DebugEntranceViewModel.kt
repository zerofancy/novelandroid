package top.ntutn.novelrecommend.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import top.ntutn.novelrecommend.adapter.DebugEntrance
import top.ntutn.readview.ReadTestActivity

class DebugEntranceViewModel : ViewModel() {
    private val _debugEntranceList = MutableLiveData<List<DebugEntrance>>()
    val debugEntranceList: LiveData<List<DebugEntrance>> = _debugEntranceList

    fun initData() {
        _debugEntranceList.value = listOf(
            DebugEntrance(title = "小说浏览Demo", "liuhaixin.zero") {
                ARouter.getInstance().build(ReadTestActivity.PATH).navigation()
            }
        )
    }
}
