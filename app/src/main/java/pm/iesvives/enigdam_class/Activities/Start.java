package pm.iesvives.enigdam_class.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.Locale;

import pm.iesvives.enigdam_class.R;

//Start of the application
public class Start extends MainActivity{

    private Button getIn,spain,eeuu;
    private SharedPreferences sharedLanguage;
    private SharedPreferences.Editor sharedLangEdit;
    private Locale locale;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_main);
        getIn = findViewById(R.id.getIn);

        spain = findViewById(R.id.languageSpain);
        eeuu = findViewById(R.id.languageEeuu);
        sharedLanguage = getSharedPreferences("Languaje",Context.MODE_PRIVATE);
        sharedLangEdit = sharedLanguage.edit();

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

        spain.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                spain.startAnimation(scaleUp);
            }else if(event.getAction() == MotionEvent.ACTION_UP) {
                spain.startAnimation(scaleDown);
               // changedLanguages("es");
            }
            return true;
        });

        eeuu.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                eeuu.startAnimation(scaleUp);
            }else if(event.getAction() == MotionEvent.ACTION_UP) {
                eeuu.startAnimation(scaleDown);
                //changedLanguages("en");
            }
            return true;
        });
    }

//    private void changedLanguages(String language) {
//        locale = new Locale(language);
//        sharedLangEdit.putString("Language", language);
//        sharedLangEdit.commit();
//        Resources resources = getResources();
//        Configuration configuration = resources.getConfiguration();
//        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
//
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
//            getApplicationContext().createConfigurationContext(configuration);
//            recreate();
//        }else{
//            resources.updateConfiguration(configuration, displayMetrics);
//            recreate();
//        }
//    }
}
