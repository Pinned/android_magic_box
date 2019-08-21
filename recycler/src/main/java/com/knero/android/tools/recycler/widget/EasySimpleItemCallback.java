package com.knero.android.tools.recycler.widget;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.knero.android.tools.recycler.widget.adapter.MultiBaseRecyclerAdapter;
import com.knero.android.tools.recycler.widget.adapter.MultiBaseRecyclerViewHolder;

/**
 * Created by Knero on 26/10/2016.
 */

class EasySimpleItemCallback extends ItemTouchHelper.Callback {

    private boolean mMoveable = false;

    @Override
    public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
        LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            return makeMovementFlags(dragFlag, 0);
        } else if (layoutManager instanceof LinearLayoutManager) {
            int dragFlag;
            if (((LinearLayoutManager) layoutManager).getOrientation() == OrientationHelper.VERTICAL) {
                dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            } else {
                dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            }
            return makeMovementFlags(dragFlag, 0);
        }
        return 0;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, ViewHolder source, ViewHolder target) {
        ((MultiBaseRecyclerAdapter) recyclerView.getAdapter()).onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(ViewHolder viewHolder, int direction) {
    }

    @Override
    public void onSelectedChanged(ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            ((MultiBaseRecyclerViewHolder) viewHolder).onSelectedChanged(actionState == 2);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
        if (viewHolder != null) {
            ((MultiBaseRecyclerViewHolder) viewHolder).onSelectedChanged(false);
        }
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return mMoveable;
    }

    public void setMoveable(boolean moveable) {
        mMoveable = moveable;
    }
}
