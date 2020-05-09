package com.selwantech.raheeb.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static androidx.recyclerview.widget.ItemTouchHelper.*;

public class ItemTouchCallBack extends ItemTouchHelper.Callback {

    RecyclerView.Adapter adapter ;
    List list ;


    public ItemTouchCallBack(RecyclerView.Adapter adapter, List list) {
        this.adapter = adapter;
        this.list = list;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeFlag(ACTION_STATE_DRAG,
                DOWN | UP | START | END);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        Collections.swap(list, viewHolder.getAdapterPosition(), target.getAdapterPosition());
        adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
