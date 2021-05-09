package pm.iesvives.enigdam_class.Game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import pm.iesvives.enigdam_class.Activities.Difficulty;
import pm.iesvives.enigdam_class.Activities.Lobby;
import pm.iesvives.enigdam_class.Activities.MainActivity;
import pm.iesvives.enigdam_class.Activities.Start;
import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.R;

public class HowToPlay extends MainActivity {

    private Button btnPlayGame;
    private PlayerDto player = new PlayerDto();
    private String difficulty;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);

        btnPlayGame = findViewById(R.id.btnPlayGame);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        player  = (PlayerDto) bundle.getSerializable("player");
        difficulty = bundle.getString("difficulty");

        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        btnPlayGame.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnPlayGame.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnPlayGame.startAnimation(scaleDown);
                Intent intent = new Intent(HowToPlay.this, StartGame.class);
                intent.putExtra("player", player);
                intent.putExtra("difficulty", difficulty);
                startActivity(intent);
            }
            return true;
        });
    }
}