package com.ccj.client.android.analytics;

import com.ccj.client.android.analytics.bean.EventBean;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public class EventTask implements Runnable {

    private String el;
    private Map ecp;

    public EventTask(String el, Map ecp) {
        this.el = el;
        this.ecp = ecp;
    }

    @Override
    public void run() {

        if (!JJEventManager.hasInit) {
            ELogger.logError(EConstant.TAG, "please init JJEventManager!");
            return;
        }

        if (EConstant.SWITCH_OFF) {
            ELogger.logWrite(EConstant.TAG, "the sdk is SWITCH_OFF");
            return;
        }

        try {

            EventBean bean = EventDecorator.generateEventBean(el, ecp);

            if (bean == null) {
                ELogger.logWrite(EConstant.TAG, " event bean == null");
                return;
            }

            ELogger.logWrite(EConstant.TAG, " event " + bean.toString());


            EDBHelper.addEventData(bean);



            EventDecorator.pushEventByNum();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @NotNull
    @Override
    public String toString() {
        return "EventTask{" +
                ", el='" + el + '\'' +
                ", ecp=" + ecp +
                '}';
    }
}
