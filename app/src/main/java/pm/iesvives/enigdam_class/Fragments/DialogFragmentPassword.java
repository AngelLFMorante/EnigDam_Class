package pm.iesvives.enigdam_class.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import cn.pedant.SweetAlert.SweetAlertDialog;
import pm.iesvives.enigdam_class.R;

public class DialogFragmentPassword extends DialogFragment {


    private View view;
    private String titlePassword;
    private String titleSuccess;
    private String loadingText;
    private EditText passwordTextField;
    private String titlePasswordIncorrect;
    private SweetAlertDialog pDialog;
    private SharedPreferences state;
    private SharedPreferences.Editor stateEdit;
    private boolean isCorrect = false;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        String btnOk = getResources().getString(R.string.btnOk);
        String btnCancel = getResources().getString(R.string.btnCancel);
        state = getActivity().getSharedPreferences("States", getContext().MODE_PRIVATE);
        stateEdit = state.edit();

        titlePassword = getResources().getString(R.string.titlePassword);
        titleSuccess = getResources().getString(R.string.success);
        loadingText = getResources().getString(R.string.loading);
        titlePasswordIncorrect = getResources().getString(R.string.passwordIncorrect);
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_password, null);

        passwordTextField = view.findViewById(R.id.password);

        dialogBuilder.setView(view)
                .setTitle(titlePassword)
                .setPositiveButton(btnOk, (dialog, which) -> {
                    pDialog = new SweetAlertDialog(view.getContext(), SweetAlertDialog.PROGRESS_TYPE)
                            .setTitleText(loadingText);
                    pDialog.show();
                    pDialog.setCancelable(false);
                   passwordCheck(passwordTextField);
                })
                .setNegativeButton(btnCancel, (dialog, which) -> dismiss());

        return dialogBuilder.create();
    }

    private void passwordCheck(EditText passwordTextField) {
        DialogEndZone2 dialog;
        String password = passwordTextField.getText().toString();

                if (password.toUpperCase().equals("ANGEL" )) {
                    pDialog.setTitleText(titleSuccess)
                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    pDialog.cancel();
                    isCorrect = true;
                    stateEdit.putBoolean("z2IsCorrectPassword", isCorrect);
                    stateEdit.commit();
                    dialog = new DialogEndZone2();
                    dialog.show(getActivity().getSupportFragmentManager(), "DialogZone2");
                }else{
                    pDialog.setTitleText(titlePasswordIncorrect)
                            .setContentText("")
                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    stateEdit.putBoolean("z2IsCorrectPassword", isCorrect);
                    stateEdit.commit();
                }
    }


}