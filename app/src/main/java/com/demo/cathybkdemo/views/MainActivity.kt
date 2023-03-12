package com.demo.cathybkdemo.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.cathybkdemo.R
import com.demo.cathybkdemo.adapter.UserAdapter
import com.demo.cathybkdemo.contract.user.UserListContract
import com.demo.cathybkdemo.contract.user.UserListPresenter
import com.demo.cathybkdemo.databinding.ActivityMainBinding
import com.demo.cathybkdemo.webAPI.client.apiResponse.GitHubUserItem

class MainActivity : AppCompatActivity(), UserListContract.ViewUser {

    private lateinit var presenter: UserListContract.Presenter
    private lateinit var binding: ActivityMainBinding
    private lateinit var mUserAdapter: UserAdapter
    private var isLoadFirstTimeApi = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)

        mUserAdapter = UserAdapter(mutableListOf())

        binding.rvListUser.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mUserAdapter

            mUserAdapter.setOnItemClickListener { _, _, position ->
                val intent = Intent(this@MainActivity, UserDetailActivity::class.java)
                intent.putExtra("UserName", mUserAdapter.data[position].login)
                startActivity(intent)
            }

            mUserAdapter.apply {
                loadMoreModule.setOnLoadMoreListener {
                    if (mUserAdapter.data.size < 100) {
                        presenter.loadApiData(mUserAdapter.data[mUserAdapter.data.size - 1].id)
                    } else {
                        mUserAdapter.loadMoreModule.loadMoreComplete()
                        mUserAdapter.loadMoreModule.loadMoreEnd()
                    }
                }
            }
        }

        presenter = UserListPresenter(this@MainActivity, this)
        setPresenter(presenter)

    }

    override fun onResume() {
        super.onResume()
        if (!isLoadFirstTimeApi) {
            presenter.start(0)
            isLoadFirstTimeApi = true
        }
    }

    override fun showUserList(userList: MutableList<GitHubUserItem>) {
        mUserAdapter.addData(userList)
        mUserAdapter.loadMoreModule.loadMoreComplete()
    }

    override fun setPresenter(presenter: UserListContract.Presenter) {
        this.presenter = presenter
    }
}