package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.content.Intent;


import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.accountjourney.about.AboutViewModel;
import com.selwantech.raheeb.ui.accountjourney.changelanguage.ChangeLanguageViewModel;
import com.selwantech.raheeb.ui.accountjourney.help.HelpViewModel;
import com.selwantech.raheeb.ui.accountjourney.updateprofilepicture.UpdateProfilePictureViewModel;
import com.selwantech.raheeb.ui.auth.changepassword.ChangePasswordViewModel;
import com.selwantech.raheeb.ui.auth.createpassword.CreatePasswordViewModel;
import com.selwantech.raheeb.ui.auth.forgetpassword.ForgetPasswordViewModel;
import com.selwantech.raheeb.ui.auth.login.LoginViewModel;
import com.selwantech.raheeb.ui.auth.otpverifier.OtpVerifierViewModel;
import com.selwantech.raheeb.ui.auth.otpverifiertoupdate.OtpVerifierToUpdateViewModel;
import com.selwantech.raheeb.ui.auth.phonenumber.PhoneNumberViewModel;
import com.selwantech.raheeb.ui.auth.register.RegisterViewModel;
import com.selwantech.raheeb.ui.standard.emptyactivity.EmptyActivityViewModel;
import com.selwantech.raheeb.ui.standard.emptyfragment.EmptyFragmentViewModel;
import com.selwantech.raheeb.ui.main.MainActivityViewModel;
import com.selwantech.raheeb.ui.mappicker.MapPickerViewModel;
import com.selwantech.raheeb.ui.splashscreen.SplashScreenViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * @ViewDataBinding : Base class for generated data binding classes
 * @ViewModel: The purpose of the ViewModel is to acquire and keep the information that is necessary for an
 * Activity or a Fragment. The Activity or the Fragment should be able to observe changes in the
 * ViewModel. ViewModels usually expose this information via LiveData or Android Data
 * Binding. You can also use any observability construct from you favorite framework.
 * <p>
 * ViewModel's only responsibility is to manage the data for the UI. It <b>should never</b> access
 * your view hierarchy or hold a reference back to the Activity or the Fragment.
 * <p>
 */
@Singleton
public class ViewModelProviderFactory<V extends ViewDataBinding>
        extends ViewModelProvider.NewInstanceFactory {

    private final DataManager dataManager;
    private final Context mContext;


    @Inject
    public ViewModelProviderFactory(DataManager dataManager, Context mContext) {
        this.dataManager = dataManager;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return super.create(modelClass);
    }

    public <T extends ViewModel> T create(Class<T> modelClass, V viewDataBinding, Intent intent) {
        if (modelClass.isAssignableFrom(EmptyFragmentViewModel.class)) {
            //noinspection unchecked
            return (T) new EmptyFragmentViewModel(mContext, dataManager, viewDataBinding, intent);
        } else if (modelClass.isAssignableFrom(EmptyActivityViewModel.class)) {
            //noinspection unchecked
            return (T) new EmptyActivityViewModel(mContext, dataManager, viewDataBinding, intent);
        } else if (modelClass.isAssignableFrom(SplashScreenViewModel.class)) {
            //noinspection unchecked
            return (T) new SplashScreenViewModel(mContext, dataManager, viewDataBinding, intent);
        }else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            //noinspection unchecked
            return (T) new LoginViewModel(mContext, dataManager, viewDataBinding, intent);
        } else if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            //noinspection unchecked
            return (T) new RegisterViewModel(mContext, dataManager, viewDataBinding, intent);
        } else if (modelClass.isAssignableFrom(PhoneNumberViewModel.class)) {
            //noinspection unchecked
            return (T) new PhoneNumberViewModel(mContext, dataManager, viewDataBinding, intent);
        } else if (modelClass.isAssignableFrom(OtpVerifierViewModel.class)) {
            //noinspection unchecked
            return (T) new OtpVerifierViewModel(mContext, dataManager, viewDataBinding, intent);
        } else if (modelClass.isAssignableFrom(ChangePasswordViewModel.class)) {
            //noinspection unchecked
            return (T) new ChangePasswordViewModel(mContext, dataManager, viewDataBinding, intent);
        } else if (modelClass.isAssignableFrom(CreatePasswordViewModel.class)) {
            //noinspection unchecked
            return (T) new CreatePasswordViewModel(mContext, dataManager, viewDataBinding, intent);
        }else if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            //noinspection unchecked
            return (T) new MainActivityViewModel(mContext, dataManager, viewDataBinding, intent);
        } else if (modelClass.isAssignableFrom(MapPickerViewModel.class)) {
            //noinspection unchecked
            return (T) new MapPickerViewModel(mContext, dataManager, viewDataBinding, intent);
        }else if (modelClass.isAssignableFrom(HelpViewModel.class)) {
            //noinspection unchecked
            return (T) new HelpViewModel(mContext, dataManager, viewDataBinding, intent);
        } else if (modelClass.isAssignableFrom(AboutViewModel.class)) {
            //noinspection unchecked
            return (T) new AboutViewModel(mContext, dataManager, viewDataBinding, intent);
        } else if (modelClass.isAssignableFrom(PhoneNumberViewModel.class)) {
            //noinspection unchecked
            return (T) new PhoneNumberViewModel(mContext, dataManager, viewDataBinding, intent);
        } else if (modelClass.isAssignableFrom(ForgetPasswordViewModel.class)) {
            //noinspection unchecked
            return (T) new ForgetPasswordViewModel(mContext, dataManager, viewDataBinding, intent);
        } else if (modelClass.isAssignableFrom(ChangeLanguageViewModel.class)) {
            //noinspection unchecked
            return (T) new ChangeLanguageViewModel(mContext, dataManager, viewDataBinding, intent);
        } else if (modelClass.isAssignableFrom(UpdateProfilePictureViewModel.class)) {
            //noinspection unchecked
            return (T) new UpdateProfilePictureViewModel(mContext, dataManager, viewDataBinding, intent);
        } else if (modelClass.isAssignableFrom(OtpVerifierToUpdateViewModel.class)) {
            //noinspection unchecked
            return (T) new OtpVerifierToUpdateViewModel(mContext, dataManager, viewDataBinding, intent);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }

}