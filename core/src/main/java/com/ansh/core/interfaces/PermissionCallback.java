package com.ansh.core.interfaces;

import com.ansh.core.data.enums.PermissionEnum;

public interface PermissionCallback {
    void onPermissionResult(int requestCode, PermissionEnum permissionResult);
}
