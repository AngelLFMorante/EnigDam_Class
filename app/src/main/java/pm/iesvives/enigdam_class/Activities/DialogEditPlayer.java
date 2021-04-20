package pm.iesvives.enigdam_class.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.util.ArrayList;
import java.util.List;
import cn.pedant.SweetAlert.SweetAlertDialog;
import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.R;
import pm.iesvives.enigdam_class.Utils.Settings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogEditPlayer extends DialogFragment {

    public interface OnInputListener{
        void sendInput(PlayerDto player, String input);
    }
    public OnInputListener mOnInputListener;

    private View view;
    private String titleUsername;
    private String messageUserExist;
    private String titleSuccess;
    private String usernameNoEdit;
    private String loadingText;
    private String usernameEdited;
    private EditText usernameTextField;
    private int typeError;
    private int id;
    private SharedPreferences.Editor editorShared;
    private SweetAlertDialog pDialog;
    private PlayerDto editedPlayer = new PlayerDto();
    private List<PlayerDto> players = new ArrayList<>();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        String editTitle = getResources().getString(R.string.editTitle);
        String editMessage = getResources().getString(R.string.editMessage);
        String btnOk = getResources().getString(R.string.btnOk);
        String btnCancel = getResources().getString(R.string.btnCancel);
        messageUserExist = getResources().getString(R.string.messageErrorUsername);
        titleUsername = getResources().getString(R.string.titleEditPlayer);
        titleSuccess = getResources().getString(R.string.success);
        usernameNoEdit = getResources().getString(R.string.messageEditPlayerNoEdit);
        loadingText = getResources().getString(R.string.loading);
        view = getActivity().getLayoutInflater().inflate(R.layout.edit_player, null);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("session", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id", 0);
        String playerUsername = sharedPreferences.getString("username", null);

        usernameTextField = view.findViewById(R.id.edtiUsername);
        usernameTextField.setText(playerUsername);

        dialogBuilder.setView(view)
                .setTitle(editMessage)
                .setPositiveButton(btnOk, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                usernameEdited = usernameTextField.getText().toString().trim();
                                pDialog = new SweetAlertDialog(view.getContext(), SweetAlertDialog.PROGRESS_TYPE)
                                        .setTitleText(loadingText);
                                pDialog.show();
                                pDialog.setCancelable(false);
                                EditedPlayer(sharedPreferences);
                            }
                        })
                .setNegativeButton(btnCancel, (dialog, which) -> dismiss());

        return dialogBuilder.create();
    }

    private void EditedPlayer(SharedPreferences sharedPreferences) {
        new CountDownTimer(800 * 7, 800) {
            public void onTick(long millisUntilFinished) {
                /*We check if the user name exists in the database.*/
                Settings.RESPONSE_CLIENT.getService().allPlayers().enqueue(new Callback<List<PlayerDto>>() {
                    @Override
                    public void onResponse(Call<List<PlayerDto>> call, Response<List<PlayerDto>> response) {
                        players = response.body();
                    }

                    @Override
                    public void onFailure(Call<List<PlayerDto>> call, Throwable t) {
                        Log.e("Error: ", t.getMessage());
                    }
                });
            }
            public void onFinish() {

                for (PlayerDto playersUsername : players) {
                    if (playersUsername.getId().equals(id) && !playersUsername.getUsername().equals(usernameEdited)) {
                        editedPlayer = playersUsername;
                        editedPlayer.setUsername(usernameEdited);
                        typeError = 0;
                    } else if (playersUsername.getUsername().equals(usernameEdited)) {
                        usernameTextField.setError(messageUserExist);
                        typeError = 1;
                    }
                }
                switch (typeError) {
                    case 0:
                        /* edit the player with the new username*/
                        Settings.RESPONSE_CLIENT.getService()
                                .editPlayer(53, editedPlayer)
                                .enqueue(new Callback<PlayerDto>() {
                                    @Override
                                    public void onResponse(Call<PlayerDto> call, Response<PlayerDto> response) {
                                        editedPlayer = response.body();
                                        typeError = 0;
                                    }

                                    @Override
                                    public void onFailure(Call<PlayerDto> call, Throwable t) {
                                        Log.e("Error: ", t.getMessage());
                                        typeError = 2;
                                    }
                                });
                        pDialog.setTitleText(titleSuccess)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        editorShared = sharedPreferences.edit();
                        editorShared.putString("username", editedPlayer.getUsername());
                        editorShared.putInt("id", editedPlayer.getId());
                        mOnInputListener.sendInput(editedPlayer, editedPlayer.getUsername());
                        editorShared.apply();
                        break;
                    case 1:
                        pDialog.setTitleText(titleUsername)
                                .setContentText(messageUserExist)
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        break;
                    case 2:
                        pDialog.setTitleText(titleUsername)
                                .setContentText(usernameNoEdit)
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        break;
                }
            }
        }.start();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            mOnInputListener = (OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e("Error: ", "onAttach: ClassCastException: "+e.getMessage());
        }
    }
}

