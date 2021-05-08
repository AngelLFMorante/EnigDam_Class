package pm.iesvives.enigdam_class.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.Game.HowToPlay;
import pm.iesvives.enigdam_class.R;

public class Difficulty extends MainActivity {

    private Button btnEasy, btnMedium, btnHard, btnBack;
    private PlayerDto player = new PlayerDto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        btnBack = findViewById(R.id.buttonsBack);
        btnEasy = findViewById(R.id.btnEasy);
        btnMedium = findViewById(R.id.btnMedium);
        btnHard = findViewById(R.id.btnHard);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        player  = (PlayerDto) bundle.getSerializable("player");

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

        btnEasy.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnEasy.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnEasy.startAnimation(scaleDown);
                Intent intent = new Intent(Difficulty.this, HowToPlay.class);
                intent.putExtra("player", player);
                intent.putExtra("difficulty", "easy");
                startActivity(intent);
            }
            return true;
        });
        btnMedium.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnMedium.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnMedium.startAnimation(scaleDown);
                Intent intent = new Intent(Difficulty.this, HowToPlay.class);
                intent.putExtra("player", player);
                intent.putExtra("difficulty", "medium");
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
                intent.putExtra("player", player);
                intent.putExtra("difficulty", "hard");
                startActivity(intent);
            }
            return true;
        });
    }


}