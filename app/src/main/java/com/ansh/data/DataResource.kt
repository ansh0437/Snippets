package com.ansh.data

class DataResource<out T> private constructor(val dataState: DataStatus, val data: T? = null) {

    companion object {
        fun <T> success(data: T? = null): DataResource<T> {
            return DataResource(DataStatus.Success, data)
        }

        fun <T> failure(data: Any? = null): DataResource<T> {
            return DataResource(DataStatus.Error(data))
        }
    }
}