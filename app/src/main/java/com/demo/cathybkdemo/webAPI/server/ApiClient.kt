package com.demo.cathybkdemo.webAPI.server

import android.annotation.SuppressLint
import android.content.Context
import com.demo.cathybkdemo.BuildConfig
import com.demo.cathybkdemo.webAPI.client.GitHubApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@SuppressLint("StaticFieldLeak")
object ApiClient {

    lateinit var gitHubApi: GitHubApi
    lateinit var mContext: Context

    fun header(includeToken: Boolean): HashMap<String, String> {
        val map = HashMap<String, String>()

        return map
    }

    fun initApi(context: Context) {
        mContext = context

        val githubApiServerUrl = ApiConstants.getServerUrl(context, ApiConstants.ServerType.API_GitHub_FORMAL)
        gitHubApi = getRetrofit(githubApiServerUrl).create(GitHubApi::class.java)

    }

    private fun getRetrofit(serverPath: String): Retrofit {
        val builder = Retrofit.Builder().baseUrl(serverPath).addConverterFactory(GsonConverterFactory.create())
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            val httpClient =
                OkHttpClient.Builder()
                    .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS).build()
            builder.client(httpClient)
        }
        return builder.build()
    }


}
