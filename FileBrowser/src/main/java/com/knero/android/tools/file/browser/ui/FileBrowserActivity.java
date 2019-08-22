package com.knero.android.tools.file.browser.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.knero.android.tools.blackbox.Action;
import com.knero.android.tools.blackbox.BlackBox;
import com.knero.android.tools.blackbox.internal.PermissionEvent;
import com.knero.android.tools.file.browser.FileBrowser;
import com.knero.android.tools.file.browser.R;
import com.knero.android.tools.file.browser.filter.Filter;
import com.knero.android.tools.file.browser.internal.model.LocationItem;
import com.knero.android.tools.file.browser.ui.adapter.FileListAdapter;
import com.knero.android.tools.file.browser.ui.widget.location.LocationBarView;
import com.knero.android.tools.recycler.widget.RecyclerViewHelper;
import com.knero.android.tools.status.MultiStatusView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by luozhaocheng on 19/06/2017.
 */

public class FileBrowserActivity extends AppCompatActivity implements LocationBarView.LocationBarClickListener {
    public static final String KEY_FILE_BROWSER_TITLE = "key.file.browser.title";
    private String mTitle;
    private int mSelectCount = -1;

    private LocationBarView mLocationBarView;
    private MultiStatusView mMultiStatusView;
    private RecyclerView mRecyclerView;
    private FileListAdapter mAdapter;
    private RecyclerViewHelper mHelper;
    private View mBottomBarLayout;
    private Filter mFilter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_file_broser);
        initData(savedInstanceState);
        setToolbar();
        setRealContentView();
        setBottomBar();
    }

    private void initData(Bundle saveInstance) {
        if (saveInstance == null) {
            saveInstance = getIntent().getExtras();
        }
        loadDataFromBundle(saveInstance);
    }

    private void loadDataFromBundle(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        mTitle = bundle.getString(KEY_FILE_BROWSER_TITLE);
        mSelectCount = bundle.getInt(FileBrowserCallbackFragment.KEY_SELECT_COUNT, -1);
        Object listener = IntentListenerManager.getListener(FileBrowserCallbackFragment.KEY_FILE_FILTER);
        if (listener != null) {
            mFilter = (Filter) listener;
        } else {
            mFilter = new Filter() {
                @Override
                public boolean filter(String name) {
                    return false;
                }
            };
        }
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TypedArray ta = getTheme().obtainStyledAttributes(new int[]{
                R.attr.toolbar_back_color, R.attr.toolbar_back_icon});
        int color = ta.getColor(0, 0);
        int icon = ta.getResourceId(1, -1);
        ta.recycle();
        if (icon != -1) {
            toolbar.setNavigationIcon(icon);
        }
        Drawable navigationIcon = toolbar.getNavigationIcon();
        navigationIcon.setColorFilter(color, PorterDuff.Mode.SRC_IN);

        if (TextUtils.isEmpty(mTitle)) {
            mTitle = getString(R.string.file_browser_title);
        }
        toolbar.setTitle(mTitle);
        toolbar.setTitleTextColor(color);
    }

    private void setRealContentView() {
        mMultiStatusView = findViewById(R.id.multi_status_view);
        mMultiStatusView.showContent();
        mLocationBarView = findViewById(R.id.location_bar);
        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new FileListAdapter(this);
        mAdapter.registerViewHolder(R.layout.layout_file_item);
        mHelper = new RecyclerViewHelper.Builder()
                .setRecyclerView(mRecyclerView)
                .setColumnsNum(1)
                .setOrientation(OrientationHelper.VERTICAL)
                .setAdapter(mAdapter)
                .useDefaultDecoration()
                .build(this);
        mHelper.setOnClickListener(new RecyclerViewHelper.OnClickListener() {
            @Override
            public void onClick(RecyclerView.ViewHolder viewHolder) {
                File file = mAdapter.getData(viewHolder.getAdapterPosition());
                if (file.isDirectory()) {
                    mLocationBarView.addLocation(new LocationItem(file.getName(), file, 1));
                    loadData(file);
                } else {
                    if (!mFilter.filter(file.getName())) {
                        Intent intent = new Intent();
                        intent.putExtra("data", file.getAbsolutePath());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        });
        final File rootFile = Environment.getExternalStorageDirectory();
        mLocationBarView.setNodeList(new ArrayList<LocationItem>() {{
            add(new LocationItem("根目录", rootFile, 0));
        }});
        mLocationBarView.setLocationClickListener(this);
        loadData(rootFile);
    }

    private void setBottomBar() {
        mBottomBarLayout = findViewById(R.id.bottom_toolbar);
        if (mSelectCount == 1) {
            mBottomBarLayout.setVisibility(View.GONE);
        } else {
            mBottomBarLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void loadData(final File file) {
        BlackBox.of(this)
                .with(new PermissionEvent(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                .execute(new Action() {
                    @Override
                    public void call() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                List<File> files = getLocalFiles(file);
                                showFiles(files);
                            }
                        }).start();
                    }
                });
    }

    private void showFiles(final List<File> files) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.updateItems(files);
                mAdapter.notifyDataSetChanged();
                if (files.size() <= 0) {
                    mMultiStatusView.showEmpty("当前文件夹为空");
                } else {
                    mMultiStatusView.showContent();
                }
            }
        });
    }

    private List<File> getLocalFiles(File file) {
        List<File> localFiles = new ArrayList<>();

        File[] files = file.listFiles();
        if (files == null || files.length <= 0) {
            return localFiles;
        }
        List<File> folderList = new ArrayList<>();
        List<File> fileList = new ArrayList<>();
        for (File childFile : files) {
            String fileName = childFile.getName();
            if (fileName.startsWith(".") || childFile.isHidden() || childFile.length() == 0) {
                continue;
            }
            if (childFile.isDirectory()) {
                folderList.add(childFile);
            } else {
                fileList.add(childFile);
            }
        }

        Comparator<File> comparator = new Comparator<File>() {
            @Override
            public int compare(File a, File b) {
                if (a != null && b != null) {
                    return a.getName().compareToIgnoreCase(b.getName());
                } else {
                    return 0;
                }
            }
        };
        Collections.sort(folderList, comparator);
        localFiles.addAll(folderList);
        Collections.sort(fileList, comparator);
        localFiles.addAll(fileList);

        return localFiles;
    }

    @Override
    public void onLocationNodeClick(LocationItem item) {
        loadData(item.getFile());
    }

    @Override
    public boolean isCanNodeClick() {
        return true;
    }
}
