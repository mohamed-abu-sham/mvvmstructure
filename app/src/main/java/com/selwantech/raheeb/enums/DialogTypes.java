package com.selwantech.raheeb.enums;

public enum DialogTypes {
    OK(1),
    OK_CANCEL(2);

    private int typeID;

    DialogTypes(int i) {
        this.typeID = i;
    }

    public static DialogTypes fromInt(int i) {
        for (DialogTypes type : DialogTypes.values()) {
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
