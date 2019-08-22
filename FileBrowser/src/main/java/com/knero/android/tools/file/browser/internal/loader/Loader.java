package com.knero.android.tools.file.browser.internal.loader;

/**
 * Created by luozhaocheng on 24/06/2017.
 */

public interface Loader<T> {
    void startLoad();

    void stopLoad();

    void setCallbacks(Callbacks<T> callbacks);

    public interface Callbacks<T> {
        void onSuccess(T result);

        void onFailed(Exception e);
    }
}
