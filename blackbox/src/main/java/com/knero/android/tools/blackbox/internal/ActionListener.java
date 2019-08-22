package com.knero.android.tools.blackbox.internal;

/**
 * @author knero
 * @since 2019-08-21
 */
public interface ActionListener {
    void onSuccess();

    void onError(Throwable throwable);
}
