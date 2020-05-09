package com.selwantech.raheeb.repository.network.services;

import android.content.Context;

import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.model.ProfileResponse;
import com.selwantech.raheeb.model.RegisterResponse;
import com.selwantech.raheeb.model.SocialLogin;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.model.VerifyPhoneResponse;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.repository.network.ApiCallHandler.ApiClient;
import com.selwantech.raheeb.repository.network.ApiCallHandler.CustomObserverResponse;
import com.selwantech.raheeb.repository.network.ApiCallHandler.GeneralResponse;
import com.selwantech.raheeb.repository.network.ApiConstants;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public class AuthService {

    private static AuthService instance;
    private static int timeOutDB = 10;
    Context mContext;
    private DataApi mDataApi;

    private AuthService() {
        mDataApi = ApiClient.getRetrofitClient(ApiConstants.BASE_URL).create(DataApi.class);
    }

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }


    public void updateFirebaseToken(Context mContext, boolean withProgress, APICallBack apiCallBack) {
        getDataApi().updateFirebaseToken(
                SessionManager.getKeyFirebaseToken(),
                ApiConstants.PLATFORM)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<String>(mContext, withProgress, new APICallBack<String>() {
                    @Override
                    public void onSuccess(String response) {
                        apiCallBack.onSuccess(response);
                    }

                    @Override
                    public void onError(String error, int errorCode) {
                        apiCallBack.onError(error, errorCode);
                    }
                }));
    }

    public void logout(Context mContext, APICallBack apiCallBack) {
        getDataApi().logout(
                SessionManager.getKeyFirebaseToken())
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<String>(mContext, true, new APICallBack<String>() {
                    @Override
                    public void onSuccess(String response) {
                        apiCallBack.onSuccess(response);
                    }

                    @Override
                    public void onError(String error, int errorCode) {
                        apiCallBack.onError(error, errorCode);
                    }
                }));
    }

    public DataApi getDataApi() {
        return mDataApi;
    }

    public void reInit() {
        instance = new AuthService();
    }

    public interface DataApi {

        @POST(ApiConstants.apiAuthService.VERIFY_PHONE)
        Single<Response<GeneralResponse<VerifyPhoneResponse>>> verifyPhone(@Query("phone_number") String phoneNumber);

        @POST(ApiConstants.apiAuthService.VERIFY_CODE)
        Single<Response<GeneralResponse<String>>> verifyCode(@Query("token") String token,
                                                             @Query("code") String code);

        @POST(ApiConstants.apiAuthService.RESEND_CODE)
        Single<Response<GeneralResponse<VerifyPhoneResponse>>> resendCode(@Query("token") String token);

        @POST(ApiConstants.apiAuthService.REGISTER_USER)
        Single<Response<GeneralResponse<RegisterResponse>>> registerUser(@Body User user);

        @POST(ApiConstants.apiAuthService.LOGIN_USER)
        Single<Response<GeneralResponse<RegisterResponse>>> loginUser(@Query("phone_number") String phone_number,
                                                                      @Query("password") String password);

        @POST(ApiConstants.apiAuthService.LOGIN_SOCIAL)
        Single<Response<GeneralResponse<RegisterResponse>>> loginWithSocial(@Body SocialLogin socialLogin);

        @POST(ApiConstants.apiAuthService.FORGET_PASSWORD)
        Single<Response<GeneralResponse<VerifyPhoneResponse>>> forgetPassword(@Query("phone_number") String phoneNumber);

        @POST(ApiConstants.apiAuthService.CREATE_PASSWORD)
        Single<Response<GeneralResponse<String>>> createPassword(@Query("password") String password,
                                                                 @Query("password_confirmation") String phone_number,
                                                                 @Query("token") String token);

        @POST(ApiConstants.apiAuthService.UPDATE_FIREBASE_TOKEN)
        Single<Response<GeneralResponse<String>>> updateFirebaseToken(@Query("token") String token,
                                                                      @Query("platform") String platform);

        @POST(ApiConstants.apiAuthService.LOGOUT)
        Single<Response<GeneralResponse<String>>> logout(@Query("token") String token);

        @PATCH(ApiConstants.apiAuthService.UPDATE_PASSWORD)
        Single<Response<GeneralResponse<String>>> updatePassword(@Query("password") String password,
                                                                 @Query("password_confirmation") String password_confirmation,
                                                                 @Query("current_password") String current_password);

        @PATCH(ApiConstants.apiAuthService.UPDATE_PROFILE)
        Single<Response<GeneralResponse<ProfileResponse>>> updateProfile(@Query("email") String email,
                                                                         @Query("name") String name);

        @Multipart
        @POST(ApiConstants.apiAuthService.UPDATE_PROFILE_PICTURE)
        Single<Response<GeneralResponse<ProfileResponse>>> updateProfilePicture(@Part MultipartBody.Part image);

    }
}


