package pm.iesvives.enigdam_class.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private int usernameRegister = 0;
    private int emailRegister = 0;
    private List<PlayerDto> players = new ArrayList<>();


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

        //we retrieve user data from the database
        checkUser();

        regEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                for (PlayerDto player : players) {
                    if (player.getEmail().equals(regEmail.getText().toString().trim())) {
                        regEmail.setError(messageEmailRegiter);
                        emailRegister = 1;
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

                if (authenticateUser()) {
                    player = new PlayerDto();
                    player.setName(regName.getText().toString());
                    player.setEmail(regEmail.getText().toString());
                    player.setUsername(regUsername.getText().toString());
                    player.setPassword(regPassword.getText().toString());
                    addNewPlayer(player);
                    Intent intent = new Intent(Registry.this, MainActivity.class);
                    startActivity(intent);
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
    private void addNewPlayer(PlayerDto player) {
        Settings.RESPONSE_CLIENT.getService().addPlayer(player).enqueue(new Callback<PlayerDto>() {
            @Override
            public void onResponse(Call<PlayerDto> call, Response<PlayerDto> response) {
                Toast.makeText(Registry.this, messageRegistry, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<PlayerDto> call, Throwable t) {
                Toast.makeText(Registry.this, messageRegistryError, Toast.LENGTH_LONG).show();
                Log.e("Error: ", t.getMessage());
            }
        });

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
}