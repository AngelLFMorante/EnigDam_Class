package pm.iesvives.enigdam_class.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.Locale;

import pm.iesvives.enigdam_class.R;

//Start of the application
public class Start extends MainActivity {

    private Button getIn, spain, eeuu;
    private Locale localizacion;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_main);

        getIn = findViewById(R.id.getIn);
        spain = findViewById(R.id.languageSpain);
        eeuu = findViewById(R.id.languageEeuu);

        //this is a small animation for the button, extends MainActivity
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        getIn.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                getIn.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                getIn.startAnimation(scaleDown);
                Intent intent = new Intent(Start.this, MainActivity.class);
                startActivity(intent);
            }
            return true;
        });

        spain.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                spain.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                spain.startAnimation(scaleDown);
                changedLanguages("es", "ES");
                recreate();
            }
            return true;
        });

        eeuu.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                eeuu.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                eeuu.startAnimation(scaleDown);
                changedLanguages("en", "US");
                recreate();
            }
            return true;
        });
    }

    /**
     * change the device language
     *
     * @param language language
     * @param country  country
     */
    private void changedLanguages(String language, String country) {
        localizacion = new Locale(language, country);
        Locale.setDefault(localizacion);

        Configuration configuration = new Configuration();
        configuration.locale = localizacion;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }
}
