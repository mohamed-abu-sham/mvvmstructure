package com.selwantech.raheeb.enums;

public enum PhoneNumberTypes {
    FORGET_PASSWORD(1),
    CHANGE_PHONE_NUMBER(2),
    REGISTER(3),
    REGISTER_SOCIAL(4);

    private int typeID;

    PhoneNumberTypes(int i) {
        this.typeID = i;
    }

    public static PhoneNumberTypes fromInt(int i) {
        for (PhoneNumberTypes type : PhoneNumberTypes.values()) {
            if (type.getValue() == i) {
                return type;
            }
        }
        return null;
    }

    public int getValue() {
        return typeID;
    }
}
