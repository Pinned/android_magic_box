package com.knero.android.tools.file.browser;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;


import com.knero.android.tools.file.browser.filter.Filter;
import com.knero.android.tools.file.browser.ui.FileBrowserCallbackFragment;

import java.util.List;

/**
 * Created by luozhaocheng on 19/06/2017.
 */

public class FileBrowser {

    private FileBrowserCallbackFragment.Lazy<FileBrowserCallbackFragment> mLazyFragment;
    private int mSelectCount = -1;
    private Filter mFilter;

    private FileBrowser(FragmentActivity activity) {
        this(activity.getSupportFragmentManager());
    }

    private FileBrowser(Fragment fragment) {
        this(fragment.getChildFragmentManager());
    }

    private FileBrowser(final FragmentManager manager) {
        mLazyFragment = FileBrowserCallbackFragment.createLazyFragemnt(manager);
    }


    public static FileBrowser of(FragmentActivity activity) {
        return new FileBrowser(activity);
    }

    public static FileBrowser of(Fragment fragment) {
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

    public void start(OnFileListener listener) {
        mLazyFragment.get().start(mSelectCount, mFilter, listener);
    }


    public interface OnFileListener {
        void onSuccess(List<String> files);

        void onCancel();
    }

}
