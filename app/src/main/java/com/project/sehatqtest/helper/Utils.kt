package com.project.sehatqtest.helper

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import java.util.*

object Utils {
   private const val PASSWORD_LENGTH_MINIMUM = 6

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        activity.currentFocus?.windowToken?.apply {
            inputMethodManager.hideSoftInputFromWindow(this, 0)
        }
    }

    fun isUserNameValid(username: String?) = !username.isNullOrEmpty()

    fun isUserPasswordValid(password: String?) =  password?.length?:0 >= PASSWORD_LENGTH_MINIMUM

    fun convertMillisToDateString(millis: Long?): String{
        if(millis == null) return ""
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH)
        return dateFormat.format(Date(millis))
    }
}