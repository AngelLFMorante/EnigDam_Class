package pm.iesvives.enigdam_class.Game;

import android.annotation.SuppressLint;
import android.app.MediaRouteButton;
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
    private ImageView paperWhite, openPaperWhite, openDoorLarge, closeDoorLarge;
    private ImageView keyCloseDoor, keyOpenDoor;
    private ImageView penDrive,penDriveScreen;
    private ImageView zone2Key;
    private boolean zone2HaveTheKey = false;
    private boolean haveThePendrive = false;
    private SharedPreferences state;
    private SharedPreferences difficulty;
    private SharedPreferences.Editor stateEdit;
    private ImageView lampHint;



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

        scaleUp = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_down);

        state = getActivity().getSharedPreferences("States", getContext().MODE_PRIVATE);
        difficulty = getActivity().getSharedPreferences("Difficulty", getContext().MODE_PRIVATE);
        stateEdit = state.edit();
        lampHint = view.findViewById(R.id.lampHint);
        //TODO FALTA POR DESARROLLAR LA LÓGICA DE LAS PISTAS
        if(difficulty.getString("difficulty", "notValue").equals("easy")){
            lampHint.setImageResource(R.drawable.lamp_on);
        }else if(difficulty.getString("difficulty", "notValue").equals("medium")){
            lampHint.setImageResource(R.drawable.lamp_on);
        }else if(difficulty.getString("difficulty", "notValue").equals("hard")){
            lampHint.setImageResource(R.drawable.lamp_off);
        }

        btnNext = view.findViewById(R.id.btnNext);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnBack = view.findViewById(R.id.btnBack);
        linearLayoutGameSwitch = view.findViewById(R.id.zone3LinearSwitch);
        openTheDoorWithSwitch = view.findViewById(R.id.zone3OpenTheDoorWithSwitch);
        paperWhite = view.findViewById(R.id.zone3paperWhite);
        openPaperWhite = view.findViewById(R.id.zone3OpenPaperWhite);
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

        penDrive = view.findViewById(R.id.zone3PenDrive);
        penDriveScreen = view.findViewById(R.id.zone3PenDriveScreen);


        //object zone 2
        zone2Key =view.findViewById(R.id.zone3KeyScreen);
        keyCloseDoor = view.findViewById(R.id.zone3KeyCloseDoor);
        keyOpenDoor = view.findViewById(R.id.zone3KeyOpenDoor);

        //load screen status
        loadState();

        ActionsButtons();

        return view;
    }

    private void loadState() {
        //VISIBLE == 0 , INVISIBLE == 4, GONE == 8
        if(state.getInt("z3OpenDoorLarge", 8) == 0 ){
            openDoorLarge.setVisibility(View.VISIBLE);
        }
        if(state.getInt("z3CloseDoorLarge", 8) == 0 ){
            closeDoorLarge.setVisibility(View.VISIBLE);
        }
        if(state.getInt("z3PaperWhite", 8) == 0 ){
            paperWhite.setVisibility(View.VISIBLE);
        }
        if(state.getBoolean("z2HaveTheKey", false)){
            zone2HaveTheKey = state.getBoolean("z2HaveTheKey", false);
            if(zone2HaveTheKey){
                zone2Key.setVisibility(View.VISIBLE);
            }else{
                zone2Key.setVisibility(View.GONE);
            }
        }
        if(state.getInt("z3KeyOpenDoor",  8) == 0){
            keyOpenDoor.setVisibility(View.VISIBLE);
        }
        if(state.getInt("z3KeyCloseDoor", 8) == 0 ){
            keyCloseDoor.setVisibility(View.VISIBLE);
        }
        if(state.getInt("z3Pendrive", 8) == 0){
            penDrive.setVisibility(View.VISIBLE);
        }
        if(state.getInt("z3PendriveScreen", 8) == 0){
            penDriveScreen.setVisibility(View.VISIBLE);
        }
        if(state.getBoolean("z3HaveThePendrive", false)){
            haveThePendrive = state.getBoolean("z3HaveThePendrive", false);
        }
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
            if(!isCorrectSwitchGame){
                gameSwitch();
            }

        });
        paperWhite.setOnClickListener(v->{
            paperWhite.setVisibility(View.GONE);
            openPaperWhite.setVisibility(View.VISIBLE);
        });
        openPaperWhite.setOnClickListener(v-> {
            openPaperWhite.setVisibility(View.GONE);
            paperWhite.setVisibility(View.VISIBLE);
        });

        keyCloseDoor.setOnClickListener(v->{
            if(zone2HaveTheKey){
                keyOpenDoor.setVisibility(View.VISIBLE);
                penDrive.setVisibility(View.VISIBLE);
                keyCloseDoor.setVisibility(View.GONE);
                zone2Key.setVisibility(View.GONE);
                zone2HaveTheKey = false;
                stateEdit.putBoolean("z2HaveTheKey", zone2HaveTheKey);
                stateEdit.putInt("z2keyScreen", 8);
                stateEdit.putInt("z3KeyOpenDoor", keyOpenDoor.getVisibility());
                stateEdit.putInt("z3KeyCloseDoor", keyCloseDoor.getVisibility());
                stateEdit.putInt("z3Pendrive", penDrive.getVisibility());
                stateEdit.commit();
            }
        });
        penDrive.setOnClickListener(v->{
            penDriveScreen.setVisibility(View.VISIBLE);
            penDrive.setVisibility(View.GONE);
            haveThePendrive = true;
            stateEdit.putBoolean("z3HaveThePendrive", haveThePendrive);
            stateEdit.putInt("z3PendriveScreen", penDriveScreen.getVisibility());
            stateEdit.putInt("z3Pendrive", penDrive.getVisibility());
            stateEdit.commit();
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
        checkPattern(patternSwitchGame);

        openTheDoorWithSwitch.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                openTheDoorWithSwitch.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                openTheDoorWithSwitch.startAnimation(scaleDown);
                if(checkPattern(patternSwitchGame)){
                    isCorrectSwitchGame = true;
                    linearLayoutGameSwitch.setVisibility(View.GONE);
                    openDoorLarge.setVisibility(View.VISIBLE);
                    paperWhite.setVisibility(View.VISIBLE);
                    closeDoorLarge.setVisibility(View.GONE);
                    btnBack.setVisibility(View.GONE);
                    stateEdit.putInt("z3OpenDoorLarge", openDoorLarge.getVisibility());
                    stateEdit.putInt("z3CloseDoorLarge", closeDoorLarge.getVisibility());
                    stateEdit.putInt("z3PaperWhite", paperWhite.getVisibility());
                    stateEdit.commit();
                }
            }
            return true;
        });
    }

    private boolean checkPattern(boolean[] patternSwitchGame) {
        sA1.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            switchGame[0] = isChecked;
        }));
        sA2.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            switchGame[1] = isChecked;
        }));
        sB1.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            switchGame[2] = isChecked;
        }));
        sB2.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            switchGame[3] = isChecked;
        }));
        sC1.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            switchGame[4] = isChecked;
        }));
        sC2.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            switchGame[5] = isChecked;
        }));
        sD1.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            switchGame[6] = isChecked;
        }));
        sD2.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            switchGame[7] = isChecked;
        }));

        return Arrays.equals(switchGame,patternSwitchGame);
    }
}