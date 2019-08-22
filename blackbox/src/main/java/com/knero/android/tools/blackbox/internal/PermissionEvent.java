package com.knero.android.tools.blackbox.internal;

import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author knero
 * @since 2019-08-21
 */
public class PermissionEvent extends BlackBoxEvent {

    private String[] mPermissions;
    private Map<String, Boolean> mPermissionStates = new HashMap<>();

    public PermissionEvent(String... permissions) {
        mPermissions = permissions;
    }

    @Override
    protected void execute() {
        if (mPermissions == null || mPermissions.length <= 0) {
            success();
        } else {
            doWithoutPermission();
        }
    }

    private void doWithoutPermission() {
        List<String> unRequestPermission = new ArrayList<>();
        for (String permission : mPermissions) {
            if (isGranted(permission)) {
                mPermissionStates.put(permission, true);
                continue;
            }
            unRequestPermission.add(permission);
        }
        if (unRequestPermission.size() <= 0) {
            success();
        } else {
            BlackBoxFragment fragment = getFragment();
            fragment.setCurrentEvent(this);
            getFragment().requestPermissions(unRequestPermission.toArray(new String[unRequestPermission.size()]),
                    BlackBoxFragment.REQUEST_PERMISSION_REQUEST_CODE);
        }
    }

    public boolean isGranted(String permission) {
        return !isMarshmallow() || getFragment().isGranted(permission);
    }

    public boolean isRevoked(String permission) {
        return isMarshmallow() && getFragment().isRevoked(permission);
    }

    boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int size = permissions.length;
        for (int i = 0; i < size; i++) {
            boolean granted = grantResults[i] == PackageManager.PERMISSION_GRANTED;
            mPermissionStates.put(permissions[i], granted);
        }
        for (Map.Entry<String, Boolean> item : mPermissionStates.entrySet()) {
            if (!item.getValue()) {
                error(new PermissionExcption(mPermissionStates));
                return;
            }
        }
        success();
    }

    public class PermissionExcption extends IllegalAccessException {
        private Map<String, Boolean> mPermissionStates;

        public PermissionExcption(Map<String, Boolean> permissionStates) {
            super("Permission error!");
            mPermissionStates = permissionStates;
        }

        public Map<String, Boolean> getPermissionStates() {
            return mPermissionStates;
        }
    }
}
