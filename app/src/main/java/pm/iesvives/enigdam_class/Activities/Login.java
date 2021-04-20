package pm.iesvives.enigdam_class.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import cn.pedant.SweetAlert.SweetAlertDialog;
import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.R;
import pm.iesvives.enigdam_class.Utils.Settings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends MainActivity {

    private Button btnSignIn;
    private Button btnBack;
    private EditText userName;
    private EditText password;
    private String playerExistsMessage;
    private String titlePlayerExistMessage;
    private String isVerifyMessage;
    private String titleSuccess;
    private int verify;
    private List<PlayerDto> players = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = findViewById(R.id.loginUserName);
        password = findViewById(R.id.loginPassword);
        btnSignIn = findViewById(R.id.btnLogin);
        btnBack = findViewById(R.id.buttonsBack);
        playerExistsMessage = getResources().getString(R.string.playerExistsMessage);
        titlePlayerExistMessage = getResources().getString(R.string.titlePlayerExistMessage);
        isVerifyMessage = getResources().getString(R.string.isVerify);
        titleSuccess = getResources().getString(R.string.success);

        //this is a small animation for the button
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        btnBack.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnBack.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnBack.startAnimation(scaleDown);
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
            return true;
        });

        btnSignIn.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnSignIn.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnSignIn.startAnimation(scaleDown);
                if (userName.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty()) {
                    return false;
                }
                if (!playerExists(userName.getText().toString().trim(), password.getText().toString().trim())) {
                    return false;
                }
            }
            return true;
        });

    }

    /**
     * We check parameters with the database service
     *
     * @param username request user
     * @param password request user
     * @return ture or false
     */
    private boolean playerExists(String username, String password) {

        checkUser();
        String loadingText = getResources().getString(R.string.loading);

        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(loadingText);
        pDialog.show();
        pDialog.setCancelable(false);
        new CountDownTimer(800 * 7, 800) {
            public void onTick(long millisUntilFinished) { checkUser(); }
            public void onFinish() {
                verify = 1;
                try {
                    for (PlayerDto player : players) {
                        if (player.getUsername().equals(username)) {
                            String passDecrypt = decrypt(player.getPassword());
                            if (!passDecrypt.equals(password)) {
                                pDialog.setTitleText(titlePlayerExistMessage)
                                        .setContentText(playerExistsMessage)
                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            } else if (!player.isVerified()) {
                                pDialog.setTitleText(titlePlayerExistMessage)
                                        .setContentText(isVerifyMessage)
                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            } else {
                                pDialog.setTitleText(titleSuccess)
                                        .setContentText("")
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                verify = 0;
                                Intent intent = new Intent(Login.this, Lobby.class);
                                intent.putExtra("player", player);
                                startActivity(intent);
                            }
                        }
                        if(verify != 0){
                            pDialog.setTitleText(titlePlayerExistMessage)
                                    .setContentText(playerExistsMessage)
                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        //check if the data is correct
        if (verify == 0) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * user password decryption
     *
     * @param password request user
     * @return decrypt password
     * @throws Exception cipher
     */
    private String decrypt(String password) throws Exception {

        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(Settings.ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] encrypt = Base64.decode(password, Base64.DEFAULT);
        byte[] decrypted = cipher.doFinal(encrypt);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /***
     *
     * @return generates a  key
     */
    private Key generateKey() {
        return new SecretKeySpec(Settings.ENCRYPT_KEY.getBytes(), Settings.ALGORITHM);
    }

    /**
     * We check if the user name exists in the database.
     */
    private void checkUser() {
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
}