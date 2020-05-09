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

package com.selwantech.raheeb.bind.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.databinding.BaseObservable;

import com.selwantech.raheeb.bind.model.People;


public class ItemPeopleViewModel extends BaseObservable {

    private final Context context;
    private People people;
    private int position ;

    public ItemPeopleViewModel(Context context, People people, int position ) {
        this.context = context;
        this.people = people;
        this.position = position ;
    }

    public People  getPeople(){
        return people;
    }
    public String getFullName() {
        return people.getName().getTitle() + "." + people.getName().getFirst() + " " + people.getName().getLast();
    }

    public String getCell() {
        return people.getCell();
    }

    public String getMail() {
        return people.getMail();
    }

    public String getPictureProfile() {
        return people.getPicture().getMedium();
    }

    public void onItemClick(View view) {

    }

    public void setPeople(People people) {
        this.people = people;
        notifyChange();
    }
}
