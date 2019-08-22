package com.knero.android.tools.blackbox.internal;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * @author knero
 * @since 2019-08-21
 */
public class BlackBoxFragment extends Fragment {
    static final int START_ACTIVITY_REQUEST_CODE = 1;
    static final int REQUEST_PERMISSION_REQUEST_CODE = 1;
    private BlackBoxEvent mCurrentEvent;

    public void setCurrentEvent(BlackBoxEvent currentEvent) {
        mCurrentEvent = currentEvent;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCurrentEvent.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mCurrentEvent instanceof PermissionEvent) {
            ((PermissionEvent) mCurrentEvent).onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 判断是不是授权
     */
    @TargetApi(Build.VERSION_CODES.M)
    boolean isGranted(String permission) {
        return getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 判断是不是在包中申明
     */
    @TargetApi(Build.VERSION_CODES.M)
    boolean isRevoked(String permission) {
        return getActivity().getPackageManager().isPermissionRevokedByPolicy(permission, getActivity().getPackageName());
    }

}
