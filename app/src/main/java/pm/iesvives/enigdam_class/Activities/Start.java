package pm.iesvives.enigdam_class.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import pm.iesvives.enigdam_class.R;

//Start of the application
public class Start extends MainActivity{

    private Button getIn;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_main);
        getIn = findViewById(R.id.getIn);

        //this is a small animation for the button, extends MainActivity
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);


        getIn.setOnTouchListener((v, event) -> {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    getIn.startAnimation(scaleUp);
                }else if(event.getAction() == MotionEvent.ACTION_UP) {
                    getIn.startAnimation(scaleDown);
                    Intent intent = new Intent(Start.this, MainActivity.class);
                    startActivity(intent);
                }
            return true;
        });
    }
}
