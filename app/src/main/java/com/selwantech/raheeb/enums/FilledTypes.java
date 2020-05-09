package com.selwantech.raheeb.enums;

public enum FilledTypes {
    NOT_FILLED(1),
    FILLED(2),
    ERROR_FILLED(3);

    private int typeID;

    FilledTypes(int i) {
        this.typeID = i;
    }

    public static FilledTypes fromInt(int i) {
        for (FilledTypes type : FilledTypes.values()) {
            if (type.getMode() == i) {
                return type;
            }
        }
        return null;
    }

    public int getMode() {
        return typeID;
    }
}
