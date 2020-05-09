package com.selwantech.raheeb.enums;

public enum OrderHiringStatus {
    TAKEN(1),
    DECLINED(2);

    private int typeID;

    OrderHiringStatus(int i) {
        this.typeID = i;
    }

    public static OrderHiringStatus fromInt(int i) {
        for (OrderHiringStatus type : OrderHiringStatus.values()) {
            if (type.getStatus() == i) {
                return type;
            }
        }
        return null;
    }

    public int getStatus() {
        return typeID;
    }
}
