package com.selwantech.raheeb.model;

import java.io.Serializable;

public class FilterDate implements Serializable {

    String name ;
    boolean selected ;
    public FilterDate(String name, boolean selected) {
        this.name = name;
        this.selected = selected ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
