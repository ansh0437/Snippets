package com.ansh.core.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class CoreVM : ViewModel() {

    val isRefreshing = MutableLiveData(false)
    val apiCalling = MutableLiveData(false)

    val closePage = MutableLiveData(false)

    val toolbarTitle = MutableLiveData("")
    val showToolbar = MutableLiveData(true)
    val showBack = MutableLiveData(true)
    val showMenu = MutableLiveData(true)

    val onBackClicked = MutableLiveData(false)
    val onMenuClicked = MutableLiveData(false)

    fun backClick(): LiveData<Boolean> = onBackClicked
    fun menuClick(): LiveData<Boolean> = onBackClicked

    fun goBack() {
        onBackClicked.value = true
    }

    fun openMenu() {
        onMenuClicked.value = true
    }

    fun setupToolbar() {

    }

}