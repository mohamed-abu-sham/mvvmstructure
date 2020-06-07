package com.selwantech.raheeb.enums;

public enum FilterProductResultsTypes {
    LOCATION(1),
    SORT(2),
    PRICE(3),
    SEARCH(4);

    private int typeID;

    FilterProductResultsTypes(int i) {
        this.typeID = i;
    }

    public static FilterProductResultsTypes fromInt(int i) {
        for (FilterProductResultsTypes type : FilterProductResultsTypes.values()) {
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
