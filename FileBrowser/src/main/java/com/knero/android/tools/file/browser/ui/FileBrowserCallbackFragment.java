package com.knero.android.tools.file.browser.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.knero.android.tools.file.browser.FileBrowser;
import com.knero.android.tools.file.browser.filter.Filter;

import java.util.Arrays;

/**
 * @author zhaocheng.luo
 * @since 2019-08-22
 */
public class FileBrowserCallbackFragment extends Fragment {


    public static final String FILE_BROWSER_FRAGMENT_TAG = "com.knero.android.tools.file.browser";

    public static Lazy<FileBrowserCallbackFragment> createLazyFragemnt(final FragmentManager manager) {
        return new Lazy<FileBrowserCallbackFragment>() {
            private FileBrowserCallbackFragment mFragment;

            @Override
            public FileBrowserCallbackFragment get() {
                if (mFragment == null) {
                    mFragment = createFragemnt(manager);
                }
                return mFragment;
            }
        };
    }

    private static FileBrowserCallbackFragment createFragemnt(FragmentManager manager) {
        FileBrowserCallbackFragment fragment = findFragment(manager);
        boolean isNewInstance = fragment == null;
        if (isNewInstance) {
            fragment = new FileBrowserCallbackFragment();
            manager.beginTransaction().add(fragment, FILE_BROWSER_FRAGMENT_TAG)
                    .commitNow();
        }
        return fragment;
    }

    private static <T extends Fragment> T findFragment(FragmentManager manager) {
        return (T) manager.findFragmentByTag(FILE_BROWSER_FRAGMENT_TAG);
    }

    public static final String KEY_SELECT_COUNT = "Select_count";
    public static final String KEY_FILE_FILTER = "File_filter";

    private final int FILE_LIST_REQUEST_CODE = 10;
    private FileBrowser.OnFileListener mOnFileListener;

    public void start(int selectCount, Filter filter, FileBrowser.OnFileListener listener) {
        mOnFileListener = listener;
        Intent intent = new Intent(getContext(), FileBrowserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_SELECT_COUNT, 1);
        intent.putExtras(bundle);
        IntentListenerManager.putListener(KEY_FILE_FILTER, filter);
        startActivityForResult(intent, FILE_LIST_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_LIST_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String path = data.getStringExtra("data");
                mOnFileListener.onSuccess(Arrays.asList(path));
                mOnFileListener = null;
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                mOnFileListener.onCancel();
                mOnFileListener = null;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public interface Lazy<T> {
        T get();
    }
}

