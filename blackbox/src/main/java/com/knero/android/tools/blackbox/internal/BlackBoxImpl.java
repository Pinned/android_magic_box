package com.knero.android.tools.blackbox.internal;

import com.knero.android.tools.blackbox.Action;
import com.knero.android.tools.blackbox.ErrorAction;

/**
 * @author knero
 * @since 2019-08-21
 */
public class BlackBoxImpl implements ActionListener {
    private Lazy<BlackBoxFragment> mLazyFragment;
    private BlackBoxEvent mRootBlackBoxEvent;
    private BlackBoxEvent mCurrentBlackBoxEvent;

    private ErrorAction mErrorAction;
    private Action mSuccess;

    public BlackBoxImpl(Lazy<BlackBoxFragment> lazyFragment) {
        mLazyFragment = lazyFragment;
    }

    public BlackBoxImpl with(BlackBoxEvent event) {
        return with(event, null);
    }

    public BlackBoxImpl with(BlackBoxEvent event, ErrorAction error) {
        if (mRootBlackBoxEvent == null) {
            mRootBlackBoxEvent = event;
            mCurrentBlackBoxEvent = event;
        } else {
            mCurrentBlackBoxEvent.setNextBlackBoxEvent(event);
            mCurrentBlackBoxEvent = event;
        }
        mCurrentBlackBoxEvent.setErrorAction(error);
        mCurrentBlackBoxEvent.setLazyFragment(mLazyFragment);
        return this;
    }

    public void execute(Action success) {
        execute(success, null);
    }

    public void execute(Action success, ErrorAction error) {
        mSuccess = success;
        mErrorAction = error;
        mCurrentBlackBoxEvent = mRootBlackBoxEvent;
        next();
    }

    private void next() {
        mCurrentBlackBoxEvent.setListener(this);
        mCurrentBlackBoxEvent.execute();
    }

    @Override
    public void onSuccess() {
        BlackBoxEvent nextBlackBoxEvent = mCurrentBlackBoxEvent.getNextBlackBoxEvent();
        if (nextBlackBoxEvent == null) {
            mSuccess.call();
        } else {
            mCurrentBlackBoxEvent = nextBlackBoxEvent;
            next();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        ErrorAction action = mCurrentBlackBoxEvent.getErrorAction();
        if (action != null && action.call(throwable)) {
            return;
        }
        if (mErrorAction != null) {
            mErrorAction.call(throwable);
        }
    }
}
