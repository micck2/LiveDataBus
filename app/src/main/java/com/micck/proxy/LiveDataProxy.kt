package com.micck.proxy

import androidx.annotation.MainThread
import androidx.lifecycle.*

/**
 * 代理LiveData
 *
 * @author lilin
 * @time on 2021/7/6 10:11 AM
 */
class LiveDataProxy<T>(private val dataEventName: String) : LiveData<T>() {

//region var/val

    private var mData: T? = null
    private var mVersion = 0
    private var mObserverCount = 0

//endregion

//region implement methods

    override fun setValue(value: T) {
        mVersion++
        super.setValue(value)
    }

    override fun postValue(value: T) {
        mVersion++
        super.postValue(value)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        mObserverCount++
        addObserver(owner)
        super.observe(owner, ObserverImpl(this, observer))
    }

    override fun observeForever(observer: Observer<in T>) {
        mObserverCount++
        super.observeForever(ObserverImpl(this, observer))
    }

    override fun removeObserver(observer: Observer<in T>) {
        if (mObserverCount > 0) {
            mObserverCount--
        }
        if (mObserverCount == 0) {
            removeDataEvent()
        }
        super.removeObserver(observer)
    }

//endregion

//region public methods

    fun getVersion() = mVersion

    /**
     * 在主线程去发送数据
     *
     * @param data
     */
    @MainThread
    fun setData(data: T) {
        mData = data
        setValue(data)
    }

    /**
     * 不受线程的限制
     *
     * @param data
     */
    fun postData(data: T) {
        mData = data
        postValue(data)
    }

//endregion

//region private methods

    /**
     * 监听宿主发生销毁事件，主动把livedata移除掉
     * @param owner
     */
    private fun addObserver(owner: LifecycleOwner) {
        owner.lifecycle.addObserver((LifecycleEventObserver { _: LifecycleOwner?, event: Lifecycle.Event ->
            if (event == Lifecycle.Event.ON_DESTROY && mObserverCount == 0) {
                removeDataEvent()
            }
        }))
    }

    private fun removeDataEvent() {
        LiveDataBus.instance.getDataEventMap().remove(dataEventName)
    }

//endregion
}