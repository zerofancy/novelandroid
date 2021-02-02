package top.ntutn.novelrecommend.ui.event

data class ConfigEditDialogCloseEvent(val saveResult: Boolean, val key: String?, val value: String?)
