package com.selwantech.raheeb.viewmodel;

import android.content.Context;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.auth.chooseusertype.ChooseUserTypeViewModel;
import com.selwantech.raheeb.ui.auth.createpassword.CreatePasswordViewModel;
import com.selwantech.raheeb.ui.auth.login.LoginViewModel;
import com.selwantech.raheeb.ui.auth.otpverifier.OtpVerifierViewModel;
import com.selwantech.raheeb.ui.auth.phonenumber.PhoneNumberViewModel;
import com.selwantech.raheeb.ui.auth.register.RegisterViewModel;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.category.CategoryViewModel;
import com.selwantech.raheeb.ui.emptyactivity.EmptyActivityViewModel;
import com.selwantech.raheeb.ui.emptyfragment.EmptyFragmentViewModel;
import com.selwantech.raheeb.ui.filebox.FileBoxViewModel;
import com.selwantech.raheeb.ui.main.MainActivityViewModel;
import com.selwantech.raheeb.ui.mappicker.MapPickerViewModel;
import com.selwantech.raheeb.ui.menufragments.filterproductslocation.FilterProductLocationViewModel;
import com.selwantech.raheeb.ui.menufragments.product.ProductViewModel;
import com.selwantech.raheeb.ui.menufragments.productdetails.ProductDetailsViewModel;
import com.selwantech.raheeb.ui.menufragments.productstabsholder.ProductsHolderViewModel;
import com.selwantech.raheeb.ui.splashscreen.SplashScreenViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;

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
public class ViewModelProviderFactory<V extends ViewDataBinding, N extends BaseNavigator>
        extends ViewModelProvider.NewInstanceFactory {

    private final DataManager dataManager;
    private final Context mContext;


    @Inject
    public ViewModelProviderFactory(DataManager dataManager, Context mContext) {
        this.dataManager = dataManager;
        this.mContext = mContext;
    }

    public <T extends ViewModel> T create(Class<T> modelClass, V viewDataBinding, N navigation) {
        if (modelClass.isAssignableFrom(EmptyFragmentViewModel.class)) {
            //noinspection unchecked
            return (T) new EmptyFragmentViewModel(mContext, dataManager, viewDataBinding, navigation);
        } else if (modelClass.isAssignableFrom(EmptyActivityViewModel.class)) {
            //noinspection unchecked
            return (T) new EmptyActivityViewModel(mContext, dataManager, viewDataBinding, navigation);
        } else if (modelClass.isAssignableFrom(SplashScreenViewModel.class)) {
            //noinspection unchecked
            return (T) new SplashScreenViewModel(mContext, dataManager, viewDataBinding, navigation);
        }else if (modelClass.isAssignableFrom(ChooseUserTypeViewModel.class)) {
            //noinspection unchecked
            return (T) new ChooseUserTypeViewModel(mContext, dataManager, viewDataBinding, navigation);
        } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            //noinspection unchecked
            return (T) new LoginViewModel(mContext, dataManager, viewDataBinding, navigation);
        } else if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            //noinspection unchecked
            return (T) new RegisterViewModel(mContext, dataManager, viewDataBinding, navigation);
        } else if (modelClass.isAssignableFrom(FileBoxViewModel.class)) {
            //noinspection unchecked
            return (T) new FileBoxViewModel(mContext, dataManager, viewDataBinding, navigation);
        } else if (modelClass.isAssignableFrom(PhoneNumberViewModel.class)) {
            //noinspection unchecked
            return (T) new PhoneNumberViewModel(mContext, dataManager, viewDataBinding, navigation);
        } else if (modelClass.isAssignableFrom(OtpVerifierViewModel.class)) {
            //noinspection unchecked
            return (T) new OtpVerifierViewModel(mContext, dataManager, viewDataBinding, navigation);
        } else if (modelClass.isAssignableFrom(CreatePasswordViewModel.class)) {
            //noinspection unchecked
            return (T) new CreatePasswordViewModel(mContext, dataManager, viewDataBinding, navigation);
        }else if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            //noinspection unchecked
            return (T) new MainActivityViewModel(mContext, dataManager, viewDataBinding, navigation);
        }
        else if (modelClass.isAssignableFrom(ProductsHolderViewModel.class)) {
            //noinspection unchecked
            return (T) new ProductsHolderViewModel(mContext, dataManager, viewDataBinding, navigation);
        }else if (modelClass.isAssignableFrom(ProductViewModel.class)) {
            //noinspection unchecked
            return (T) new ProductViewModel(mContext, dataManager, viewDataBinding, navigation);
        }else if (modelClass.isAssignableFrom(FilterProductLocationViewModel.class)) {
            //noinspection unchecked
            return (T) new FilterProductLocationViewModel(mContext, dataManager, viewDataBinding, navigation);
        }else if (modelClass.isAssignableFrom(CategoryViewModel.class)) {
            //noinspection unchecked
            return (T) new CategoryViewModel(mContext, dataManager, viewDataBinding, navigation);
        } else if (modelClass.isAssignableFrom(MapPickerViewModel.class)) {
            //noinspection unchecked
            return (T) new MapPickerViewModel(mContext, dataManager, viewDataBinding, navigation);
        } else if (modelClass.isAssignableFrom(ProductDetailsViewModel.class)) {
            //noinspection unchecked
            return (T) new ProductDetailsViewModel(mContext, dataManager, viewDataBinding, navigation);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }

}