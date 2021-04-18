package pm.iesvives.enigdam_class.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import pm.iesvives.enigdam_class.R;

public class MainActivity extends AppCompatActivity  {

    private Button login, registry, scores;
    protected Animation scaleUp, scaleDown;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.login);
        registry = findViewById(R.id.registry);
        scores = findViewById(R.id.scores);

        //this is a small animation for the button
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        login.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                login.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                login.startAnimation(scaleDown);
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
            }
            return true;
        });
        registry.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                registry.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                registry.startAnimation(scaleDown);
                Intent intent = new Intent(MainActivity.this, Registry.class);
                startActivity(intent);
            }
            return true;
        });
        scores.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                scores.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                scores.startAnimation(scaleDown);
                Intent intent = new Intent(MainActivity.this, Scores.class);
                startActivity(intent);
            }
            return true;
        });

    }
}