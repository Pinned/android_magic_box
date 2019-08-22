package com.knero.android.tools.file.browser.internal.loader;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by luozhaocheng on 24/06/2017.
 */

public abstract class AsyncLoader<T> implements Loader<T> {
    private Callbacks<T> mCallbacks;
    private Executor mExecutor = Executors.newFixedThreadPool(3);
    private Handler mHandler = new Handler(Looper.getMainLooper());
    
    @Override
    public void startLoad() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    T result = onLoad();
                    callSuccess(result);
                } catch (Exception e) {
                    callError(e);
                }
            }
        });
    }

    @Override
    public void stopLoad() {
//        mExecutor.
    }

    protected abstract T onLoad() throws Exception;

    private void callSuccess(final T data) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mCallbacks != null) {
                    mCallbacks.onSuccess(data);
                }
            }
        });
    }

    private void callError(final Exception e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mCallbacks != null) {
                    mCallbacks.onFailed(e);
                }
            }
        });
    }

    @Override
    public void setCallbacks(Callbacks<T> callbacks) {

    }
}
