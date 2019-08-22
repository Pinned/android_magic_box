package com.knero.android.tools.file.browser.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by luozhaocheng on 20/06/2017.
 */

public class DateTimeUtils {

    public static final SimpleDateFormat FILE_LAST_MODIFY = new SimpleDateFormat("yy-MM-dd HH:mm");

    public static String getFileLastModifyTime(File file) {
        return FILE_LAST_MODIFY.format(new Date(file.lastModified()));
    }

}
