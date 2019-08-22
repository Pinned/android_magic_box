package com.knero.android.tools.blackbox.internal;

/**
 * @author wangshi
 * @since 2019-08-21
 */
public interface Lazy<T> {
    T get();
}
