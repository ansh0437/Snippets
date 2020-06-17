package com.ansh.core.data

sealed class DataStatus {

    data class Loading(val progress: Int? = null) : DataStatus()

    data class Successful(val data: Any? = null) : DataStatus()

    data class Failed(val errorMsg: String? = null) : DataStatus()

}