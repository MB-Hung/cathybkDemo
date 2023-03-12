package com.demo.cathybkdemo.webAPI

import android.app.Application
import com.demo.cathybkdemo.webAPI.apiHandle.ApiErrorManager
import com.demo.cathybkdemo.webAPI.server.ApiClient

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        apiErrorManager = ApiErrorManager(applicationContext)
        ApiClient.initApi(applicationContext)
    }

    companion object {
        lateinit var apiErrorManager: ApiErrorManager
    }
}