package top.ntutn.novelrecommend.utils

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.EventBusException
import timber.log.Timber

object EventBusWrapper {
    fun post(obj: Any?) {
        EventBus.getDefault().post(obj)
    }

    fun postSticky(obj: Any?) {
        EventBus.getDefault().postSticky(obj)
    }

    fun register(subscriber: Any?) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            try {
                EventBus.getDefault().register(subscriber)
            } catch (e: EventBusException) {
                Timber.e( e,"EventBus register error")
            }
        }
    }

    fun unregister(subscriber: Any?) {
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber)
        }
    }

    fun isRegistered(subscriber: Any?): Boolean {
        return EventBus.getDefault().isRegistered(subscriber)
    }

    fun cancelEventDelivery(event: Any?) {
        EventBus.getDefault().cancelEventDelivery(event)
    }
}