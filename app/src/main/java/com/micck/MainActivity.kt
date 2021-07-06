package com.micck

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.micck.proxy.LiveDataBus
import com.micck.proxy.LiveDataProxy
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"

        const val TEST_EVENT_NAME = "testEventName"
        const val TEST_FOREVER_EVENT_NAME = "testForeverEventName"
    }

    private var mForeverLiveData: LiveDataProxy<String>? = null

    override fun onDestroy() {
        super.onDestroy()
        mForeverLiveData?.removeObserver(mForeverObserver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initObserver()
        initForeverObserver()
        observeData()

        tv_observe.setOnClickListener {
            startActivity(Intent(this, ObserverActivity::class.java))
        }
    }

    private fun initObserver() {
        LiveDataBus.instance.with<String>(TEST_EVENT_NAME).observe(this, Observer {
            Log.i(TAG, "Main observe value: $it")
        })
    }

    private fun initForeverObserver() {
        mForeverLiveData = LiveDataBus.instance.with(TEST_FOREVER_EVENT_NAME)
        mForeverLiveData?.observeForever(mForeverObserver)
    }

    private val mForeverObserver = Observer<String> {
        Log.i(TAG, "Main foreverObserve value: $it")
    }

    private fun observeData() {
        LiveDataBus.instance.with<String>(Observer2Activity.OBSERVER2_EVENT_NAME).observe(this, Observer {
            Log.i(TAG, "Main observe value from Observer2: $it")
        })
    }
}