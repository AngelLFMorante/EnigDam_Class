package pm.iesvives.enigdam_class.Game;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import pm.iesvives.enigdam_class.Activities.MainActivity;
import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.R;

public class StartGame extends MainActivity {

    FragmentTransaction transaction;
    Fragment fragmentZone1;
    private PlayerDto player = new PlayerDto();
    private String difficulty;
    private CountDownTimer countDownTimer;
    private TextView countDownText;
    private long timeLeftInMilliseconds = 600000 * 3; //30mins
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        countDownText = findViewById(R.id.timer);

        updateTimer();
        startTimerMethod();

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        player  = (PlayerDto) bundle.getSerializable("player");
        difficulty = bundle.getString("difficulty");

        fragmentZone1 = new Zone1();


        getSupportFragmentManager().beginTransaction().add(R.id.fragment_nav_game, fragmentZone1).commit();

    }

    private void startTimerMethod() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                Toast.makeText(StartGame.this, "GAME OVER", Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    private void updateTimer() {
        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;

        String timeText;

        timeText =  "" + minutes;
        timeText += ":";
        if(seconds < 10) timeText += "0";
        timeText += seconds;

        countDownText.setText(timeText);
    }


}