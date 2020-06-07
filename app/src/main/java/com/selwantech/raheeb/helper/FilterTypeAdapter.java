package com.selwantech.raheeb.helper;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.selwantech.raheeb.model.FilterProduct;

import java.io.IOException;

public class FilterTypeAdapter extends TypeAdapter<FilterProduct> {

    @Override
    public void write(JsonWriter out, FilterProduct value) throws IOException {

        out.beginObject();
        if (value.getLat() != 0.0) {
            out.name("lat");
            out.value(value.getLat());
        }

        if (value.getLon() != 0.0) {
            out.name("lon");
            out.value(value.getLon());
        }
        if (value.getPrice_min() != 0.0) {
            out.name("price_min");
            out.value(value.getPrice_min());
        }

        if (value.getPrice_max() != 0.0) {
            out.name("price_max");
            out.value(value.getPrice_max());
        }

        if (value.isShipping()) {
            out.name("shipping");
            out.value(value.isShipping());
        }

        if (value.isPick_up()) {
            out.name("pick_up");
            out.value(value.isPick_up());
        }

        out.name("distance");
        out.value(value.getDistance());

        if (value.getTitle() != null
                && !value.getTitle().isEmpty()) {
            out.name("title");
            out.value(value.getTitle());
        }

        if (value.getOrdering() != null
                && !value.getOrdering().isEmpty()) {
            out.name("ordering");
            out.value(value.getOrdering());
        }

        out.endObject();
    }

    @Override
    public FilterProduct read(JsonReader in) throws IOException {
        // do something similar, but the other way around
        return null;
    }
}