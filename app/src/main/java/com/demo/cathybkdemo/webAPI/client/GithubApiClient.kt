package com.demo.cathybkdemo.webAPI.client

import com.demo.cathybkdemo.webAPI.apiHandle.ApiController
import com.demo.cathybkdemo.webAPI.client.apiResponse.GitHubUser
import com.demo.cathybkdemo.webAPI.server.ApiClient


object GithubApiClient {

    fun getGithubUsers(nextPageFirstUserId: Int, controller: ApiController<GitHubUser>) {
        val headers = ApiClient.header(false)
        headers["Content-Type"] = "application/json"
        headers["Accept"] = "application/vnd.github+json"
        controller.enqueue(ApiClient.gitHubApi.getGithubUsers(headers, nextPageFirstUserId))
    }

}