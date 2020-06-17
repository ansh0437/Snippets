package com.ansh.core.api.builders

import okhttp3.Headers

class HeaderBuilder private constructor() {

    companion object {
        fun addHeader(key: String, value: String): HeaderBuilder {
            return HeaderBuilder().addHeader(key, value)
        }
    }

    private var headerBuilder: Headers.Builder = Headers.Builder()

    fun addHeader(key: String, value: String) = apply { headerBuilder.add(key, value) }

    fun build(): Headers = headerBuilder.build()

}