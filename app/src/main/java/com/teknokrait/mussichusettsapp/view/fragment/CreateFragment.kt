package com.teknokrait.mussichusettsapp.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.teknokrait.mussichusettsapp.R
import com.teknokrait.mussichusettsapp.local.RealmManager
import com.teknokrait.mussichusettsapp.model.Event
import com.teknokrait.mussichusettsapp.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_create.*
import java.util.*

class CreateFragment : BaseFragment() {

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_create
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RealmManager.open()
        initCreateEvent()
    }

    private fun initCreateEvent() {
        tvCreateEvent.setOnClickListener {
            if(etEventDate.text.toString().isEmpty()){
                Toast.makeText(context, "Event Date cannot be empty", Toast.LENGTH_LONG).show()
            } else if(etEventName.text.toString().isEmpty()){
                Toast.makeText(context, "Event Name cannot be empty", Toast.LENGTH_LONG).show()
            } else {
                RealmManager.createEventDao()?.save(Event(etEventName.text.toString(), Date()))
                Toast.makeText(context, "Create Event Success", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RealmManager.close()
    }

}