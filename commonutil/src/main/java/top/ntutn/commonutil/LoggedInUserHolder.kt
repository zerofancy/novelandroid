package top.ntutn.commonutil

/**
 * 为了全局访问登陆用户信息
 */
object LoggedInUserHolder {
    var user: LoggedInUser? = null
        set(value) {
            field = value
            DeviceUtil.uid = value?.id
        }
}