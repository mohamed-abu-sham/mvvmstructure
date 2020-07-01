package com.selwantech.raheeb.utils;


public final class AppConstants {


    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
    public static final String PLATFORM = "android";

    private AppConstants() {

    }

    public class BundleData {

        public static final String PRODUCT = "PRODUCT";
        public static final String REQUEST_TYPE = "REQUEST_TYPE";
        public static final String FILTER_LOCATION = "FILTER_LOCATION";
        public static final String TYPE = "TYPE";
        public static final String ADDRESS = "ADDRESS";
        public static final String PRODUCT_ID = "PRODUCT_ID";
        public static final String POST = "POST";
        public static final String PRODUCT_OWNER = "PRODUCT_OWNER";
        public static final String MY_OFFER = "MY_OFFER";
        public static final String USER_ID = "USER_ID";
        public static final String SELLING_ITEM = "SELLING_ITEM";
        public static final String KEY_CHAT_ID = "KEY_CHAT_ID";
        public static final String CHAT = "CHAT";
        public static final String CHAT_ID = "CHAT_ID";
        public static final String BUYING_ITEM = "BUYING_ITEM";
        public static final String TOKEN = "TOKEN";
        public static final String INVITE_TOKEN = "INVITE_TOKEN";
        public static final String CHAT_POSITION = "CHAT_POSITION";
        public static final String NOTIFICATION = "NOTIFICATION";
        public static final String PAYMENT_ACTION = "PAYMENT_ACTION";
    }

    public class FILTER_BY_KEYS {

        public static final String LOCATION = "LOCATION";
        public static final String PRICE = "PRICE";
        public static final String DISTANCE = "DISTANCE";
        public static final String ORDER_BY = "ORDER_BY";
        public static final String CATEGORY = "CATEGORY";
    }

    public class PRODUCT_STATUS {
        public static final String AVAILABLE = "available";
        public static final String SOLD_OTHER_APP = "sold_other_app";
        public static final String SOLD = "sold";
    }

    public class MESSAGE_TYPE {
        public static final String OFFER = "offer";
        public static final String TEXT = "text";
        public static final String VOICE = "voice";
    }

    public class OFFER_STATUS {
        public static final String APPROVED = "approved";
        public static final String WAITING = "waiting";
        public static final String REJECT = "reject";
    }

    public class LOGGED_IN_TYPE {
        public static final String RAHEEB = "raheeb";
        public static final String TWITTER = "twitter";
    }

    public class ORDERING_TYPE {
        public static final String DISTANCE = "distance";
        public static final String DATE = "date";
        public static final String PRICE = "price";
    }
}
