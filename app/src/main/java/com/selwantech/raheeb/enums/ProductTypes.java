package com.selwantech.raheeb.enums;

public enum ProductTypes {
    ALL(1),
    PICKUP(2),
    SHIPPING(3);

    private int typeID;

    ProductTypes(int i) {
        this.typeID = i;
    }

    public static ProductTypes fromInt(int i) {
        for (ProductTypes type : ProductTypes.values()) {
            if (type.getTypeValue() == i) {
                return type;
            }
        }
        return null;
    }

    public int getTypeValue() {
        return typeID;
    }
}
