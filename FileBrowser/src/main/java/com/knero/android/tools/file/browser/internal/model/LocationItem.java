package com.knero.android.tools.file.browser.internal.model;

import java.io.File;

/**
 * Created by luozhaocheng on 20/06/2017.
 */

public class LocationItem {

    private String mShowName;

    private File mFile;

    private int mPosition;

    public LocationItem(String showName, File file, int position) {
        mShowName = showName;
        mFile = file;
        mPosition = position;
    }

    public String getShowName() {
        return mShowName;
    }

    public File getFile() {
        return mFile;
    }

    public int getPosition() {
        return mPosition;
    }
}
