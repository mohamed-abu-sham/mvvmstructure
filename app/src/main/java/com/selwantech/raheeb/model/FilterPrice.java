package com.selwantech.raheeb.model;

import java.io.Serializable;

public class FilterPrice implements Serializable {

    double min ;
    double max ;

    public FilterPrice(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
}
