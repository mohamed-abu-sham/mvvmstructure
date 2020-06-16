package com.selwantech.raheeb.enums;

public enum FollowStatus {
    FOLLOW(1),
    UNFOLLOW(2),
    NON(3);

    private int typeID;

    FollowStatus(int i) {
        this.typeID = i;
    }

    public static FollowStatus fromInt(int i) {
        for (FollowStatus type : FollowStatus.values()) {
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
