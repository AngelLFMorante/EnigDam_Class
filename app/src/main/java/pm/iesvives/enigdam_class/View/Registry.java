package pm.iesvives.enigdam_class.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.R;

public class Registry extends MainActivity {

    private Button buttonBack;
    private Button buttonRegister;
    private EditText regName;
    private EditText regEmail;
    private EditText regUsername;
    private EditText regPassword;
    private PlayerDto player;
    private String message;
    private boolean isCorrect;


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

        //this is a small animation for the button
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

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

        buttonRegister.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                buttonRegister.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                buttonRegister.startAnimation(scaleDown);
                isCorrect = authenticateUser();
                if(isCorrect){
                    Log.i("Boolean : ", String.valueOf(isCorrect));
                    player = new PlayerDto();
                    player.setName(regName.getText().toString());
                    player.setEmail(regEmail.getText().toString());
                    player.setUsername(regUsername.getText().toString());
                    player.setPassword(regPassword.getText().toString());
                    //                RegisterAdapter();
//                Intent intent = new Intent(Registry.this, MainActivity.class);
//                startActivity(intent);
                }
            }
            return true;
        });

    }

    private boolean authenticateUser() {

        if (regName.getText().toString().trim().length() == 0) {
            regName.setError(message);
            return false;
        }
        if (regEmail.getText().toString().trim().length() == 0) {
            regEmail.setError(message);
            return false;
        }

        if(!validateEmail(regEmail.getText().toString().trim())){
            regEmail.setError("Invalid email");
        }

        if (regUsername.getText().toString().trim().length() == 0) {
            regUsername.setError(message);
            return false;
        }
        //TODO mirar en la base de datos si ese usuario ya está registrado.

        if (regPassword.getText().toString().trim().length() == 0) {
            regPassword.setError(message);
            return false;
        }

        return true;
    }

    private boolean validateEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        String patternEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        pattern = Pattern.compile(patternEmail);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}