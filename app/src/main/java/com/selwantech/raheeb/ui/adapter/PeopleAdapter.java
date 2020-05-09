/**
 * Copyright 2016 Erik Jhordan Rey.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.selwantech.raheeb.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.selwantech.raheeb.R;
import com.selwantech.raheeb.bind.model.People;
import com.selwantech.raheeb.bind.viewmodel.ItemPeopleViewModel;
import com.selwantech.raheeb.databinding.ItemPeopleBinding;

import java.util.Collections;
import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleAdapterViewHolder> {

    private List<People> peopleList;

    PeopleAdapter() {
        this.peopleList = Collections.emptyList();
    }

    @NonNull
    @Override
    public PeopleAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPeopleBinding itemPeopleBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_people, parent, false);
        return new PeopleAdapterViewHolder(itemPeopleBinding);
    }

    @Override
    public void onBindViewHolder(PeopleAdapterViewHolder holder, int position) {
        holder.bindPeople(peopleList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return peopleList.size();
    }

    void setPeopleList(List<People> peopleList) {
        this.peopleList = peopleList;
        notifyDataSetChanged();
    }

    static class PeopleAdapterViewHolder extends RecyclerView.ViewHolder {
        ItemPeopleBinding binding;

        PeopleAdapterViewHolder(ItemPeopleBinding binding) {
            super(binding.itemPeople);
            this.binding = binding;
        }

        void bindPeople(People people,int position ) {
            if (binding.getPeopleViewModel() == null) {
                binding.setPeopleViewModel(new ItemPeopleViewModel(itemView.getContext(), people,position));
            } else {
                binding.getPeopleViewModel().setPeople(people);
            }
        }
    }
}
