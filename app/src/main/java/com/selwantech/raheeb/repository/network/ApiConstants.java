package com.selwantech.raheeb.repository.network;

public class ApiConstants {
//    public static final String BASE_URL = "http://raheeb.selwantech.tech/api/";

    public static final String BASE_URL = "http://157.245.252.177/api/";
    public static final String GET_DATA = "user/1/categories";
    public static final String PLATFORM = "android";
    public static final String SOCKET_URL = "";

    public class apiAuthService {
        public static final String VERIFY_PHONE = "auth/verifyPhone";
        public static final String VERIFY_CODE = "auth/verifyPhone/check-code";
        public static final String RESEND_CODE = "auth/verifyPhone/reset-code";
        public static final String REGISTER_USER = "auth/new_register";
        public static final String LOGIN_USER = "auth/login";
        public static final String LOGIN_SOCIAL = "auth/login/socialMedia";
        public static final String FORGET_PASSWORD = "auth/forgotPassword/verifyPhone";
        public static final String CREATE_PASSWORD = "auth/forgotPassword/update/password";
        public static final String UPDATE_FIREBASE_TOKEN = "user/update/device-token";
        public static final String LOGOUT = "user/logout";
        public static final String UPDATE_PASSWORD = "user/update/password";
        public static final String UPDATE_PROFILE = "user/update/profile";
        public static final String UPDATE_PROFILE_PICTURE = "user/update/avatar";
    }

    public class apiProductService {
        public static final String PRODUCTS = "posts";
        public static final String PRODUCT = "posts/{productId}/show";
        public static final String PRICE_DETAILS = "user/post/sold/{productId}/calculate_price";
        public static final String ADD_FAVORITE = "user/post/{productId}/faverate";
        public static final String REMOVE_FAVORITE = "user/post/{productId}/unFaverate";
        public static final String MAKE_OFFER = "user/post/{productId}/offer";
        public static final String BUY_NOW = "user/post/sold/{productId}/buy_now";
        public static final String CREATE_PRODUCT = "user/post";
        public static final String SELLING = "user/post";
        public static final String BUYING = "user/post/buying";
        public static final String FAVORITE = "user/faverate";
        public static final String INTERACTIVE_PEOPLE = "user/post/{product_id}/mark_sold";
        public static final String SET_SOLD = "user/post/sold/{product_id}/set_sold";
    }

    public class apiCategoryService {
        public static final String ALL_CATEGORIES = "categories";
        public static final String CATEGORY_BOX_SIZE = "categories/{categoryId}/boxsize";
    }

    public class apiUserService {
        public static final String GET_USER = "users/{userId}/profile";
        public static final String FOLLOW_USER = "user/follower/follow";
        public static final String UNFOLLOW_USER = "user/follower/unfollow";
        public static final String GET_MY_OFFERS = "users/{userId}/offers";
    }

    public class apiOrderService {
        public static final String UPLOAD_ORDER_IMAGES = "user/customer/orders/up-image";
        public static final String CANCELLATION_FEE = "user/customer/orders/wrongOrderCharge/{orderId}";
        public static final String CANCEL_ORDER = "user/customer/orders/cancel/{orderId}";
        public static final String SUBMIT_ORDER = "user/customer/orders/submit";
        public static final String CALCULATE_PRICE = "user/customer/orders/calculatePrice/{serviceId}";
        public static final String CALCULATE_OFFER_BUT_LIMITED = "user/customer/orders/calcPriceOfferButLimited/{serviceId}";
        public static final String ACTIVE_ORDER = "user/customer/orders/active";
        public static final String HISTORY_ORDER = "user/customer/orders/previous";
        public static final String UNRATED_ORDER = "user/customer/orders/getOrdersNotRate";
        public static final String ORDER_DETAILS = "user/customer/orders/{orderId}/show";
        public static final String RATE_ORDERS = "user/customer/orders/orderRate/{orderId}";
        public static final String OFFERS = "user/customer/orders/{orderId}/offers";
        public static final String HIRE_WORKER = "user/customer/orders/hireWorker/{orderId}/{offerId}";

    }

    public class apiAppService {
        public static final String PRIVACY_POLICY = "app/privacy-policy";
        public static final String TERMS_AND_CONDITION = "app/terms-and-condations";
        public static final String DISTANCES = "app/distance";
        public static final String CONDITION = "condition_type";
        public static final String CURRENCY = "app/currency";

    }

    public class apiMessagesService {
        public static final String CHATS = "user/chat";
        public static final String NOTIFICATIONS = "user/notifications";
        public static final String CHAT_MESSAGES = "user/chat/{chat_id}/messages";
    }

}
