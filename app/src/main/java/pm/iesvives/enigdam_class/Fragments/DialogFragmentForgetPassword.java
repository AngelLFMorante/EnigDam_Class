package pm.iesvives.enigdam_class.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import cn.pedant.SweetAlert.SweetAlertDialog;
import pm.iesvives.enigdam_class.R;
import pm.iesvives.enigdam_class.Utils.Settings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DialogFragmentForgetPassword extends DialogFragment {

    private View view;
    private String titlePassword;
    private String titleSuccess;
    private String loadingText;
    private String sendEmail;
    private EditText emailUser;
    private SweetAlertDialog pDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        String btnOk = getResources().getString(R.string.btnOk);
        String btnCancel = getResources().getString(R.string.btnCancel);

        titlePassword = getResources().getString(R.string.titleForgetPassword);
        titleSuccess = getResources().getString(R.string.success);
        loadingText = getResources().getString(R.string.loading);
        sendEmail = getResources().getString(R.string.sendForgetPassword);
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_forget_password, null);

        emailUser = view.findViewById(R.id.forgetPasswordDialog);

        dialogBuilder.setView(view)
                .setTitle(titlePassword)
                .setPositiveButton(btnOk, (dialog, which) -> {
                    sendEmailToServer(emailUser.getText().toString());
                    Toast.makeText(getContext(), sendEmail, Toast.LENGTH_LONG).show();
                })
                .setNegativeButton(btnCancel, (dialog, which) -> dismiss());

        return dialogBuilder.create();
    }

    private void sendEmailToServer(String email) {

        Settings.RESPONSE_CLIENT.getService().forgetPassword(email).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });

    }

}