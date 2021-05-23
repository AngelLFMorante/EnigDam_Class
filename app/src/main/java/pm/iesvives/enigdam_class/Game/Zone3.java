package pm.iesvives.enigdam_class.Game;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Arrays;

import pm.iesvives.enigdam_class.R;

public class Zone3 extends Fragment {

    protected Animation scaleUp, scaleDown;
    private Button btnNext, btnPrevious, btnBack;
    private LinearLayout linearLayoutGameSwitch;
    private SwitchCompat sA1, sB1, sC1, sD1, sA2, sB2, sC2, sD2;
    private Button openTheDoorWithSwitch;
    private boolean[] patternSwitchGame;
    private boolean[] switchGame;
    private boolean isCorrectSwitchGame = false;
    private ImageView papperWhite, openDoorLarge, closeDoorLarge;
    private SharedPreferences state;

    public Zone3() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zone3, container, false);
        state = getActivity().getSharedPreferences("States", getContext().MODE_PRIVATE);

        scaleUp = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_down);

        btnNext = view.findViewById(R.id.btnNext);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnBack = view.findViewById(R.id.btnBack);
        linearLayoutGameSwitch = view.findViewById(R.id.zone3LinearSwitch);
        openTheDoorWithSwitch = view.findViewById(R.id.zone3OpenTheDoorWithSwitch);
        papperWhite = view.findViewById(R.id.zone3PapperWhite);
        openDoorLarge = view.findViewById(R.id.zone3OpenDoorLarge);
        closeDoorLarge = view.findViewById(R.id.zone3CloseDoorLarge);
        sA1 = view.findViewById(R.id.switch1);
        sA2 = view.findViewById(R.id.switch2);
        sB1 = view.findViewById(R.id.switch3);
        sB2 = view.findViewById(R.id.switch4);
        sC1 = view.findViewById(R.id.switch5);
        sC2 = view.findViewById(R.id.switch6);
        sD1 = view.findViewById(R.id.switch7);
        sD2 = view.findViewById(R.id.switch8);

        loadState();

        ActionsButtons();

        return view;
    }

    private void loadState() {
//        if(state.getBoolean("z1EndGame", false)){
//            roomOpen.setVisibility(View.VISIBLE);
//        }
    }



    @SuppressLint("ClickableViewAccessibility")
    private void ActionsButtons() {

        btnNext.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnNext.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnNext.startAnimation(scaleDown);
                Zone4 z4 = new Zone4();
//                z4.setArguments(bundle);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().add(R.id.fragment_nav_game, z4).addToBackStack(null).commit();
            }
            return true;
        });

        btnPrevious.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnPrevious.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnPrevious.startAnimation(scaleDown);
                Zone2 z2 = new Zone2();
//                z2.setArguments(bundle);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().add(R.id.fragment_nav_game, z2).addToBackStack(null).commit();
            }
            return true;
        });

        btnBack.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnBack.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnBack.startAnimation(scaleDown);
                linearLayoutGameSwitch.setVisibility(View.GONE);
                btnBack.setVisibility(View.GONE);
            }
            return true;
        });

        closeDoorLarge.setOnClickListener(v->{
            linearLayoutGameSwitch.setVisibility(View.VISIBLE);
            btnBack.setVisibility(View.VISIBLE);
            gameSwitch();
        });


        //END GAME PROVISIONAL
//        roomOpen.setOnClickListener(v-> {
//            CountTimer.pauseTimer();
//            Intent intentEndGame = new Intent(getActivity().getApplicationContext(), EndGame.class);
//            intentEndGame.putExtra("Time", CountTimer.timeText);
//            getActivity().startActivity(intentEndGame);
//        });
    }


    /* game open the door whith switch */
    @SuppressLint("ClickableViewAccessibility")
    private void gameSwitch() {

        patternSwitchGame = new boolean[] {true, false, false, true, false, true, true, false};
        switchGame = new boolean[] {false, false, false, false, false, false, false, false};

        openTheDoorWithSwitch.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                openTheDoorWithSwitch.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                openTheDoorWithSwitch.startAnimation(scaleDown);
                if(checkPattern(patternSwitchGame)){
                    isCorrectSwitchGame = true;
                    linearLayoutGameSwitch.setVisibility(View.GONE);
                    openDoorLarge.setVisibility(View.VISIBLE);
                    papperWhite.setVisibility(View.VISIBLE);
                    closeDoorLarge.setVisibility(View.GONE);
                    btnBack.setVisibility(View.GONE);
                }
            }
            return true;
        });
    }

    private boolean checkPattern(boolean[] patternSwitchGame) {
        sA1.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            switchGame[0] = isChecked;
            Log.i("array1: ", String.valueOf(switchGame[0]));
        }));
        sA2.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            switchGame[1] = isChecked;
            Log.i("array2: ", String.valueOf(switchGame[1]));
        }));
        sB1.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            switchGame[2] = isChecked;
            Log.i("array3: ", String.valueOf(switchGame[2]));
        }));
        sB2.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            switchGame[3] = isChecked;
            Log.i("array4: ", String.valueOf(switchGame[3]));
        }));
        sC1.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            switchGame[4] = isChecked;
            Log.i("array15: ", String.valueOf(switchGame[4]));
        }));
        sC2.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            switchGame[5] = isChecked;
            Log.i("array16: ", String.valueOf(switchGame[5]));
        }));
        sD1.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            switchGame[6] = isChecked;
            Log.i("array17: ", String.valueOf(switchGame[6]));
        }));
        sD2.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            switchGame[7] = isChecked;
            Log.i("array18: ", String.valueOf(switchGame[7]));
        }));

        return Arrays.equals(switchGame,patternSwitchGame);
    }
}