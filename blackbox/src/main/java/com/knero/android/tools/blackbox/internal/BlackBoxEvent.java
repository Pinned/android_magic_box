package com.knero.android.tools.blackbox.internal;

import android.content.Intent;


import com.knero.android.tools.blackbox.ErrorAction;

/**
 * @author knero
 * @since 2019-08-21
 */
public abstract class BlackBoxEvent {
    private BlackBoxEvent mNextBlackBoxEvent;
    private Lazy<BlackBoxFragment> mLazyFragment;
    private ActionListener mListener;
    private ErrorAction mErrorAction;

    public BlackBoxEvent getNextBlackBoxEvent() {
        return mNextBlackBoxEvent;
    }

    public void setNextBlackBoxEvent(BlackBoxEvent nextBlackBoxEvent) {
        mNextBlackBoxEvent = nextBlackBoxEvent;
    }

    public void setLazyFragment(Lazy<BlackBoxFragment> lazyFragment) {
        mLazyFragment = lazyFragment;
    }

    public void setListener(ActionListener listener) {
        mListener = listener;
    }

    ErrorAction getErrorAction() {
        return mErrorAction;
    }

    void setErrorAction(ErrorAction errorAction) {
        mErrorAction = errorAction;
    }

    protected void success() {
        mListener.onSuccess();
    }

    protected void error(Throwable throwable) {
        mListener.onError(throwable);
    }

    public BlackBoxFragment getFragment() {
        return mLazyFragment.get();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    protected void startActivityForResult(Intent intent) {
        BlackBoxFragment fragment = getFragment();
        fragment.setCurrentEvent(this);
        fragment.startActivityForResult(intent, BlackBoxFragment.START_ACTIVITY_REQUEST_CODE);
    }

    protected abstract void execute();
}
