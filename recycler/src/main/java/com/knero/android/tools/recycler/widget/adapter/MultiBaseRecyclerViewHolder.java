package com.knero.android.tools.recycler.widget.adapter;

import android.content.Context;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.IntDef;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Knero on 6/22/16.
 */
public abstract class MultiBaseRecyclerViewHolder<DATA> extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews = new SparseArray<>();

    protected Context mContext;
    protected MultiBaseRecyclerViewHolder<DATA> holder;
    protected MultiBaseRecyclerAdapter mAdapter;

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public MultiBaseRecyclerAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(MultiBaseRecyclerAdapter adapter) {
        mAdapter = adapter;
    }

    /**
     * @param itemView itemView
     */
    public MultiBaseRecyclerViewHolder(View itemView) {
        super(itemView);
        holder = this;
    }

    protected abstract void convert(DATA data, int position);

    /**
     * 返回对应view
     *
     * @param resId view resource Id
     * @param <T>   返回view的类型
     * @return 返回View
     */
    public <T extends View> T getView(@IdRes int resId) {
        View view = mViews.get(resId);
        if (view == null) {
            view = itemView.findViewById(resId);
            mViews.put(resId, view);
        }
        return (T) view;
    }

    /**
     * 给ImageView设置背景
     *
     * @param imgViewResId ImageView对应资源ID
     * @param imgResId     图片资源ID
     */
    public void setImageView(@IdRes int imgViewResId, @DrawableRes int imgResId) {
        ImageView view = getView(imgViewResId);
        view.setBackgroundResource(imgResId);
    }

    /**
     * 设置View的可见性
     *
     * @param resId   View的Resource ID
     * @param visible 是否可见
     */
    public void setVisible(@IdRes int resId, @VisibleType int visible) {
        View view = getView(resId);
        view.setVisibility(visible);
    }

    /**
     * 设置字体颜色
     *
     * @param resId     TextView ResId
     * @param textColor 字体颜色
     */
    public void setTextColor(@IdRes int resId, int textColor) {
        TextView textView = getView(resId);
        textView.setTextColor(textColor);
    }

    /**
     * 设置View的选中状态
     *
     * @param resId    View的Resource ID
     * @param selected 是不选中，如果为true, 则选中
     */
    public void setViewSelected(@IdRes int resId, boolean selected) {
        View view = getView(resId);
        view.setSelected(selected);
    }

    /**
     * 设置View的背景颜色
     *
     * @param resId View的Resource ID
     * @param color 颜色值
     */
    public void setViewBackground(@IdRes int resId, int color) {
        View view = getView(resId);
        view.setBackgroundColor(color);
    }

    /**
     * 设置文字
     *
     * @param resId TextView ResId
     * @param text  文字内容
     */
    public void setText(@IdRes int resId, String text) {
        TextView view = getView(resId);
        view.setText(text);
    }

    /**
     * 设置文字
     *
     * @param resId    TextView ResId
     * @param txtResId String res id
     */
    public void setText(@IdRes int resId, @StringRes int txtResId) {
        TextView view = getView(resId);
        view.setText(txtResId);
    }

    /**
     * 设置View的点击事件
     *
     * @param resId           View的Resource ID
     * @param onClickListener 点击事件监听器
     */
    public void setViewOnClickListener(@IdRes int resId, View.OnClickListener onClickListener) {
        View view = getView(resId);
        view.setOnClickListener(onClickListener);
    }

    /**
     * 给整个Item设置点击事件
     *
     * @param onItemClickListener onClickListener
     */
    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.itemView.setOnClickListener(onItemClickListener);
    }

    /**
     * 当被长按选中时、或取消选中是执行
     *
     * @param selected 是否选中
     */
    public void onSelectedChanged(boolean selected) {

    }

    /**
     * 取每一个Item的View
     *
     * @return 返回整个Item
     */
    public View getItemView() {
        return itemView;
    }

    @IntDef({
            View.VISIBLE,
            View.INVISIBLE,
            View.GONE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface VisibleType {

    }
}
