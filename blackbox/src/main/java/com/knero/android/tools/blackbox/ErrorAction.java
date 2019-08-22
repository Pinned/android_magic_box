package com.knero.android.tools.blackbox;

/**
 * @author knero
 * @since 2019-08-21
 */
public interface ErrorAction {
    boolean call(Throwable throwable);
}
