package com.micck

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.micck.proxy.LiveDataBus
import kotlinx.android.synthetic.main.activity_observer.*

/**
 * @author lilin
 * @time on 2021/7/6 11:15 AM
 */
class ObserverActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ObserverActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_observer)

        observeData()

        tv_send_data.setOnClickListener {
            LiveDataBus.instance.with<String>(MainActivity.TEST_EVENT_NAME).setData("data from Observer")
        }

        tv_send_forever_data.setOnClickListener {
            LiveDataBus.instance.with<String>(MainActivity.TEST_FOREVER_EVENT_NAME).setData("forever data from Observer")
        }

        tv_test_observer2.setOnClickListener {
            startActivity(Intent(this, Observer2Activity::class.java))
        }
    }

    private fun observeData() {
        LiveDataBus.instance.with<String>(Observer2Activity.OBSERVER2_EVENT_NAME).observe(this, Observer {
            Log.i(TAG, "Observer observe value: $it")
        })
    }
}