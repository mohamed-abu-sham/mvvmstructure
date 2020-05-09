package com.selwantech.raheeb.enums;

public enum SeekKMTypes {
    KM5(0),
    KM10(25),
    KM20(50),
    KM30(75),
    MAX(100);

    private int typeID;

    SeekKMTypes(int i) {
        this.typeID = i;
    }

    public static SeekKMTypes fromInt(int i) {
        for (SeekKMTypes type : SeekKMTypes.values()) {
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
