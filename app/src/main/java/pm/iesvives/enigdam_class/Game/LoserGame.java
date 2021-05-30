package pm.iesvives.enigdam_class.Game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import pm.iesvives.enigdam_class.Activities.Lobby;
import pm.iesvives.enigdam_class.Activities.MainActivity;
import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.R;
import pm.iesvives.enigdam_class.Utils.Settings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoserGame extends MainActivity {

    private Button btnBackMenu;
    private PlayerDto player = new PlayerDto();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loser_game);

        btnBackMenu = findViewById(R.id.btnBackToMenu);

        //this is a small animation for the button
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        //id player in session game
        SharedPreferences session = getSharedPreferences("Session", Context.MODE_PRIVATE);
        int idPlayer = session.getInt("id", 0);

        btnBackMenu.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnBackMenu.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnBackMenu.startAnimation(scaleDown);
                Settings.RESPONSE_CLIENT.getService().getPlayer(idPlayer).enqueue(new Callback<PlayerDto>() {
                    @Override
                    public void onResponse(Call<PlayerDto> call, Response<PlayerDto> response) {
                        player = response.body();
                        Intent intent = new Intent(LoserGame.this, Lobby.class);
                        intent.putExtra("player", player);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<PlayerDto> call, Throwable t) {
                        Log.e("Error: ", t.getMessage());
                    }
                });
            }
            return true;
        });
    }

}