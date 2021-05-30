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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import cn.pedant.SweetAlert.SweetAlertDialog;
import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.R;
import pm.iesvives.enigdam_class.Utils.Settings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registry extends MainActivity {

    private Button buttonBack;
    private Button buttonRegister;
    private EditText regName;
    private EditText regEmail;
    private EditText regUsername;
    private EditText regPassword;
    private PlayerDto player;
    private String message;
    private String messageEmail;
    private String messageUsername;
    private String messageEmailRegiter;
    private String messageRegistry;
    private String messageRegistryError;
    private String encryptPassword;
    private String titleMessageRegis;
    private int usernameRegister = 0;
    private int emailRegister = 0;
    private List<PlayerDto> players = new ArrayList<>();
    private boolean add = true;
    private SweetAlertDialog pDialog;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);
        buttonBack = findViewById(R.id.buttonsBack);
        buttonRegister = findViewById(R.id.btnRegistry);
        regName = findViewById(R.id.registerName);
        regEmail = findViewById(R.id.registerEmail);
        regUsername = findViewById(R.id.regsiterUsername);
        regPassword = findViewById(R.id.regsiterPass);
        message = getResources().getString(R.string.messageError);
        messageEmail = getResources().getString(R.string.messageErrorEmail);
        messageUsername = getResources().getString(R.string.messageErrorUsername);
        messageEmailRegiter = getResources().getString(R.string.messageErrorEmailRegister);
        messageRegistry = getResources().getString(R.string.addPlayer);
        messageRegistryError = getResources().getString(R.string.addPlayerError);
        titleMessageRegis = getResources().getString(R.string.titlePlayerRegistryMessage);

        //we retrieve user data from the database
        checkUser();

        regEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                for (PlayerDto player : players) {
                    if (player.getEmail().equals(regEmail.getText().toString().trim())) {
                        regEmail.setError(messageEmailRegiter);
                        emailRegister = 1;
                    } else {
                        emailRegister = 0;
                    }
                }
            }
        });
        regUsername.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                for (PlayerDto player : players) {
                    if (player.getUsername().equals(regUsername.getText().toString().trim())) {
                        regUsername.setError(messageUsername);
                        usernameRegister = 1;
                    } else {
                        usernameRegister = 0;
                    }
                }
            }
        });

        //this is a small animation for the button
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        //back to home page
        buttonBack.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                buttonBack.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                buttonBack.startAnimation(scaleDown);
                Intent intent = new Intent(Registry.this, MainActivity.class);
                startActivity(intent);
            }
            return true;
        });

        //check registration and validate
        buttonRegister.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                buttonRegister.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                buttonRegister.startAnimation(scaleDown);

                //we check if the user exists
                if (authenticateUser()) {
                    try {
                        player = new PlayerDto();
                        player.setName(regName.getText().toString());
                        player.setEmail(regEmail.getText().toString());
                        player.setUsername(regUsername.getText().toString());
                        encryptPassword = encryptPassword(regPassword.getText().toString());
                        player.setPassword(encryptPassword);
                        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                                .setTitleText("Loading");
                        pDialog.show();
                        pDialog.setCancelable(false);
                        new CountDownTimer(800 * 7, 800) {
                            public void onTick(long millisUntilFinished) {

                            }

                            public void onFinish() {
                                if (addNewPlayer(player)) {
                                    pDialog.setTitleText("Success!")
                                            .setContentText(messageRegistry)
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    Intent intent = new Intent(Registry.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    pDialog.setTitleText(titleMessageRegis)
                                            .setContentText(messageRegistryError)
                                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                }
                            }
                        }.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    return false;
                }
            }
            return true;
        });

    }

    /**
     * We add a new user to the database
     *
     * @param player player object
     */
    private boolean addNewPlayer(PlayerDto player) {

        Settings.RESPONSE_CLIENT.getService().addPlayer(player).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    add = false;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });

        return add;
    }

    /**
     * User authentication with the parameters that you enter
     *
     * @return true or false
     */
    private boolean authenticateUser() {

        if (regName.getText().toString().trim().length() == 0) {
            regName.setError(message);
            return false;
        }
        if (regEmail.getText().toString().trim().length() == 0) {
            regEmail.setError(message);
            return false;
        }

        if (!validateEmail(regEmail.getText().toString().trim())) {
            regEmail.setError(messageEmail);
            return false;
        }

        if (regUsername.getText().toString().trim().length() == 0) {
            regUsername.setError(message);
            return false;
        }

        if (regPassword.getText().toString().trim().length() == 0) {
            regPassword.setError(message);
            return false;
        }
        if (emailRegister == 1) {
            return false;
        }
        if (usernameRegister == 1) {
            return false;
        }

        return true;
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

    /**
     * We validate the email address entered by the user
     *
     * @param email email user
     * @return email validated, true or false
     */
    private boolean validateEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        String patternEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        pattern = Pattern.compile(patternEmail);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * encrypt user password
     *
     * @param password player
     * @return password encrypted
     * @throws Exception Cipher
     */
    private String encryptPassword(String password) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(Settings.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.encode(encrypted, Base64.DEFAULT));
    }

    private Key generateKey() {
        return new SecretKeySpec(Settings.ENCRYPT_KEY.getBytes(), Settings.ALGORITHM);
    }
}