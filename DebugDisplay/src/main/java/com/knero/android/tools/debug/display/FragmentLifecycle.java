package com.knero.android.tools.debug.display;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentManager.FragmentLifecycleCallbacks;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;

public class FragmentLifecycle {
    public static void set(final Activity activity) {
        if (Build.VERSION.SDK_INT >= 26) {
            activity.getFragmentManager()
                    .registerFragmentLifecycleCallbacks(new FragmentLifecycleCallbacks() {
                        public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                            DebugDisplay.instance().showText(activity, f.getClass().getSimpleName());
                        }
                    }, true);
        }
    }
}
