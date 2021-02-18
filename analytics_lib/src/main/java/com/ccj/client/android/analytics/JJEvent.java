package com.ccj.client.android.analytics;

import com.ccj.client.android.analytics.thread.JJPoolExecutor;

import java.util.Map;
import java.util.concurrent.FutureTask;


/**
 * 统计入口
 * Created by chenchangjun on 18/2/8.
 */
public final class JJEvent {


    /**
     * 点击事件
     *
     * @param el event label 事件标签
     */
    public static void event(String el) {
        event(el, null);
    }


    /**
     * 点击事件
     *
     * @param el  event label 事件标签
     * @param ecp event custom Parameters 自定义参数Map<key,value>
     */
    public static void event(String el, Map ecp) {

        try {
            EventTask eventTask = new EventTask(el, ecp);
            JJPoolExecutor.getInstance().execute(new FutureTask<>(eventTask, null));
        } catch (Exception e) {
            e.printStackTrace();
            ELogger.logWrite(EConstant.TAG, "event error " + e.getMessage());
        }
    }


}
