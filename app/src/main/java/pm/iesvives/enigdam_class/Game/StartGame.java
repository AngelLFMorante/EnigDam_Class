package pm.iesvives.enigdam_class.Game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.BreakIterator;

import pm.iesvives.enigdam_class.Activities.MainActivity;
import pm.iesvives.enigdam_class.CountDownTimer.CountTimer;
import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.R;
import pm.iesvives.enigdam_class.Utils.Settings;

public class StartGame extends MainActivity {

    Fragment fragmentZone1;
    private PlayerDto player = new PlayerDto();
    public static TextView countDownText;
    private SharedPreferences.Editor difficulty;
    private SharedPreferences.Editor states;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        countDownText = findViewById(R.id.timer);

        CountTimer.timeLeftInMilliseconds = Settings.TIME_LEFT_IN_MILLISECONDS;
        CountTimer.updateTimer();
        CountTimer.startTimerMethod(this);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        difficulty = getSharedPreferences("Difficulty", Context.MODE_PRIVATE).edit();
        difficulty.putString("difficulty",  bundle.getString("difficulty"));
        difficulty.apply();

        fragmentZone1 = new Zone1();
        fragmentZone1.setArguments(bundle);

        states = getSharedPreferences("States", MODE_PRIVATE).edit().clear();
        states.apply();

        countDownText.setText(CountTimer.timeText);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_nav_game, fragmentZone1).commit();

    }

}