package com.selwantech.raheeb.model;

import java.io.Serializable;

public class FilterBy implements Serializable {

    String key;
    String filterName;
    String filterValue;
    boolean available = true;

    public FilterBy(String key, String filterName, String filterValue) {
        this.key = key;
        this.filterName = filterName;
        this.filterValue = filterValue;

    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getKey() {
        return key;
    }
}
