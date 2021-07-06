package com.micck

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.micck.proxy.LiveDataBus
import kotlinx.android.synthetic.main.activity_observer2.*

/**
 * @author lilin
 * @time on 2021/7/6 11:15 AM
 */
class Observer2Activity : AppCompatActivity() {

    companion object {
        private const val TAG = "Observer2Activity"

        const val OBSERVER2_EVENT_NAME = "observer2EventName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_observer2)

        var count = 0

        tv_send_data.setOnClickListener {
            LiveDataBus.instance.with<String>(OBSERVER2_EVENT_NAME).setData("data from Observer2: ${count++}")
        }
    }
}