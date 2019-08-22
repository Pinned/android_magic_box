package com.knero.android.tools.file.browser.ui.widget.location;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.knero.android.tools.file.browser.R;
import com.knero.android.tools.file.browser.internal.model.LocationItem;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by luozhaocheng on 20/06/2017.
 */

public class LocationBarView extends FrameLayout {

    private ViewGroup mPathContainer;
    private HorizontalScrollView mPathContainerWrapper;
    private LayoutInflater mInflater;
    private ArrayList<LocationItem> mPathLists;
    private RecycleBin mRecycleBin;
    private LocationBarClickListener mListener;

    public LocationBarView(Context context) {
        super(context);
        init();
    }

    public LocationBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LocationBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_location_bar, this);
        mPathContainer = findViewById(R.id.container);
        mPathContainerWrapper = findViewById(R.id.container_wrapper);
        mInflater = LayoutInflater.from(getContext());
        mPathLists = new ArrayList<>();
        mRecycleBin = new RecycleBin();
        mRecycleBin.setViewTypeCount(1);
    }

    public void setLocationClickListener(LocationBarClickListener listener) {
        mListener = listener;
    }

    private View createItem(final LocationItem item) {
        View view = mRecycleBin.getScrapView(mPathLists.size(), 0);
        ViewHolder viewHolder;
        if (view == null) {
            view = mInflater.inflate(R.layout.location_bar_item, mPathContainer, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
            ViewParent vp = view.getParent();
            if (vp instanceof ViewGroup) {
                ((ViewGroup) vp).removeView(view);
            }
        }
        viewHolder.mTextView.setText(item.getShowName());
        viewHolder.mRootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<LocationItem> temp = new ArrayList<>();
                for (LocationItem locationNode : mPathLists) {
                    temp.add(locationNode);
                    if (locationNode.getFile().equals(item.getFile())) {
                        break;
                    }
                }
                mPathLists.clear();
                setNodeList(temp);
                if (mListener != null && mListener.isCanNodeClick()) {
                    mListener.onLocationNodeClick(item);
                }
            }
        });
        return view;
    }

    private void appendDir(LocationItem item) {
        mPathContainer.addView(createItem(item));
        mPathLists.add(item);
    }

    public void setNodeList(List<LocationItem> nodeList) {
        List<LocationItem> nodes = new ArrayList<>();
        nodes.addAll(mPathLists);
        mPathLists.clear();
        int childCount = mPathContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            mRecycleBin.addScrapView(mPathContainer.getChildAt(i), i, 0);
        }
        mPathContainer.removeAllViews();
        for (LocationItem node : nodeList) {
            appendDir(node);
        }
        mPathContainer.post(mScrollToRightRunnable);
    }

    public void addLocation(final LocationItem item) {
        List<LocationItem> temp = new ArrayList<LocationItem>() {{
            addAll(mPathLists);
            add(item);
        }};
        setNodeList(temp);
    }

    private final Runnable mScrollToRightRunnable = new Runnable() {
        @Override
        public void run() {
            if (mPathContainerWrapper != null) {
                mPathContainerWrapper.fullScroll(View.FOCUS_RIGHT);
            }
        }
    };

    private class ViewHolder {

        private View mRootView;
        private TextView mTextView;

        private ViewHolder(View v) {
            mRootView = v;
            mTextView = (TextView) v.findViewById(R.id.title);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.
                    LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            Drawable drawable = getResources().getDrawable(R.drawable.btn_location_arrow);
            lp.leftMargin = -drawable.getIntrinsicWidth();
            mTextView.setLayoutParams(lp);
        }
    }

    public interface LocationBarClickListener {

        void onLocationNodeClick(LocationItem item);

        boolean isCanNodeClick();
    }

}
