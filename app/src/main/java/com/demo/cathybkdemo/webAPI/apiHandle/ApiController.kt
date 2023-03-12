package com.demo.cathybkdemo.webAPI.apiHandle

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.demo.cathybkdemo.BuildConfig
import com.demo.cathybkdemo.views.MainActivity
import com.demo.cathybkdemo.R
import com.demo.cathybkdemo.views.dialog.NoDoubleClickAlertDialogBuilder
import com.demo.cathybkdemo.webAPI.MainApp.Companion.apiErrorManager
import com.demo.cathybkdemo.webAPI.apiHandle.ApiErrorManager.Companion.INTERNET_ERROR
import com.demo.cathybkdemo.webAPI.apiHandle.ApiErrorManager.Companion.TOKEN_ERROR
import com.demo.cathybkdemo.webAPI.apiHandle.ApiErrorManager.Companion.UNKNOWN_ERROR
import com.google.gson.Gson
import okio.Buffer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class ApiController<T> @JvmOverloads constructor(private val context: Context, shouldHandleProgressDialog: Boolean = true) {
    private var shouldHandleProgressDialog = true
    private val apiDetail = ApiDetail()
    private var dialog: Dialog
    private val RESPONSE_CODE: Array<Int> = arrayOf(1000, 1001, 200, 2000)

    init {
        this.shouldHandleProgressDialog = shouldHandleProgressDialog
        dialog = showLoading(context)
    }

    fun enqueue(call: Call<T>) {
        apiDetail.setParamMessage(call)
        if (shouldHandleProgressDialog)
            enqueueStart(dialog.show())

        try {
            call.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    dialog.dismiss()
                    println(response.raw().body()!!.contentType().toString())
                    if (response.isSuccessful && response.body() != null) {
//                        val jsonString = Gson().toJson(response.body())
//                        val apiResponse = Gson().fromJson(jsonString, ResponseCommon::class.java)

                        // TODO 暫時先不判斷是不是失敗
//                    if (RESPONSE_CODE.contains(apiResponse.code))
//                        onSuccess(response.body()!!)
//                    else
//                        onFail(apiResponse.code.toString(), apiResponse.code.toString(), apiResponse.message, getDialog(context))
                        onSuccess(response.body()!!)

                    } else if (onFail(response)) { //

                    } else if (response.errorBody() != null) {
                        try {
                            onFail(context, getErrorCode(response))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } else {
                        try {
                            onFail(context, UNKNOWN_ERROR)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                    enqueueFinish()
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    dialog.dismiss()
                    apiDetail.setFailureMessage(t)
                    onFail(context, INTERNET_ERROR)
                    enqueueFinish()
                }
            })
        } catch (error: Exception) {
            call.cancel()
        }

    }

    private fun getErrorCode(response: Response<T>): String {
        val errorString: String
        try {
            errorString = response.errorBody().toString()
            apiDetail.setErrorMessage(errorString)
        } catch (e: Exception) {
            return UNKNOWN_ERROR
        }

        val gson = Gson()
        try {
            val e1 = gson.fromJson(errorString, ResponseError::class.java)
            return e1.errorCode
        } catch (ignored: Exception) {

        }

        try {
            val e2 = gson.fromJson(errorString, ResponseError2::class.java)
            return e2.errorCode
        } catch (ignored: Exception) {

        }

        return UNKNOWN_ERROR
    }

    private fun reStart() {

//        MainApp.removeLoginInfo()
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    /**
     * 當 context 為空或是 shouldHandleProgressDialog 為 False 時，傳入的 progressDialog 會是 null
     */
    private fun enqueueStart(alertDialog: Unit) {

    }

    fun enqueueFinish() {

    }

    private fun onFailPositiveButtonClick(errorCode: String?) {

    }

    private fun onFailDialogDismiss(errorCode: String?) {

    }

    @Throws(Exception::class)
    abstract fun onSuccess(response: T)

    open fun onFail(httpResponse: Response<T>): Boolean {
        return false // 覆寫後可以用 return true 或 false 決定：ApiClient 要不要執行後續的錯誤處理機制
    }

    private fun onFail(context: Context, errorCode: String) {
        if (onFailEnable) {
            val title = getDialogTitle(errorCode)
            val message = getDialogMessage(errorCode)
            onFail(errorCode, title, message, getDialog(context))
        }
    }

    fun onFail(errorCode: String?, title: String?, message: String?, builder: NoDoubleClickAlertDialogBuilder?) {
        if (builder != null) {
            val positiveButtonClickListener = DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                if (TOKEN_ERROR == errorCode) {
                    // MainApp.removeLoginInfo()
                    reStart()
                } else {
                    onFailPositiveButtonClick(errorCode)
                }
            }
            builder.setOnDismissListener {
                onFailEnable = true
                onFailDialogDismiss(errorCode)
            }
            builder.setPositiveButton(android.R.string.ok, positiveButtonClickListener)
            builder.setTitle(title).setMessage(message)
            builder.setCancelable(false)
            try {
                // builder.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun getDialogTitle(errorCode: String): String? {
        return apiErrorManager.getErrorTitle(errorCode)
    }

    private fun getDialogMessage(errorCode: String): String? {
        return apiErrorManager.getErrorMessage(errorCode)
    }

    private fun getDialog(context: Context): NoDoubleClickAlertDialogBuilder {
        val builder = NoDoubleClickAlertDialogBuilder(context, com.google.android.material.R.style.Theme_AppCompat_Light_Dialog_Alert)
        return apiDetail.setDialogBuilder(builder)
    }

    internal inner class ApiDetail {
        var message = ""
        var isRelease = BuildConfig.BUILD_TYPE === "release"

        fun setParamMessage(call: Call<T>) {
            if (!isRelease) {
                message = "Method: " + call.request().method()
                message += "\n\nUrl:\n" + call.request().url()
                message += "\n\nHeader:\n" + call.request().headers().toString()
                val requestBody = call.request().body()
                if (requestBody != null) {
                    try {
                        val sink = Buffer()
                        requestBody.writeTo(sink)
                        message += "\nBody:\n" + sink.readUtf8()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }
        }

        fun setFailureMessage(t: Throwable) {
            if (!isRelease) {
                message += "\n\nFailure: $t"
            }
        }

        fun setErrorMessage(errorString: String) {
            if (!isRelease) {
                message += "\n\nResponse Error String: \n$errorString"
            }
        }

        fun setDialogBuilder(builder: NoDoubleClickAlertDialogBuilder): NoDoubleClickAlertDialogBuilder {
            if (!isRelease) {
                builder.setNegativeButton("send test data") { _, _ ->
                    val it = Intent(Intent.ACTION_SEND)
                    it.putExtra(Intent.EXTRA_TEXT, message)
                    it.type = "text/plain"
                    context.startActivity(Intent.createChooser(it, "傳送詳細資料（測試用）"))
                }
            }
            return builder
        }
    }

    companion object {
        private var onFailEnable = true
    }

    fun showLoading(context: Context): Dialog {
        val dialog = Dialog(context)
        val inflate = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null)
        dialog.setContentView(inflate)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}
