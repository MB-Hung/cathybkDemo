package com.demo.cathybkdemo.contract.userDetail

import android.content.Context
import com.demo.cathybkdemo.webAPI.apiHandle.ApiController
import com.demo.cathybkdemo.webAPI.client.GithubApiClient
import com.demo.cathybkdemo.webAPI.client.apiResponse.GitHubUserDetail

class UserInfoPresenter(val context: Context, val view: UserDetailContract.ViewUser) : UserDetailContract.Presenter {

    override fun start(userName: String) {
        loadApiData(userName)
    }

    override fun loadApiData(userName: String) {
        GithubApiClient.getGithubUserDetail(userName,
            object : ApiController<GitHubUserDetail>(context, true) {
                override fun onSuccess(response: GitHubUserDetail) {
                    view.showUserDetail(response)
                }
            })
    }


}