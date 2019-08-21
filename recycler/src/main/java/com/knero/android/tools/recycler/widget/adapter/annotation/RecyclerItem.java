package com.knero.android.tools.recycler.widget.adapter.annotation;


import com.knero.android.tools.recycler.widget.adapter.DefaultBaseRecyclerViewHolder;

/**
 * Created by luozhaocheng on 03/08/2017.
 */
public @interface RecyclerItem {

    public int type() default 0;

    public Class<?> className() default DefaultBaseRecyclerViewHolder.class;

    public int layoutResId() default -1;
}
