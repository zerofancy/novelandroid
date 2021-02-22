package top.ntutn.novelrecommend.ui.viewmodel

import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import top.ntutn.commonutil.ClipboardUtil
import top.ntutn.commonutil.DeviceUtil
import top.ntutn.commonutil.MetricsUtil
import top.ntutn.commonutil.showToast
import top.ntutn.novelrecommend.adapter.DebugEntrance
import top.ntutn.novelrecommend.arch.CheckedLiveData
import top.ntutn.novelrecommend.arch.InitedLiveData
import top.ntutn.readview.ReadTestActivity

class DebugEntranceViewModel : ViewModel() {
    private val _debugEntranceList = InitedLiveData<List<DebugEntrance>> { listOf() }
    val debugEntranceList: CheckedLiveData<List<DebugEntrance>> = _debugEntranceList

    fun initData() {
        _debugEntranceList.value = listOf(
            DebugEntrance(title = "小说浏览Demo", "liuhaixin.zero") {
                ReadTestActivity.actionStart(it)
//                ARouter.getInstance().build(ReadTestActivity.PATH).navigation()
            },
            DebugEntrance(title = "获取设备信息", owner = "liuhaixin.zero") { context ->
                val message = """
                        IMEI: ${DeviceUtil.getIMEI()}
                        Android ID: ${DeviceUtil.getAndroidId()}
                        GUID: ${DeviceUtil.getGUID()}
                    """.trimIndent()

                AlertDialog.Builder(context).apply {
                    setTitle("设备信息")
                    setMessage(message).setNeutralButton("复制") { _, _ ->
                        ClipboardUtil.copyToClipboard(message)
                        "已复制到剪贴板".showToast()
                    }
                }.create().show()
            },
            DebugEntrance(title = "主动推送埋点事件", owner = "liuhaixin.zero") {
                "主动推送埋点事件".showToast()
                MetricsUtil.onEvent(
                    "ping", mapOf(
                        "random1" to (0..100).random().toString(),
                        "random2" to (0..100).random().toString()
                    )
                )
                MetricsUtil.push()
            }
        )
    }
}
