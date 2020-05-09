package com.selwantech.raheeb.enums;

public enum HiringResult {
    NO_RESULT_YET(1),
    FAILED_TO_HIRE(2),
    HIRED(3);

    private int typeID;

    HiringResult(int i) {
        this.typeID = i;
    }

    public static HiringResult fromInt(int i) {
        for (HiringResult type : HiringResult.values()) {
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
