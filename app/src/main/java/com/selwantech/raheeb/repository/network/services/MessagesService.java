package com.selwantech.raheeb.repository.network.services;

import android.content.Context;

import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.model.ChatObject;
import com.selwantech.raheeb.model.chatdata.Chat;
import com.selwantech.raheeb.model.notificationsdata.Notification;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.repository.network.ApiCallHandler.ApiClient;
import com.selwantech.raheeb.repository.network.ApiCallHandler.CustomObserverResponse;
import com.selwantech.raheeb.repository.network.ApiCallHandler.GeneralResponse;
import com.selwantech.raheeb.repository.network.ApiConstants;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class MessagesService {

    private static MessagesService instance;
    private static int timeOutDB = 10;
    Context mContext;
    private DataApi mDataApi;

    private MessagesService() {
        mDataApi = ApiClient.getRetrofitClient(ApiConstants.BASE_URL).create(DataApi.class);
    }

    public static MessagesService getInstance() {
        if (instance == null) {
            instance = new MessagesService();
        }
        return instance;
    }

    public void getChats(Context mContext, boolean enableLoading, int skip, APICallBack<ArrayList<Chat>> apiCallBack) {
        getDataApi().getChats(skip)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<ArrayList<Chat>>(mContext, enableLoading, apiCallBack));
    }

    public void getNotifications(Context mContext, boolean enableLoading, int skip, APICallBack<ArrayList<Notification>> apiCallBack) {
        getDataApi().getNotifications(skip)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<ArrayList<Notification>>(mContext, enableLoading, apiCallBack));
    }

    public void getCurrency(Context mContext, boolean enableLoading, APICallBack<String> apiCallBack) {
        getDataApi().getCurrency()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<String>(mContext, enableLoading, apiCallBack));
    }


    public void getChatMessages(Context mContext, boolean enableLoading, int chatId, int skip, APICallBack<ArrayList<ChatObject>> apiCallBack) {
        getDataApi().getChatMessages(chatId, skip)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<ArrayList<ChatObject>>(mContext, enableLoading, apiCallBack));
    }

    public void sendTextMessage(Context mContext, boolean enableLoading, int chatId, String message,
                                String messageType, APICallBack<ChatObject> apiCallBack) {
        getDataApi().sendTextMessage(chatId, message, messageType)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<ChatObject>(mContext, enableLoading, apiCallBack));
    }

    public void sendImageMessage(Context mContext, boolean enableLoading, int chatId, String image,
                                 String messageType, APICallBack<ChatObject> apiCallBack) {
        getDataApi().senImageMessage(chatId, GeneralFunction.getImageMultipart(image, "message"), messageType)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<ChatObject>(mContext, enableLoading, apiCallBack));
    }

    public void acceptOffer(Context mContext, boolean enableLoading, int messageId, APICallBack<String> apiCallBack) {
        getDataApi().acceptOffer(messageId)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<String>(mContext, enableLoading, apiCallBack));
    }


    public DataApi getDataApi() {
        return mDataApi;
    }

    public interface DataApi {

        @GET(ApiConstants.apiMessagesService.CHATS)
        Single<Response<GeneralResponse<ArrayList<Chat>>>> getChats(@Query("skip") int skip);

        @GET(ApiConstants.apiMessagesService.CHAT_MESSAGES)
        Single<Response<GeneralResponse<ArrayList<ChatObject>>>> getChatMessages(@Path("chat_id") int chat_id,
                                                                                 @Query("skip") int skip);


        @GET(ApiConstants.apiMessagesService.NOTIFICATIONS)
        Single<Response<GeneralResponse<ArrayList<Notification>>>> getNotifications(@Query("skip") int skip);

        @GET(ApiConstants.apiAppService.CURRENCY)
        Single<Response<GeneralResponse<String>>> getCurrency();


        @POST(ApiConstants.apiMessagesService.SEND_MESSAGE)
        Single<Response<GeneralResponse<ChatObject>>> sendTextMessage(@Path("chat_id") int chatId,
                                                                      @Query("message") String message,
                                                                      @Query("message_type") String messageType);

        @Multipart
        @POST(ApiConstants.apiMessagesService.SEND_MESSAGE)
        Single<Response<GeneralResponse<ChatObject>>> senImageMessage(@Path("chat_id") int chatId,
                                                                      @Part MultipartBody.Part message,
                                                                      @Query("message_type") String messageType);

        @POST(ApiConstants.apiMessagesService.ACCEPT_OFFER)
        Single<Response<GeneralResponse<String>>> acceptOffer(@Path("messageId") int messageId);

    }
}


