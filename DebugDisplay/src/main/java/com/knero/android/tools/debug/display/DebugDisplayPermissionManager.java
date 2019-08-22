package com.knero.android.tools.debug.display;

import android.app.Activity;
import android.app.FragmentManager;

public class DebugDisplayPermissionManager {
    private static final String FRAGMENT_TAG = "cn.qiwoo.support.debug.display.permission.fragment";
    private DebugDisplayPermissionFragment mPermissionFragment;

    public DebugDisplayPermissionManager(Activity activity) {
        this.mPermissionFragment = getPermissionManagerFragment(activity);
    }

    private DebugDisplayPermissionFragment getPermissionManagerFragment(Activity activity) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        DebugDisplayPermissionFragment fragment = (DebugDisplayPermissionFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new DebugDisplayPermissionFragment();
            fragmentManager.beginTransaction().add(fragment, FRAGMENT_TAG).commit();
            fragmentManager.executePendingTransactions();
        }
        return fragment;
    }

    public void getOverlayPermission(DebugDisplayPermissionCallback callback) {
        this.mPermissionFragment.setCallback(callback);
        this.mPermissionFragment.showGetPermissionDialog();
    }
}
