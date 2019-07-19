package com.teknokrait.mussichusettsapp.view.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.teknokrait.mussichusettsapp.util.dpToPx

/**
 * Created by Aprilian Nur Wakhid Daini on 7/19/2019.
 */
class VerticalRecyclerView : RecyclerView {

    var linearLayoutManager: LinearLayoutManager? = null
        private set

    constructor(@NonNull context: Context) : super(context) {
        init()
    }

    constructor(@NonNull context: Context, @Nullable attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(@NonNull context: Context, @Nullable attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    @SuppressLint("WrongConstant")
    private fun init() {
        linearLayoutManager = LinearLayoutManager(
            getContext(),
            LinearLayoutManager.VERTICAL, false
        )
        //setHasFixedSize(true);
        setLayoutManager(linearLayoutManager)
        addItemDecoration(VerticalSpaceItemDecoration(dpToPx(8f, getContext()).toInt()))
    }
}