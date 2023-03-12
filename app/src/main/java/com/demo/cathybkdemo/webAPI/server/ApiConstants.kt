package com.demo.cathybkdemo.webAPI.server

import android.content.Context
import com.demo.cathybkdemo.R

object ApiConstants {

    enum class ServerType {
        API_GitHub_FORMAL,
    }

    var SERVER_PATH = ""
    var SERVER_URL = ""

    fun getServerUrl(context: Context, serverType: ServerType): String {
        SERVER_URL = when (serverType) {
            ServerType.API_GitHub_FORMAL -> {
                context.getString(R.string.gcreate_server_host_formal)
            }
        }

        return SERVER_URL
    }

}