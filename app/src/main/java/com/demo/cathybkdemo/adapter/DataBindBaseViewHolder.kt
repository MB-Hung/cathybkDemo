package com.demo.cathybkdemo.adapter

import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.viewholder.BaseViewHolder


class DataBindBaseViewHolder(view: View) : BaseViewHolder(view) {

    private var viewBinding: ViewDataBinding? = null

    init {
        try {
            viewBinding = DataBindingUtil.bind(itemView)
        }catch (e:Exception){
            Log.i("DataBindBaseViewHolder",e.toString())
        }
    }

    fun getViewBinding(): ViewDataBinding? {
        return viewBinding
    }

}