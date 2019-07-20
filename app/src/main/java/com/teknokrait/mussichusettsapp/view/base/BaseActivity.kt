package com.teknokrait.mussichusettsapp.view.base

import com.teknokrait.mussichusettsapp.listener.RxBus
import androidx.appcompat.app.AppCompatActivity
import org.greenrobot.eventbus.EventBus





/**
 * Created by Aprilian Nur Wakhid Daini on 7/20/2019.
 */
abstract class BaseActivity : AppCompatActivity() {

    private var isEventBusNeeded:Boolean = false

    override fun onDestroy() {
        super.onDestroy()
        RxBus.unregister(this)
    }

    public override fun onStart() {
        super.onStart()
        if(isEventBusNeeded)EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        if(isEventBusNeeded)EventBus.getDefault().unregister(this)
    }
}