package com.selwantech.raheeb.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.selwantech.raheeb.ui.adapter.AddImagesAdapter;

import java.util.Collections;
import java.util.List;

import static androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_DRAG;
import static androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_IDLE;
import static androidx.recyclerview.widget.ItemTouchHelper.END;
import static androidx.recyclerview.widget.ItemTouchHelper.START;

public class ItemTouchCallBack extends ItemTouchHelper.Callback {

    AddImagesAdapter adapter;
    List list ;


    public ItemTouchCallBack(AddImagesAdapter adapter, List list) {
        this.adapter = adapter;
        this.list = list;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (adapter.getItem(viewHolder.getAdapterPosition()) == null) {
            return makeFlag(ACTION_STATE_DRAG, ACTION_STATE_IDLE);
        }

        return makeFlag(ACTION_STATE_DRAG,
                START | END);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

        if (adapter.getItem(target.getAdapterPosition()) != null) {
            Collections.swap(list, viewHolder.getAdapterPosition(), target.getAdapterPosition());
            adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }

        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
