
package com.knero.android.tools.file.browser.ui.widget.location;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class LocationLayout extends LinearLayout {

    public LocationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setChildrenDrawingOrderEnabled(true);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        return childCount - i - 1;
    }
}
