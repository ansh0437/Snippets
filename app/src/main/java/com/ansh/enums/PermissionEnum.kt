package com.ansh.enums

sealed class PermissionEnum {

    object GRANTED : PermissionEnum()
    object PARTIALLY_GRANTED : PermissionEnum()
    object DENIED : PermissionEnum()
    object NEVER_ASK_AGAIN : PermissionEnum()

}
