package com.ansh.interfaces;

import com.ansh.data.enums.PermissionEnum;

public interface PermissionCallback {
    void onPermissionResult(int requestCode, PermissionEnum permissionResult);
}
