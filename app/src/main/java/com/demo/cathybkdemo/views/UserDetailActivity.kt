package com.demo.cathybkdemo.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.demo.cathybkdemo.R
import com.demo.cathybkdemo.contract.userDetail.UserDetailContract
import com.demo.cathybkdemo.contract.userDetail.UserInfoPresenter
import com.demo.cathybkdemo.databinding.ActivityUserDetailBinding
import com.demo.cathybkdemo.webAPI.client.apiResponse.GitHubUserDetail


class UserDetailActivity : AppCompatActivity(), UserDetailContract.ViewUser {

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var presenter: UserDetailContract.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@UserDetailActivity, R.layout.activity_user_detail)

        presenter = UserInfoPresenter(this@UserDetailActivity, this)
        setPresenter(presenter)

        binding.apply {
            toolbar.setNavigationOnClickListener {
                finish()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        intent.getStringExtra("UserName")?.let { presenter.start(it) }
    }

    override fun showUserDetail(userDetail: GitHubUserDetail) {
        binding.apply {
            Glide.with(this@UserDetailActivity).load(userDetail.avatar_url).into(binding.userAvatar)
            data = userDetail
        }
    }

    override fun setPresenter(presenter: UserDetailContract.Presenter) {
        this.presenter = presenter
    }

}