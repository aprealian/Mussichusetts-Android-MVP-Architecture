package com.teknokrait.mussichusettsapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.DisplayMetrics
import android.webkit.WebView
import android.annotation.TargetApi
import android.text.format.DateFormat
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*
import android.net.ConnectivityManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Created by Aprilian Nur Wakhid Daini on 7/19/2019.
 */
fun toBitmap(context: Context, layoutResource: Int): Bitmap {
    return BitmapFactory.decodeResource(context.resources, layoutResource)
}

fun dpToPx(dp: Float, context: Context): Float {
    return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun pxToDp(px: Float, context: Context): Float {
    return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun WebView.loadFile(filePath: String) {
    loadUrl("file:///android_asset/$filePath");
}


@SuppressLint("SimpleDateFormat")
fun getModifiedDate(stringDate: String?): String {

    //val s = "2013-07-17T03:58:00.000Z"
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val date = formatter.parse(stringDate)

    return getModifiedDate(Locale.getDefault(), date.time)
}

@SuppressLint("SimpleDateFormat")
fun getModifiedDate(locale: Locale, modified: Long): String {
    val dateFormat: SimpleDateFormat?

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
        dateFormat = SimpleDateFormat(getDateFormat(locale))
    } else {
        dateFormat = SimpleDateFormat("dd MMMM yyyy")
    }

    return dateFormat.format(Date(modified))
}

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
fun getDateFormat(locale: Locale): String {
    return DateFormat.getBestDateTimePattern(locale, "dd MMMM yyyy")
}


fun addFragmentToActivity(manager: FragmentManager, fragment: Fragment, frameId: Int) {
    val transaction = manager.beginTransaction()
    transaction.add(frameId, fragment)
    transaction.commit()
}

//fun removeFragmentToActivity(manager: FragmentManager, fragment: Fragment, frameId: Int) {
//    val transaction = manager.beginTransaction()
//    transaction.remove(fragment)
//    transaction.commit()
//}

fun replaceFragmentToActivity(manager: FragmentManager, fragment: Fragment, frameId: Int) {
    val transaction = manager.beginTransaction()
    transaction.replace(frameId,fragment)
    transaction.commit()
}


fun closeKeyboard(context: Context, editText: EditText){
    editText.clearFocus()
    val `in` = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    `in`.hideSoftInputFromWindow(editText.getWindowToken(), 0)
}

fun isNetworkConnected(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager //1
    val networkInfo = connectivityManager.activeNetworkInfo //2
    return networkInfo != null && networkInfo.isConnected //3
}
