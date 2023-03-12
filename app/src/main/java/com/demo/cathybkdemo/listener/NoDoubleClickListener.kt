package com.demo.cathybkdemo.listener

import android.view.View
import java.util.*

abstract class NoDoubleClickListener : View.OnClickListener {
    private var MIN_CLICK_DELAY_TIME = 350

    private val isAvailableToClick: Boolean
        get() {
            val currentTime = Calendar.getInstance().timeInMillis
            synchronized(this) {
                if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                    lastClickTime = currentTime
                    return true
                }
                return false
            }
        }

    override fun onClick(v: View) {
        if (isAvailableToClick) {
            onNoDoubleClick(v)
        }
    }

    protected abstract fun onNoDoubleClick(v: View)

    companion object {
        private var lastClickTime = Calendar.getInstance().timeInMillis
    }
}
