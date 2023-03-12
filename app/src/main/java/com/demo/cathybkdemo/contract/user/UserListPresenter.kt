package com.demo.cathybkdemo.contract.user

import android.content.Context
import com.demo.cathybkdemo.webAPI.apiHandle.ApiController
import com.demo.cathybkdemo.webAPI.client.GithubApiClient
import com.demo.cathybkdemo.webAPI.client.apiResponse.GitHubUser

class UserListPresenter(val context: Context, val view: UserListContract.ViewUser) : UserListContract.Presenter {

    override fun start(lastUserId: Int) {
        loadApiData(lastUserId)
    }

    override fun loadApiData(lastUserId: Int) {
        GithubApiClient.getGithubUsers(lastUserId,
            object : ApiController<GitHubUser>(context, true) {
                override fun onSuccess(response: GitHubUser) {
                    view.showUserList(response)
                }
            })
    }
}