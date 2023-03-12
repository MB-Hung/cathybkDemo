package com.demo.cathybkdemo.views.dialog

import android.content.Context
import android.content.DialogInterface.*
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.demo.cathybkdemo.listener.NoDoubleClickListener


class NoDoubleClickAlertDialogBuilder(context: Context, themeResId: Int) : AlertDialog.Builder(context, themeResId) {

    private var positiveListener: OnClickListener? = null
    private var negativeListener: OnClickListener? = null
    private var neutralListener: OnClickListener? = null

    override fun setPositiveButton(@StringRes textId: Int, listener: OnClickListener): AlertDialog.Builder {
        positiveListener = listener
        return super.setPositiveButton(textId, null)
    }

    override fun setPositiveButton(text: CharSequence, listener: OnClickListener): AlertDialog.Builder {
        positiveListener = listener
        return super.setPositiveButton(text, null)
    }

    override fun setNegativeButton(@StringRes textId: Int, listener: OnClickListener): AlertDialog.Builder {
        negativeListener = listener
        return super.setNegativeButton(textId, null)
    }

    override fun setNegativeButton(text: CharSequence, listener: OnClickListener): AlertDialog.Builder {
        negativeListener = listener
        return super.setNegativeButton(text, null)
    }

    override fun setNeutralButton(@StringRes textId: Int, listener: OnClickListener): AlertDialog.Builder {
        neutralListener = listener
        return super.setNeutralButton(textId, null)
    }

    override fun setNeutralButton(text: CharSequence, listener: OnClickListener): AlertDialog.Builder {
        neutralListener = listener
        return super.setNeutralButton(text, null)
    }

    override fun show(): AlertDialog {
        //必須要在 super.show() 以後再設定 listener
        val dialog = super.show()
        dialog.getButton(BUTTON_POSITIVE).setOnClickListener(object : NoDoubleClickListener() {
            override fun onNoDoubleClick(v: View) {
                positiveListener!!.onClick(dialog, BUTTON_POSITIVE)
            }
        })
        dialog.getButton(BUTTON_NEGATIVE).setOnClickListener(object : NoDoubleClickListener() {
            public override fun onNoDoubleClick(v: View) {
                negativeListener!!.onClick(dialog, BUTTON_NEGATIVE)
            }
        })
        dialog.getButton(BUTTON_NEUTRAL).setOnClickListener(object : NoDoubleClickListener() {
            public override fun onNoDoubleClick(v: View) {
                neutralListener!!.onClick(dialog, BUTTON_NEUTRAL)
            }
        })
        return dialog
    }
}