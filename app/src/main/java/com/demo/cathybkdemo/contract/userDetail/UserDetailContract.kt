package com.demo.cathybkdemo.contract.userDetail

import com.demo.cathybkdemo.webAPI.client.apiResponse.GitHubUserDetail

interface UserDetailContract {
    interface ViewUser : UserDetailBaseView<Presenter> {
        fun showUserDetail(userDetail: GitHubUserDetail)
    }

    interface Presenter : UserDetailPresenter {
        fun loadApiData(userName: String)
    }
}