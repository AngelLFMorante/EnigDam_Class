package pm.iesvives.enigdam_class.Game;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.R;

public class Zone4 extends Fragment {

    protected Animation scaleUp, scaleDown;
    private Button btnNext, btnPrevious;
    private ImageView penDriveScreen;
    private ImageView zone2Key;
    private boolean zone2HaveTheKey = false;
    private SharedPreferences state;
    private SharedPreferences difficulty;
    private SharedPreferences.Editor stateEdit;
    private ImageView lampHint;

    public Zone4() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zone4, container, false);

        scaleUp = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_down);

        btnNext = view.findViewById(R.id.btnNext);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        lampHint = view.findViewById(R.id.lampHint);

        state = getActivity().getSharedPreferences("States", getContext().MODE_PRIVATE);
        stateEdit = state.edit();

        //Difficulty game
        difficulty = getActivity().getSharedPreferences("Difficulty", getContext().MODE_PRIVATE);
        if(difficulty.getString("difficulty", "notValue").equals("normal")){
            lampHint.setImageResource(R.drawable.lamp_on);
        }else if(difficulty.getString("difficulty", "notValue").equals("hard")){
            lampHint.setImageResource(R.drawable.lamp_off);
        }

        //objects zones
        penDriveScreen = view.findViewById(R.id.zone3PenDriveScreen);
        zone2Key =view.findViewById(R.id.zone2KeyScreen);

        //load screen status
        loadState(state);

        ActionsButtons();

        return view;
    }

    private void loadState(SharedPreferences state) {
        //VISIBLE == 0 , INVISIBLE == 4, GONE == 8
        if(state.getBoolean("z2HaveTheKey", false)){
            zone2HaveTheKey = state.getBoolean("z2HaveTheKey", false);
            if(zone2HaveTheKey){
                zone2Key.setVisibility(View.VISIBLE);
            }else{
                zone2Key.setVisibility(View.GONE);
            }
        }
        if(state.getInt("z3PendriveScreen", 8) == 0){
            penDriveScreen.setVisibility(View.VISIBLE);
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private void ActionsButtons() {

        btnNext.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnNext.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnNext.startAnimation(scaleDown);
                Zone1 z1 = new Zone1();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().add(R.id.fragment_nav_game, z1).addToBackStack(null).commit();
            }
            return true;
        });

        btnPrevious.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnPrevious.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnPrevious.startAnimation(scaleDown);
                Zone3 z3 = new Zone3();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().add(R.id.fragment_nav_game, z3).addToBackStack(null).commit();
            }
            return true;
        });

        lampHint.setOnClickListener(v->{
            Toast.makeText(getContext(), getResources().getString(R.string.z4hintNoClues), Toast.LENGTH_LONG).show();
        });
    }
}