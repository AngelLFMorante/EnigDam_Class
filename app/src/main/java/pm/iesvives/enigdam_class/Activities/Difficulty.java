package pm.iesvives.enigdam_class.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.Game.HowToPlay;
import pm.iesvives.enigdam_class.R;

public class Difficulty extends MainActivity {

    private Button btnNormal, btnHard, btnBack;
    private PlayerDto player = new PlayerDto();
    private SharedPreferences.Editor editorShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        btnBack = findViewById(R.id.buttonsBack);
        btnNormal = findViewById(R.id.btnNormal);
        btnHard = findViewById(R.id.btnHard);

        editorShared = getSharedPreferences("session", Context.MODE_PRIVATE).edit();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if ((PlayerDto) bundle.getSerializable("player") == null) {
            editorShared.clear();
            editorShared.apply();
            Intent backToHome = new Intent(Difficulty.this, Start.class);
            startActivity(backToHome);
        } else {
            player = (PlayerDto) bundle.getSerializable("player");
        }

        //this is a small animation for the button
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        ActionsButtons();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void ActionsButtons() {

        btnBack.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnBack.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnBack.startAnimation(scaleDown);
                Intent intent = new Intent(Difficulty.this, Lobby.class);
                intent.putExtra("player", player);
                startActivity(intent);
            }
            return true;
        });

        btnNormal.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnNormal.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnNormal.startAnimation(scaleDown);
                Intent intent = new Intent(Difficulty.this, HowToPlay.class);
                intent.putExtra("difficulty", "normal");
                startActivity(intent);
            }
            return true;
        });
        btnHard.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnHard.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnHard.startAnimation(scaleDown);
                Intent intent = new Intent(Difficulty.this, HowToPlay.class);
                intent.putExtra("difficulty", "hard");
                startActivity(intent);
            }
            return true;
        });
    }


}