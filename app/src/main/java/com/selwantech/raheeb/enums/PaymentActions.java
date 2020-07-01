package com.selwantech.raheeb.enums;

public enum PaymentActions {
    CASH_IN(1),
    BUY_PRODUCT(2);

    private int action;

    PaymentActions(int i) {
        this.action = i;
    }

    public static PaymentActions fromInt(int i) {
        for (PaymentActions action : PaymentActions.values()) {
            if (action.getAction() == i) {
                return action;
            }
        }
        return null;
    }

    public int getAction() {
        return action;
    }
}
