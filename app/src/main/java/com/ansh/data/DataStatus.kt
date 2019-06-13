package com.ansh.data

sealed class DataStatus {

    object Success : DataStatus()

    data class Error<out T>(val err: T? = null) : DataStatus()

}