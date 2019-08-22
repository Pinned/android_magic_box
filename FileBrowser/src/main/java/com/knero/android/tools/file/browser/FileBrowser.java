package com.knero.android.tools.file.browser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.knero.android.tools.file.browser.filter.Filter;
import com.knero.android.tools.file.browser.ui.FileBrowserActivity;
import com.knero.android.tools.file.browser.ui.IntentListenerManager;

import java.lang.ref.WeakReference;

/**
 * Created by luozhaocheng on 19/06/2017.
 */

public class FileBrowser {

    private WeakReference<Activity> mContext;
    private WeakReference<Fragment> mFragment;
    public static final String KEY_SELECT_COUNT = "Select_count";
    public static final String KEY_FILE_FILTER = "File_filter";
    private int mSelectCount = -1;
    private Filter mFilter;

    private FileBrowser(Activity activity) {
        this(activity, null);
    }

    private FileBrowser(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    private FileBrowser(Activity activity, Fragment fragment) {
        mContext = new WeakReference<>(activity);
        mFragment = new WeakReference<>(fragment);
    }

    public static FileBrowser from(Activity activity) {
        return new FileBrowser(activity);
    }

    public static FileBrowser from(Fragment fragment) {
        return new FileBrowser(fragment);
    }

    public FileBrowser selectCount(int count) {
        mSelectCount = count;
        return this;
    }

    public FileBrowser filter(Filter filter) {
        mFilter = filter;
        return this;
    }

    public void forResult(int requestCode) {
        Activity activity = mContext.get();
        if (activity == null) {
            return;
        }

        Intent intent = new Intent(activity, FileBrowserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_SELECT_COUNT, mSelectCount);
        intent.putExtras(bundle);
        IntentListenerManager.putListener(KEY_FILE_FILTER, mFilter);
        Fragment fragment = mFragment.get();
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }
}
