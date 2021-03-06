package com.ansh.core.data.enums

sealed class PermissionEnum {

    object Granted : PermissionEnum()
    object PartiallyGranted : PermissionEnum()
    object Denied : PermissionEnum()
    object NeverAskAgain : PermissionEnum()

}
