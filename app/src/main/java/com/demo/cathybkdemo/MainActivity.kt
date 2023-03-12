package com.demo.cathybkdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.demo.cathybkdemo.webAPI.apiHandle.ApiController
import com.demo.cathybkdemo.webAPI.client.GithubApiClient
import com.demo.cathybkdemo.webAPI.client.apiResponse.GitHubUser

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GithubApiClient.getGithubUsers(0,
            object : ApiController<GitHubUser>(this@MainActivity, false) {
                override fun onSuccess(response: GitHubUser) {
                    Log.d("ben", "users = $response")
                }
            })
    }
}