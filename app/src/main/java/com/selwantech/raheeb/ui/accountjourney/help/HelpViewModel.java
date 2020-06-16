package com.selwantech.raheeb.ui.accountjourney.help;

import android.content.Context;
import android.net.Uri;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentHelpBinding;
import com.selwantech.raheeb.enums.DialogTypes;
import com.selwantech.raheeb.enums.PickImageTypes;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.OnLineDialog;
import com.selwantech.raheeb.ui.dialog.PickImageFragmentDialog;
import com.selwantech.raheeb.utils.PickImageUtility;
import com.selwantech.raheeb.utils.SnackViewBulider;

import androidx.databinding.ViewDataBinding;

public class HelpViewModel extends BaseViewModel<HelpNavigator, FragmentHelpBinding> {

    private Uri imageUri;

    public <V extends ViewDataBinding, N extends BaseNavigator> HelpViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (HelpNavigator) navigation, (FragmentHelpBinding) viewDataBinding);

    }


    @Override
    protected void setUp() {

    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public void pickPictureClick() {
        PickImageFragmentDialog pickImageFragmentDialog = new PickImageFragmentDialog.Builder().build();
        pickImageFragmentDialog.setMethodCallBack(new PickImageFragmentDialog.methodClick() {
            @Override
            public void onMethodBack(int type) {
                if (type == PickImageTypes.GALLERY.getIntValue()) {
                    PickImageUtility.selectImage(getBaseActivity());
                } else {
                    PickImageUtility.TakePictureIntent(getBaseActivity());
                }
            }
        });
        pickImageFragmentDialog.show(getBaseActivity().getSupportFragmentManager(), "picker");
    }

    public void onSendClicked(){
        if(isValid()){
            if(imageUri!=null){
                getDataManager().getAppService().sendHelpWithImage(getMyContext(), true,
                        getViewBinding().edMessage.getText().toString(), imageUri, new APICallBack<String>() {
                            @Override
                            public void onSuccess(String response) {
                                showSuccessDialog();
                            }

                            @Override
                            public void onError(String error, int errorCode) {
                                showErrorSnackBar(error);
                            }
                        });
            }else{
                getDataManager().getAppService().sendHelp(getMyContext(), true,
                        getViewBinding().edMessage.getText().toString(), new APICallBack<String>() {
                            @Override
                            public void onSuccess(String response) {
                                showSuccessDialog();
                            }

                            @Override
                            public void onError(String error, int errorCode) {
                                showErrorSnackBar(error);
                            }
                        });
            }
        }
    }

    private void showErrorSnackBar(String error) {
        showSnackBar(getMyContext().getResources().getString(R.string.error),
                error, getMyContext().getResources().getString(R.string.ok), new SnackViewBulider.SnackbarCallback() {
                    @Override
                    public void onActionClick(Snackbar snackbar) {
                        snackbar.dismiss();
                    }
                });
    }

    private void showSuccessDialog() {
        new OnLineDialog(getMyContext()) {
            @Override
            public void onPositiveButtonClicked() {
                dismiss();
                popUp();
            }

            @Override
            public void onNegativeButtonClicked() {
                dismiss();
            }
        }.showConfirmationDialog(DialogTypes.OK,getMyContext().getResources().getString(R.string.success),
                getMyContext().getResources().getString(R.string.report_sent_successful));
    }

    private boolean isValid() {
        int error = 0 ;

        if(getViewBinding().edMessage.getText().toString().trim().isEmpty()){
            getViewBinding().edMessage.setError(getMyContext().getResources().getString(R.string.this_fieled_is_required));
            error++;
        }
        return error == 0 ;
    }


}
