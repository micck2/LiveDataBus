package com.micck.proxy

import java.util.concurrent.ConcurrentHashMap

/**
 * @author lilin
 * @time on 2021/7/6 10:40 AM
 */
class LiveDataBus {

    companion object {
        val instance = SingletonHolder.holder
    }
//region var/val

    private val mDataEventMap: ConcurrentHashMap<String, LiveDataProxy<*>> = ConcurrentHashMap()

//endregion

//region implement methods

//endregion

//region public methods

    fun getDataEventMap(): ConcurrentHashMap<String, LiveDataProxy<*>> {
        return mDataEventMap
    }

    fun <T> with(dataEventName: String?): LiveDataProxy<T> {
        if (dataEventName.isNullOrEmpty()) {
            throw NullPointerException("dataEventName can not be null")
        }

        var liveDataProxy = mDataEventMap[dataEventName]
        if (liveDataProxy == null) {
            liveDataProxy = LiveDataProxy<T>(dataEventName)
            mDataEventMap[dataEventName] = liveDataProxy
        }

        return liveDataProxy as LiveDataProxy<T>
    }

//endregion

//region private methods

    private object SingletonHolder {
        val holder = LiveDataBus()
    }

//endregion
}