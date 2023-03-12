package com.demo.cathybkdemo.webAPI.client

import com.demo.cathybkdemo.webAPI.apiHandle.ApiController
import com.demo.cathybkdemo.webAPI.client.apiResponse.GitHubUser
import com.demo.cathybkdemo.webAPI.client.apiResponse.GitHubUserDetail
import com.demo.cathybkdemo.webAPI.server.ApiClient


object GithubApiClient {

    /**
     * Token will expired at Mar 19 2023
     * */
    fun getGithubUsers(nextPageFirstUserId: Int, controller: ApiController<GitHubUser>) {
        val headers = ApiClient.header(false)
        headers["Content-Type"] = "application/json"
        headers["Accept"] = "application/vnd.github+json"
        headers["Authorization"] = "Bearer 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"
        controller.enqueue(ApiClient.gitHubApi.getGithubUsers(headers, nextPageFirstUserId))
    }

    fun getGithubUserDetail(Username: String, controller: ApiController<GitHubUserDetail>) {
        val headers = ApiClient.header(false)
        headers["Content-Type"] = "application/json"
        headers["Accept"] = "application/vnd.github+json"
        headers["Authorization"] = "Bearer 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"
        controller.enqueue(ApiClient.gitHubApi.getGithubUserDetail(headers, Username))
    }

}