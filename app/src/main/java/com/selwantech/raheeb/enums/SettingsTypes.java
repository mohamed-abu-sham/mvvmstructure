package com.selwantech.raheeb.enums;

public enum SettingsTypes {
    PHONE_NUMBER(0),
    EMAIL(1),
    IMAGE(2),
    TWITTER(3),
    PASSWORD(4),
    LOCATION(5);

    private int typeID;

    SettingsTypes(int i) {
        this.typeID = i;
    }

    public static SettingsTypes fromInt(int i) {
        for (SettingsTypes type : SettingsTypes.values()) {
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
