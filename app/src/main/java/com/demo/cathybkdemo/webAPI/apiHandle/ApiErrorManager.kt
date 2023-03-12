package com.demo.cathybkdemo.webAPI.apiHandle

import android.content.Context
import com.demo.cathybkdemo.R
import org.xmlpull.v1.XmlPullParser

class ApiErrorManager(context: Context) {
    private val titles: Map<String, String>?
    private val messages: Map<String, String>?
    private val tag = javaClass.simpleName

    init {
        titles = getHashMapFromResource(context, R.xml.api_errors_title)
        messages = getHashMapFromResource(context, R.xml.api_errors_message)
    }

    fun getErrorTitle(errorCode: String): String? {
        return getErrorString(titles!!, errorCode)
    }

    fun getErrorMessage(errorCode: String): String? {
        return getErrorString(messages!!, errorCode)
    }

    private fun getErrorString(errorMap: Map<String, String>, errorCode: String): String? {
        return if (errorMap.containsKey(errorCode)) {
            errorMap[errorCode]
        } else {
            errorMap[UNKNOWN_ERROR] + " " + errorCode
        }
    }

    private fun getHashMapFromResource(context: Context, hashMapResId: Int): Map<String, String>? {
        val map = HashMap<String, String>()
        val parser = context.resources.getXml(hashMapResId)
        var key: String? = null
        var value: String? = null
        try {
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (parser.name == "entry") {
                        key = parser.getAttributeValue(null, "key")
                        if (null == key) {
                            parser.close()
                            return null
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (parser.name == "entry") {
                        if (key != null) {
                            if (value != null) {
                                map[key] = value
                            }
                        }
                        key = null
                        value = null
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    if (null != key) {
                        value = parser.text
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            return null
        }

        return map
    }

    companion object {
        val UNKNOWN_ERROR = "UNKNOWN" //與 xml/api_errors_title.xml 裡的 UNKNOWN 欄位對應
        val INTERNET_ERROR = "INTERNET" //與 xml/api_errors_title.xml 裡的 INTERNET 欄位對應
        val TOKEN_ERROR = "104"
        val ACCESS_DENIED = "403"
        val LOGIN_ERROR = "90020"
    }
}