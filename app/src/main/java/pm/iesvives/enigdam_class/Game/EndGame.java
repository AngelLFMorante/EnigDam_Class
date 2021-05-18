package pm.iesvives.enigdam_class.Game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import pm.iesvives.enigdam_class.Activities.MainActivity;
import pm.iesvives.enigdam_class.Entity.Game;
import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.R;
import pm.iesvives.enigdam_class.Utils.Settings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EndGame extends MainActivity {

    private TextView time, score, gameOver;
    private ImageView endGame;
    private Button btnExit;
    private SharedPreferences preferences;
    private SharedPreferences.Editor difficultyEditor;
    private SharedPreferences.Editor states;
    private String timeGame;
    private String scoreGame;
    private Game game;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        preferences= getSharedPreferences("Difficulty", Context.MODE_PRIVATE);

        //id player in session game
        SharedPreferences session = getSharedPreferences("Session", Context.MODE_PRIVATE);
        int idPlayer = session.getInt("id", 0);

        btnExit = findViewById(R.id.buttonsBack);

        //this is a small animation for the button
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        timeGame = bundle.getString("Time");

        //we subtract the minutes
        double secondTime = Integer.parseInt(timeGame.substring(3));
        String partTime = timeGame.substring(0,2);

        double subTime ;
        if(partTime.equals("0:") && secondTime < 10){
            subTime = 0.1 * secondTime ;
            Log.i("numero :" , String.valueOf(subTime));
        }else{
            partTime = "00";
            subTime = Double.parseDouble(partTime);
        }



        //Some methods
        tableOfScore(subTime);
        difficulty();
        dataPlayer(idPlayer);
        //

        states = getSharedPreferences("States", MODE_PRIVATE).edit().clear();
        states.commit();

        btnExit.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnExit.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnExit.startAnimation(scaleDown);
                if(subTime == 0){
                    Intent intentLoose = new Intent(EndGame.this, LoserGame.class);
                    startActivity(intentLoose);
                }else{
                    Intent intentWin = new Intent(EndGame.this, WinnerGame.class);
                    startActivity(intentWin);
                }
            }
            return true;
        });
    }

    private void dataPlayer(int idPlayer) {
        final PlayerDto[] player = {new PlayerDto()};
        Settings.RESPONSE_CLIENT.getService().getPlayer(idPlayer).enqueue(new Callback<PlayerDto>() {
            @Override
            public void onResponse(Call<PlayerDto> call, Response<PlayerDto> response) {
                player[0] = response.body();
                sendToDataGame(player[0]);
            }
            @Override
            public void onFailure(Call<PlayerDto> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }

    private void sendToDataGame(PlayerDto p) {

        game = new Game();
        game.setTime(timeGame);
        game.setScore(scoreGame);
        game.setPlayer(p);

        Settings.RESPONSE_CLIENT.getService().addGame(game).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }

    private void tableOfScore(double subTime) {
        if(subTime >= 25 && subTime < 30){
            scoreGame = "1000";
        }else if(subTime >= 20 && subTime < 25){
            scoreGame = "600";
        }else if(subTime >= 15 && subTime < 20){
            scoreGame = "400";
        }else if(subTime >= 10 && subTime < 15){
            scoreGame = "225";
        }else if(subTime >= 5 && subTime < 10){
            scoreGame = "110";
        }else if(subTime >= 2 && subTime < 5){
            scoreGame = "75";
        }else if(subTime >= 1){
            scoreGame = "25";
        }else{
            scoreGame = "0000";
        }
    }

    private void difficulty() {

        if(preferences.getString("difficulty", "notValue").equals("easy")){
            gameOver = findViewById(R.id.gameOverEasy);
            time = findViewById(R.id.endTimeEasy);
            score = findViewById(R.id.endScoreEasy);
            endGame = findViewById(R.id.levelCompleteEasy);
            gameOver.setVisibility(View.VISIBLE);
            time.setVisibility(View.VISIBLE);
            score.setVisibility(View.VISIBLE);
            endGame.setVisibility(View.VISIBLE);
        }else if(preferences.getString("difficulty", "notValue").equals("medium")){
            gameOver = findViewById(R.id.gameOverMedium);
            time = findViewById(R.id.endTimeMedium);
            score = findViewById(R.id.endScoreMedium);
            endGame = findViewById(R.id.levelCompleteMedium);
            gameOver.setVisibility(View.VISIBLE);
            time.setVisibility(View.VISIBLE);
            score.setVisibility(View.VISIBLE);
            endGame.setVisibility(View.VISIBLE);
        }else if(preferences.getString("difficulty", "notValue").equals("hard")){
            gameOver = findViewById(R.id.gameOverHard);
            time = findViewById(R.id.endTimeHard);
            score = findViewById(R.id.endScoreHard);
            endGame = findViewById(R.id.levelCompleteHard);
            gameOver.setVisibility(View.VISIBLE);
            time.setVisibility(View.VISIBLE);
            score.setVisibility(View.VISIBLE);
            endGame.setVisibility(View.VISIBLE);
        }

        time.setText(timeGame);
        score.setText(scoreGame);
        difficultyEditor = getSharedPreferences("Difficulty", Context.MODE_PRIVATE).edit().clear();
        difficultyEditor.commit();
    }
}