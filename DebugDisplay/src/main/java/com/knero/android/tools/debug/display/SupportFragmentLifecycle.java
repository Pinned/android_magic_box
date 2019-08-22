package com.knero.android.tools.debug.display;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;


public class SupportFragmentLifecycle {
    public static void set(final Activity activity) {
        if ((activity instanceof FragmentActivity)) {
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                    DebugDisplay.instance().showText(activity, f.getClass().getSimpleName());
                }
            }, true);
        }
    }
}
