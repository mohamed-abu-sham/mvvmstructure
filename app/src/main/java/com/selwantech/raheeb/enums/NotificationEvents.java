package com.selwantech.raheeb.enums;

public enum NotificationEvents {
    NEW_MESSAGE("message"),
    TYPE_OFFER("offer"),
    TYPE_RATE("rate"),
    TYPE_RATED("rated"),
    TYPE_ADMIN("admin"),
    TYPE_APPROVED_OFFER("approved_offer"),
    TYPE_REJECT_OFFER("reject_offer"),
    TYPE_FOLLOWING("following"),
    TYPE_FAVORITE("favorite"),
    TYPE_BUY_NOW("buy_now");

    private String event;

    NotificationEvents(String i) {
        this.event = i;
    }

    public static NotificationEvents fromString(String i) {
        for (NotificationEvents type : NotificationEvents.values()) {
            if (type.getEvent().equals(i)) {
                return type;
            }
        }
        return null;
    }

    public String getEvent() {
        return event;
    }
}
