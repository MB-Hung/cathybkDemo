package com.demo.cathybkdemo.webAPI.client

import com.demo.cathybkdemo.webAPI.client.apiResponse.GitHubUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query

interface GitHubApi {

    @GET("users?per_page=20")
    fun getGithubUsers(
        @HeaderMap headers: HashMap<String, String>,
        @Query(value = "since", encoded = true) lastUserId: Int,
    ): Call<GitHubUser>

}