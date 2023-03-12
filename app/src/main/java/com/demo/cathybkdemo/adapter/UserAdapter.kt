package com.demo.cathybkdemo.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.demo.cathybkdemo.R
import com.demo.cathybkdemo.databinding.RvItemUserBinding
import com.demo.cathybkdemo.webAPI.client.apiResponse.GitHubUserItem


class UserAdapter(dataList: MutableList<GitHubUserItem>) :
    BaseQuickAdapter<GitHubUserItem, DataBindBaseViewHolder>(R.layout.rv_item_user, dataList), LoadMoreModule {

    override fun convert(holder: DataBindBaseViewHolder, item: GitHubUserItem) {
        val binding: RvItemUserBinding = holder.getViewBinding() as RvItemUserBinding
        binding.data = item
        binding.executePendingBindings()

        Glide.with(holder.itemView.context).load(item.avatar_url).into(binding.userAvatar)

    }

}