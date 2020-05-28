package com.selwantech.raheeb.utils;


public final class AppConstants {

    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;

    public static final String DB_NAME = "mindorks_mvvm.db";

    public static final long NULL_INDEX = -1L;

    public static final String PREF_NAME = "mindorks_pref";

    public static final String SEED_DATABASE_OPTIONS = "seed/options.json";

    public static final String SEED_DATABASE_QUESTIONS = "seed/questions.json";

    public static final String STATUS_CODE_FAILED = "failed";

    public static final String STATUS_CODE_SUCCESS = "success";

    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
    public static final String PLATFORM = "android";
    public static final String CHOICES = "choices";

    //    Service Extra
    public static final String NUMBERED = "numbered";
    public static final String BOOLEAN = "boolean";
    public static final String TEXT_NUMBER = "text_number";
    public static final String TEXT_NUMBER_DECIMAL = "text_number_decimal";
    public static final String TEXT_LONG = "text_long";
    public static final String LOCATION = "location";
    public static final String ANY_TIME_NO_EXTRA_PRICE = "any_time_no_extra_price";

    //  Service Timing Type
    public static final String EMERGENCY_ALLOWED_BUT_EXTRA_PRICED = "emergency_allowed_but_extra_priced";
    public static final String NO_EMERGENCY = "no_emergency";
    public static final String EMERGENCY_ONLY = "emergency_only";
    public static final String EMERGENCY = "em";

    //  Order Timing type
    public static final String LATER = "later";
    public static final String GOOGLE = "google";

    //   Social login
    public static final String FACEBOOK = "facebook";
    public static final String CONSTANT = "constant";

    //  Service Pricing Type
    public static final String OFFERS_BUT_LIMITED = "offers_but_limited";
    public static final String OFFERS = "offers";
    public static final String HOUR_BASED = "hour_based";
    public static final String ONLY_ORDER = "only_order";

    //   Service  Delivery Type
    public static final String CHARGED_CONSTANT = "charged_constant";
    public static final String FREE = "free";
    public static final String ORDER_BY_HOUR = "by_hour";

    //    Order status
    public static final String ORDER_CONSTANT = "constant";

    //    ORDER Pricing Type
    public static final String ORDER_OFFER = "offer";

    private AppConstants() {

    }

    public class OrderStatus {
        public static final String AWAITING_OFFERS = "awaiting_offers";
        public static final String NO_GOOD_OFFERS = "no_good_offers";
        public static final String OFFER_CHOSEN = "offer_chosen";
        public static final String WORKER_INT_THE_WAY = "worker_in_the_way";
        public static final String SERVICE_STARTED = "service_started";
        public static final String SERVICE_ENDED = "service_ended";
        public static final String WRONG_ORDER = "wrong_order";
    }

    public class NotificationTypes {

        public static final String ORDER_EXPIRATION = "ORDER_EXPIRATION";
        public static final String NEW_ORDER = "NEW_ORDER";
        public static final String NEW_ORDER_OFFER = "NEW_ORDER_OFFER";
        public static final String ORDER_TAKEN = "ORDER_TAKEN";
        public static final String ORDER_UPDATE_STATUS = "ORDER_UPDATE_STATUS";
        public static final String ORDER_NEW_OFFER = "ORDER_NEW_OFFER";
        public static final String ORDER_CANCEL = "ORDER_CANCEL";
        public static final String ORDER_COMPLEATED = "ORDER_COMPLEATED";
    }

    public class BundleData {

        public static final String PRODUCT = "PRODUCT";
        public static final String REQUEST_TYPE = "REQUEST_TYPE";
        public static final String FILTER_LOCATION = "FILTER_LOCATION";
        public static final String TYPE = "TYPE";
        public static final String ADDRESS = "ADDRESS";
    }
}
