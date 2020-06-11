package com.selwantech.raheeb.enums;

public enum SellingItemClickTypes {
    ITEM_CLICK(1),
    SELL_FASTER_CLICK(2),
    MARK_SOLD_CLICK(3);

    private int typeID;

    SellingItemClickTypes(int i) {
        this.typeID = i;
    }

    public static SellingItemClickTypes fromInt(int i) {
        for (SellingItemClickTypes type : SellingItemClickTypes.values()) {
            if (type.getTypeID() == i) {
                return type;
            }
        }
        return null;
    }

    public int getTypeID() {
        return typeID;
    }
}
