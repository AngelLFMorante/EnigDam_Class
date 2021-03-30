package pm.iesvives.enigdam_class.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import pm.iesvives.enigdam_class.R;

public class Scores extends MainActivity {

    private Button buttonBack;
    private TextView textUserScore;
    private RecyclerView recyclerScore;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        buttonBack = findViewById(R.id.buttonsBack);

        //this is a small animation for the button
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        buttonBack.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                buttonBack.startAnimation(scaleUp);
            }else if(event.getAction() == MotionEvent.ACTION_UP) {
                buttonBack.startAnimation(scaleDown);
                    Intent intent = new Intent(Scores.this, MainActivity.class);
                    startActivity(intent);
            }
            return true;
        });
    }
}