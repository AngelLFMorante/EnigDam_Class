package pm.iesvives.enigdam_class.CountDownTimer;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;

import pm.iesvives.enigdam_class.Activities.MainActivity;
import pm.iesvives.enigdam_class.Game.EndGame;
import pm.iesvives.enigdam_class.Game.StartGame;
import pm.iesvives.enigdam_class.Utils.Settings;

/**
 * Counter class for timer action in the game
 */
public class CountTimer extends MainActivity {

    public static CountDownTimer countDownTimer;
    public static String timeText;
    public static long timeLeftInMilliseconds = Settings.TIME_LEFT_IN_MILLISECONDS;

    public CountTimer() {
    }

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

    public static void pauseTimer() {
        countDownTimer.cancel();
    }

    public static void updateTimer() {

        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;

        timeText = "" + minutes;
        timeText += ":";
        if (seconds < 10) timeText += "0";
        timeText += seconds;
        StartGame.countDownText.setText(timeText);
    }

}