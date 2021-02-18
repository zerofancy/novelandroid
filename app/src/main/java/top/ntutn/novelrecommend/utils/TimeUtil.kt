package top.ntutn.novelrecommend.utils

/**
 * 计时统计工具类
 * 来自 https://www.jianshu.com/p/59a2ca7df681
 */
object TimeUtil {
    private val sCalTimeMap: HashMap<String, Long> = HashMap()
    const val COLD_START = "cold_start"
    const val HOT_START = "hot_start"
    var sColdStartTime: Long = 0

    /**
     * 记录某个事件的开始时间
     * @param key 事件名称
     */
    fun beginTimeCalculate(key: String) {
        val currentTime = System.currentTimeMillis()
        sCalTimeMap[key] = currentTime
    }

    /**
     * 获取某个事件的运行时间
     *
     * @param key 事件名称
     * @return 返回某个事件的运行时间，调用这个方法之前没有调用 [.beginTimeCalculate] 则返回-1
     */
    fun getTimeCalculate(key: String): Long {
        val currentTime = System.currentTimeMillis()
        val beginTime = sCalTimeMap[key]
        return if (beginTime == null) {
            -1
        } else {
            sCalTimeMap.remove(key)
            currentTime - beginTime
        }
    }

    /**
     * 清除某个时间运行时间计时
     *
     * @param key 事件名称
     */
    fun clearTimeCalculate(key: String) {
        sCalTimeMap.remove(key)
    }

    /**
     * 清除启动时间计时
     */
    fun clearStartTimeCalculate() {
        clearTimeCalculate(HOT_START)
        clearTimeCalculate(COLD_START)
        sColdStartTime = 0
    }
}