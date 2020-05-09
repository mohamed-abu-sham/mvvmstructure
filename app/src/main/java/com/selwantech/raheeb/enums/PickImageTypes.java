package com.selwantech.raheeb.enums;

public enum PickImageTypes {
    GALLERY(1),
    CAMERA(2);

    private int typeID;

    PickImageTypes(int i) {
        this.typeID = i;
    }

    public static PickImageTypes fromInt(int i) {
        for (PickImageTypes type : PickImageTypes.values()) {
            if (type.getIntValue() == i) {
                return type;
            }
        }
        return null;
    }

    public int getIntValue() {
        return typeID;
    }
}
