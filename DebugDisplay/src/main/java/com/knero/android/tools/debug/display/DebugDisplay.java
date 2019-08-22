package com.knero.android.tools.debug.display;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author zhaocheng.luo
 * @since 2019-08-22
 */
public class DebugDisplay {
    private static abstract class Holder {
        public static final DebugDisplay INSTANCE = new DebugDisplay();
    }

    public static DebugDisplay instance() {
        return Holder.INSTANCE;
    }

    private ArrayList<String> mAllShowTexts;
    private int mMaxShowCount;
    private TextView mTextView;
    private int sActivityCount;
    private boolean mCreateTextViewResult;
    private String[] ingnores = new String[]{
            "BlackBoxFragment",
            "ReportFragment",
    };

    public void init(Application application) {
        init(application, 5);
    }

    public void init(Application application, int maxShowCount) {
        createTextView(application);
        this.mMaxShowCount = maxShowCount;
        this.mAllShowTexts = new ArrayList(this.mMaxShowCount);

        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                DebugDisplay.instance().showText(activity, activity.getClass().getSimpleName());
                try {
                    SupportFragmentLifecycle.set(activity);
                } catch (Throwable localThrowable1) {
                }
                try {
                    FragmentLifecycle.set(activity);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

            public void onActivityStarted(Activity activity) {
                DebugDisplay.this.sActivityCount = (DebugDisplay.this.sActivityCount <= 0 ? 1 : DebugDisplay.this.sActivityCount + 1);
                if ((DebugDisplay.this.sActivityCount > 0) && (DebugDisplay.this.mTextView != null)) {
                    DebugDisplay.this.mTextView.setVisibility(View.VISIBLE);
                }
            }

            public void onActivityResumed(Activity activity) {
            }

            public void onActivityPaused(Activity activity) {
            }

            public void onActivityStopped(Activity activity) {
                sActivityCount -= 1;
                if ((DebugDisplay.this.sActivityCount <= 0) && (DebugDisplay.this.mTextView != null)) {
                    DebugDisplay.this.mTextView.setVisibility(View.GONE);
                }
            }

            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            public void onActivityDestroyed(Activity activity) {
            }
        });
    }


    public void showText(final Activity activity, final String text) {
        if (Arrays.asList(ingnores).contains(text)) {
            return;
        }
        if (!this.mCreateTextViewResult) {
            new DebugDisplayPermissionManager(activity).getOverlayPermission(new DebugDisplayPermissionCallback() {
                @Override
                public void onResult(boolean success) {
                    if (success) {
                        DebugDisplay.this.createTextView(activity);
                        DebugDisplay.this.showText(text);
                    }
                }
            });
        } else {
            showText(text);
        }
    }


    private void showText(String text) {
        if (this.mTextView == null) {
            return;
        }
        this.mAllShowTexts.add(text);
        while (this.mAllShowTexts.size() > this.mMaxShowCount) {
            this.mAllShowTexts.remove(0);
        }
        StringBuffer sb = new StringBuffer();
        for (String showText : this.mAllShowTexts) {
            sb.append(showText);
            sb.append("\n");
        }
        this.mTextView.setText(sb.toString());
    }

    private void createTextView(Context context) {
        if (context == null) {
            return;
        }
        this.mTextView = new TextView(context);
        WindowManager wmManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams(-2, -2);
        if (Build.VERSION.SDK_INT >= 26) {
            wmParams.type = 2038;
        } else if (Build.VERSION.SDK_INT > 24) {
            wmParams.type = 2002;
        } else {
            wmParams.type = 2005;
        }
        wmParams.format = 1;
        wmParams.flags = 56;
        wmParams.gravity = 53;
        wmParams.x = 0;
        wmParams.y = 0;
        this.mTextView.setGravity(5);
        this.mTextView.setTextColor(-65536);
        try {
            wmManager.addView(this.mTextView, wmParams);
            this.mCreateTextViewResult = true;
        } catch (Exception e) {
            e.printStackTrace();
            this.mTextView = null;
            this.mCreateTextViewResult = false;
        }
    }

}
