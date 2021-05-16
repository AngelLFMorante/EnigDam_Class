package pm.iesvives.enigdam_class.CountDownTimer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import pm.iesvives.enigdam_class.Activities.MainActivity;
import pm.iesvives.enigdam_class.Activities.Start;
import pm.iesvives.enigdam_class.Fragments.DialogEditPlayer;
import pm.iesvives.enigdam_class.Game.EndGame;
import pm.iesvives.enigdam_class.Game.HowToPlay;
import pm.iesvives.enigdam_class.Game.StartGame;
import pm.iesvives.enigdam_class.R;
import pm.iesvives.enigdam_class.Utils.Settings;

public class CountTimer extends MainActivity {

    private static CountDownTimer countDownTimer;
    public static String timeText;
    public static long timeLeftInMilliseconds = Settings.TIME_LEFT_IN_MILLISECONDS;
    public CountTimer() { }

    public static void startTimerMethod(Activity activity) {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                Intent intentEndGame = new Intent(activity.getApplicationContext(), EndGame.class);
                intentEndGame.putExtra("Time", CountTimer.timeText);
                activity.startActivity(intentEndGame);
            }
        }.start();
    }

    public static void pauseTimer(){
        countDownTimer.cancel();
    }

    public static void updateTimer() {
        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;

        timeText =  "" + minutes;
        timeText += ":";
        if(seconds < 10) timeText += "0";
        timeText += seconds;
        StartGame.countDownText.setText(timeText);
    }

}