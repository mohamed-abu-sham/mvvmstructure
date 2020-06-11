package com.selwantech.raheeb.helper;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.selwantech.raheeb.model.SetSold;

import java.io.IOException;

public class SetSoldGsonConverter extends TypeAdapter<SetSold> {

    @Override
    public void write(JsonWriter out, SetSold value) throws IOException {

        out.beginObject();
        if (value.getUserId() != -1) {
            out.name("user_id");
            out.value(value.getUserId());
        }

        if (value.getRate() != -1) {
            out.name("rate");
            out.value(value.getRate());
        }


        out.endObject();
    }

    @Override
    public SetSold read(JsonReader in) throws IOException {
        // do something similar, but the other way around
        return null;
    }
}