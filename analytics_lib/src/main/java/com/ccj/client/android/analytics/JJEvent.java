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
     * @param ec event category 事件类别
     * @param ea event action 事件操作
     * @param el event label 事件标签
     */
    public static void event(String ec, String ea, String el) {
        event(ec, ea, el, null);
    }


    /**
     * 点击事件
     *
     * @param ec  event category 事件类别
     * @param ea  event action 事件操作
     * @param el  event label 事件标签
     * @param ecp event custom Parameters 自定义参数Map<key,value>
     */
    public static void event(String ec, String ea, String el, Map ecp) {

        try {
            EventTask eventTask = new EventTask(ec, ea, el, ecp);
            JJPoolExecutor.getInstance().execute(new FutureTask<>(eventTask, null));
        } catch (Exception e) {
            e.printStackTrace();
            ELogger.logWrite(EConstant.TAG, "event error " + e.getMessage());
        }
    }


}
