/**
 * Copyright 2016 Erik Jhordan Rey.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.selwantech.raheeb.bind.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class People implements Serializable {

    @SerializedName("gender")
    private String gender;

    @SerializedName("name")
    private Name name;

    @SerializedName("location")
    private Location location;

    @SerializedName("email")
    private String mail;

    @SerializedName("login")
    private Login login;

    @SerializedName("cell")
    private String cell;

    @SerializedName("picture")
    private Picture picture;

    private String fullName;

    public String getGender() {
        return gender;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getFullName() {
        return name.getTitle() + "." + name.getFirst() + " " + name.getLast();
    }

    public boolean hasEmail() {
        return mail != null && !mail.isEmpty();
    }
}
