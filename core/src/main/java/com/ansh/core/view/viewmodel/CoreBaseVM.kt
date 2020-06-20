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

    fun closePage(): LiveData<Boolean> = closePage
    fun backClick(): LiveData<Boolean> = onBackClicked
    fun menuClick(): LiveData<Boolean> = onMenuClicked

    fun goBack() {
        onBackClicked.value = true
    }

    fun openMenu() {
        onMenuClicked.value = true
    }

    fun setupToolbar(
        showToolbar: Boolean = true,
        showBack: Boolean = true,
        showMenu: Boolean = true
    ) {
        this.showToolbar.value = showToolbar
        this.showBack.value = showBack
        this.showMenu.value = showMenu
    }

}