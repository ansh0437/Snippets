package com.ansh.extensions

import android.location.Geocoder
import android.location.Location
import com.ansh.CoreApp
import java.util.*

private const val tag = "LocationExtension"

val Location.getFormattedAddress
    get() = run {
        try {
            val geo = Geocoder(CoreApp.appCtx, Locale.getDefault())
            val addressList = geo.getFromLocation(latitude, longitude, 1)
            val sb = StringBuilder("")
            if (addressList != null && addressList.size > 0) {
                val address = addressList[0]
                for (i in 0 until address.maxAddressLineIndex)
                    sb.append(address.getAddressLine(i)).append(" ")
            }
            sb.toString()
        } catch (e: Exception) {
            e.logError(tag, "getFormattedAddress: ")
            ""
        }
    }
