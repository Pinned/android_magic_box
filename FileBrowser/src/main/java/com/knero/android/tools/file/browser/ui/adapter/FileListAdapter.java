package com.knero.android.tools.file.browser.ui.adapter;

import android.content.Context;

import com.knero.android.tools.file.browser.R;
import com.knero.android.tools.file.browser.utils.DateTimeUtils;
import com.knero.android.tools.file.browser.utils.FileThumbnailUtil;
import com.knero.android.tools.file.browser.utils.SizeUtils;
import com.knero.android.tools.recycler.widget.adapter.MultiBaseRecyclerAdapter;
import com.knero.android.tools.recycler.widget.adapter.MultiBaseRecyclerViewHolder;

import java.io.File;

/**
 * Created by luozhaocheng on 20/06/2017.
 */

public class FileListAdapter extends MultiBaseRecyclerAdapter<File> {

    public FileListAdapter(Context context) {
        super(context);
    }

    @Override
    public void convert(MultiBaseRecyclerViewHolder<File> holder, File item, int position) {
        super.convert(holder, item, position);
        if (item.isDirectory()) {
            holder.setImageView(R.id.file_item_iv_icon, R.drawable.ic_type_folder);
        } else {
            holder.setImageView(R.id.file_item_iv_icon,
                    FileThumbnailUtil.getIcon(FileThumbnailUtil.getExtName(item.getName())));
            holder.setText(R.id.file_item_tv_size, SizeUtils.format(item.length()));
        }
        holder.setText(R.id.file_item_tv_name, item.getName());

        holder.setText(R.id.file_item_tv_time, DateTimeUtils.getFileLastModifyTime(item));
    }
}
