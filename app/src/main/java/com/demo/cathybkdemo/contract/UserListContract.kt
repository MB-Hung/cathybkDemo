package com.demo.cathybkdemo.contract

import com.demo.cathybkdemo.webAPI.client.apiResponse.GitHubUserItem


interface UserListContract {

    interface View : BaseView<Presenter> {
        fun showUserList(userList: MutableList<GitHubUserItem>)
    }

    interface Presenter : BasePresenter {
        fun loadApiData(lastUserId: Int)
    }

}