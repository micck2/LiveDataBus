package com.micck.proxy

import androidx.lifecycle.Observer

/**
 * @author lilin
 * @time on 2021/7/6 10:30 AM
 */
class ObserverImpl<T>(private val liveDataProxy: LiveDataProxy<T>?,
                      private val observer: Observer<in T>?) : Observer<T> {

//region var/val

    private var mLastVersion = 0

//endregion

    init {
        mLastVersion = liveDataProxy?.getVersion() ?: 0
    }

//region implement methods

    override fun onChanged(t: T) {
        if (liveDataProxy == null || observer == null) {
            return
        }

        if (mLastVersion >= liveDataProxy.getVersion()) {
            return
        }

        mLastVersion = liveDataProxy.getVersion()
        observer.onChanged(t)
    }

//endregion

//region public methods

//endregion

//region private methods

//endregion
}