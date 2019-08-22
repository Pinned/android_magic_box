package com.knero.android.tools.file.browser.ui;


import androidx.collection.ArrayMap;

/**
 * Created by knero on 6/25/17.
 */

public class IntentListenerManager {
    private static ArrayMap<String, Object> mListeners = new ArrayMap<>();

    public static void putListener(String key, Object listener) {
        mListeners.put(key, listener);
    }

    public static Object getListener(String key) {
        Object object = mListeners.remove(key);
        return object;
    }
}
