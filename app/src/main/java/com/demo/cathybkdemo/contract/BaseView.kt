package com.demo.cathybkdemo.contract

interface BaseView<T> {
    fun setPresenter(presenter: T)
}