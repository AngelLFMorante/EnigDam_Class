package pm.iesvives.enigdam_class.Game;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.BreakIterator;

import pm.iesvives.enigdam_class.Activities.MainActivity;
import pm.iesvives.enigdam_class.CountDownTimer.CountTimer;
import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.R;

public class StartGame extends MainActivity {

    Fragment fragmentZone1;
    private PlayerDto player = new PlayerDto();
    private String difficulty;
    public static TextView countDownText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        countDownText = findViewById(R.id.timer);

        CountTimer.updateTimer();
        CountTimer.startTimerMethod(this);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        player  = (PlayerDto) bundle.getSerializable("player");
        difficulty = bundle.getString("difficulty");

        fragmentZone1 = new Zone1();
        fragmentZone1.setArguments(bundle);

        countDownText.setText(CountTimer.timeText);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_nav_game, fragmentZone1).commit();

    }

//    private void startTimerMethod() {
//        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                timeLeftInMilliseconds = millisUntilFinished;
//                updateTimer();
//            }
//
//            @Override
//            public void onFinish() {
//                Toast.makeText(StartGame.this, "GAME OVER", Toast.LENGTH_LONG).show();
//            }
//        }.start();
//    }
//
//    public void pauseTimer(){
//        countDownTimer.cancel();
//        Log.i("timer: ", countDownText.getText().toString());
//    }
//
//    private void updateTimer() {
//        int minutes = (int) timeLeftInMilliseconds / 60000;
//        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;
//
//        String timeText;
//
//        timeText =  "" + minutes;
//        timeText += ":";
//        if(seconds < 10) timeText += "0";
//        timeText += seconds;
//
//        countDownText.setText(timeText);
//    }


}