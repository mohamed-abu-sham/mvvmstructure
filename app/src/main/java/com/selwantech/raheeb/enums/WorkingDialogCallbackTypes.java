package com.selwantech.raheeb.enums;

public enum WorkingDialogCallbackTypes {
    CALL(1),
    MESSAGE(2),
    TRACK(3);

    private int typeID;

    WorkingDialogCallbackTypes(int i) {
        this.typeID = i;
    }

    public static WorkingDialogCallbackTypes fromInt(int i) {
        for (WorkingDialogCallbackTypes type : WorkingDialogCallbackTypes.values()) {
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
