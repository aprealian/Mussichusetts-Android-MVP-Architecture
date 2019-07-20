package com.teknokrait.mussichusettsapp.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.teknokrait.mussichusettsapp.listener.RxBus
import org.greenrobot.eventbus.EventBus

/**
 * Created by Aprilian Nur Wakhid Daini on 7/19/2019.
 */

abstract class BaseFragment : Fragment() {

    protected var isEventBusNeeded:Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getFragmentLayout(), container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * Every fragment has to inflate a layout in the onCreateView method. We have added this method to
     * avoid duplicate all the inflate code in every fragment. You only have to return the layout to
     * inflate in this method when extends BaseFragment.
     */
    protected abstract fun getFragmentLayout(): Int

    override fun onDestroy() {
        super.onDestroy()
        RxBus.unregister(this)
    }

    override fun onStart() {
        super.onStart()
        if(isEventBusNeeded)EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        if(isEventBusNeeded)EventBus.getDefault().unregister(this)
    }
}