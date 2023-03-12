package com.demo.cathybkdemo.contract.user

import com.demo.cathybkdemo.webAPI.client.apiResponse.GitHubUserItem

interface UserListContract {

    interface ViewUser : UserBaseView<Presenter> {
        fun showUserList(userList: MutableList<GitHubUserItem>)
    }

    interface Presenter : UserPresenter {
        fun loadApiData(lastUserId: Int)
    }

}