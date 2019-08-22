package com.knero.android.tools.debug.display;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * @author zhaocheng.luo
 * @since 2019-08-22
 */
public class DebugDisplayProvider extends EmptyContentProvider {

    @Override
    public boolean onCreate() {
        try {
            Context context = getContext();
            Application application = (Application) context.getApplicationContext();
            DebugDisplay.instance().init(application);
            return true;
        } catch (Exception e) {
            Log.e("DebugDisplayProvider", "Init failed.", e);
        }
        return true;
    }
}
