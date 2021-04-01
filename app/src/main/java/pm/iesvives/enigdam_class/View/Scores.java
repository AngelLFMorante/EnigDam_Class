package pm.iesvives.enigdam_class.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;

import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.R;
import pm.iesvives.enigdam_class.Service.ScoresAdapter;
import pm.iesvives.enigdam_class.Service.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Scores extends MainActivity {

    private Button buttonBack;
    private RecyclerView recyclerScore;
    private List<PlayerDto> players = new ArrayList<>();
    private RetrofitClient client = new RetrofitClient();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        buttonBack = findViewById(R.id.buttonsBack);
        recyclerScore = findViewById(R.id.recyclerViewScore);
        recyclerScore.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ScoreList();

        //this is a small animation for the button
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        buttonBack.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                buttonBack.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                buttonBack.startAnimation(scaleDown);
                Intent intent = new Intent(Scores.this, MainActivity.class);
                startActivity(intent);
            }
            return true;
        });
    }

    public void ScoreList() {
        //petición, lo que nos devuelve.
        client.getService().getAllScores().enqueue(new Callback<List<PlayerDto>>() {
            @Override
            public void onResponse(Call<List<PlayerDto>> call, Response<List<PlayerDto>> response) {
                players = response.body();

                ScoresAdapter adapter = new ScoresAdapter(players);
                recyclerScore.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<PlayerDto>> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });


    }

}