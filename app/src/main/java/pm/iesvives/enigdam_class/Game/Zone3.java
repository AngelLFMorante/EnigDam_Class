package pm.iesvives.enigdam_class.Game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pm.iesvives.enigdam_class.CountDownTimer.CountTimer;
import pm.iesvives.enigdam_class.R;

@SuppressWarnings("ConstantConditions")
public class Zone3 extends Fragment {

    protected Animation scaleUp, scaleDown;
    private Button btnNext, btnPrevious, btnBackSwitch, btnBackCodeExit;
    private LinearLayout linearLayoutGameSwitch, linearLayoutCodeExit;
    private SwitchCompat sA1, sB1, sC1, sD1, sA2, sB2, sC2, sD2;
    private Button code1, code2, code3, code4;
    private Button btnOpenTheDoorExit;
    private Button openTheDoorWithSwitch;
    private Button btnLedCorrectExit;
    private boolean[] patternSwitchGame;
    private boolean[] switchGame;
    private boolean isCorrectSwitchGame = false;
    private boolean theCodeisCorrect = false;
    private ImageView paperWhite, openPaperWhite, openDoorLarge, closeDoorLarge;
    private ImageView keyCloseDoor, keyOpenDoor;
    private ImageView penDrive, penDriveScreen;
    private ImageView zone2Key;
    private ImageView exitGame;
    private ImageView codeDoor;
    private boolean zone2HaveTheKey = false;
    private boolean haveThePendrive = false;
    private SharedPreferences state;
    private SharedPreferences difficulty;
    private SharedPreferences.Editor stateEdit;
    private ImageView lampHint;

    private String value = "";
    private String valueCode1 = "0";
    private String valueCode2 = "0";
    private String valueCode3 = "0";
    private String valueCode4 = "0";

    private String[] hints = new String[3];

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
        stateEdit = state.edit();
        //Difficulty game
        difficulty = getActivity().getSharedPreferences("Difficulty", getContext().MODE_PRIVATE);
        lampHint = view.findViewById(R.id.lampHint);
        clickHintDifficulty();

        btnNext = view.findViewById(R.id.btnNext);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnBackSwitch = view.findViewById(R.id.btnBackSwitch);
        btnBackCodeExit = view.findViewById(R.id.btnBackCodeExit);
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
        zone2Key = view.findViewById(R.id.zone3KeyScreen);
        keyCloseDoor = view.findViewById(R.id.zone3KeyCloseDoor);
        keyOpenDoor = view.findViewById(R.id.zone3KeyOpenDoor);

        codeDoor = view.findViewById(R.id.zone3CodeDoor);
        linearLayoutCodeExit = view.findViewById(R.id.zone3LinearCode);
        code1 = view.findViewById(R.id.z3Code1);
        code2 = view.findViewById(R.id.z3Code2);
        code3 = view.findViewById(R.id.z3Code3);
        code4 = view.findViewById(R.id.z3Code4);
        btnLedCorrectExit = view.findViewById(R.id.z3BtnCodeExit);
        btnOpenTheDoorExit = view.findViewById(R.id.zone3ExitDoorCode);
        exitGame = view.findViewById(R.id.zone3RoomExit);

        //load screen status
        loadState();

        ActionsButtons();

        return view;
    }

    private void hintsZone3() {
        hints[0] = getResources().getString(R.string.z3hintCodeDoor);
        hints[1] = getResources().getString(R.string.z3hintDrawer);
        hints[2] = getResources().getString(R.string.z3hintSolutionCode);
    }

    private void clickHintDifficulty() {
        if (difficulty.getString("difficulty", "notValue").equals("normal")) {
            lampHint.setImageResource(R.drawable.lamp_on);
            hintsZone3();
            lampHint.setOnClickListener(v->{
                if(openDoorLarge.getVisibility() != View.VISIBLE){
                    Toast.makeText(getContext(), hints[0], Toast.LENGTH_LONG).show();
                }else if(keyOpenDoor.getVisibility() != View.VISIBLE && zone2HaveTheKey){
                    Toast.makeText(getContext(), hints[1], Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), hints[2], Toast.LENGTH_LONG).show();
                }
            });
        } else if (difficulty.getString("difficulty", "notValue").equals("hard")) {
            lampHint.setImageResource(R.drawable.lamp_off);
            lampHint.setOnClickListener(v->{
                Toast.makeText(getContext(), getResources().getString(R.string.z4hintNoClues), Toast.LENGTH_LONG).show();
            });
        }
    }

    private void loadState() {
        //VISIBLE == 0 , INVISIBLE == 4, GONE == 8
        if (state.getInt("z3OpenDoorLarge", 8) == 0) {
            openDoorLarge.setVisibility(View.VISIBLE);
        }
        if (state.getInt("z3CloseDoorLarge", 8) == 0) {
            closeDoorLarge.setVisibility(View.VISIBLE);
        }
        if (state.getInt("z3PaperWhite", 8) == 0) {
            paperWhite.setVisibility(View.VISIBLE);
        }
        if (state.getBoolean("z2HaveTheKey", false)) {
            zone2HaveTheKey = state.getBoolean("z2HaveTheKey", false);
            if (zone2HaveTheKey) {
                zone2Key.setVisibility(View.VISIBLE);
            } else {
                zone2Key.setVisibility(View.GONE);
            }
        }
        if (state.getInt("z3KeyOpenDoor", 8) == 0) {
            keyOpenDoor.setVisibility(View.VISIBLE);
        }
        if (state.getInt("z3KeyCloseDoor", 8) == 0) {
            keyCloseDoor.setVisibility(View.VISIBLE);
        }
        if (state.getInt("z3Pendrive", 8) == 0) {
            penDrive.setVisibility(View.VISIBLE);
        }
        if (state.getInt("z3PendriveScreen", 8) == 0) {
            penDriveScreen.setVisibility(View.VISIBLE);
        }
        if (state.getBoolean("z3HaveThePendrive", false)) {
            haveThePendrive = state.getBoolean("z3HaveThePendrive", false);
        }
        if(state.getInt("z3ExitGame", 8) == 0){
            exitGame.setVisibility(View.VISIBLE);
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
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().add(R.id.fragment_nav_game, z2).addToBackStack(null).commit();
            }
            return true;
        });

        btnBackSwitch.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnBackSwitch.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnBackSwitch.startAnimation(scaleDown);
                linearLayoutGameSwitch.setVisibility(View.GONE);
                btnBackSwitch.setVisibility(View.GONE);
            }
            return true;
        });

        closeDoorLarge.setOnClickListener(v -> {
            linearLayoutGameSwitch.setVisibility(View.VISIBLE);
            btnBackSwitch.setVisibility(View.VISIBLE);
            if (!isCorrectSwitchGame) {
                gameSwitch();
            }

        });
        paperWhite.setOnClickListener(v -> {
            paperWhite.setVisibility(View.GONE);
            openPaperWhite.setVisibility(View.VISIBLE);
        });
        openPaperWhite.setOnClickListener(v -> {
            openPaperWhite.setVisibility(View.GONE);
            paperWhite.setVisibility(View.VISIBLE);
        });

        keyCloseDoor.setOnClickListener(v -> {
            if (zone2HaveTheKey) {
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
        penDrive.setOnClickListener(v -> {
            penDriveScreen.setVisibility(View.VISIBLE);
            penDrive.setVisibility(View.GONE);
            haveThePendrive = true;
            stateEdit.putBoolean("z3HaveThePendrive", haveThePendrive);
            stateEdit.putInt("z3PendriveScreen", penDriveScreen.getVisibility());
            stateEdit.putInt("z3Pendrive", penDrive.getVisibility());
            stateEdit.commit();
        });

        codeDoor.setOnClickListener(v -> {
            linearLayoutCodeExit.setVisibility(View.VISIBLE);
            btnBackCodeExit.setVisibility(View.VISIBLE);
            enterCodeExit();
        });

        btnBackCodeExit.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnBackCodeExit.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnBackCodeExit.startAnimation(scaleDown);
                linearLayoutCodeExit.setVisibility(View.GONE);
                btnBackCodeExit.setVisibility(View.GONE);
                code1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code0));
                code2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code0));
                code3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code0));
                code4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code0));
                value = "";
                valueCode1 = "0";
                valueCode2 = "0";
                valueCode3 = "0";
                valueCode4 = "0";
            }
            return true;
        });

        btnOpenTheDoorExit.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnOpenTheDoorExit.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnOpenTheDoorExit.startAnimation(scaleDown);
                if(theCodeisCorrect){
                    exitGame.setVisibility(View.VISIBLE);
                    stateEdit.putInt("z3ExitGame", exitGame.getVisibility());
                    stateEdit.commit();
                    linearLayoutCodeExit.setVisibility(View.GONE);
                    btnBackCodeExit.setVisibility(View.GONE);
                }
            }
            return true;
        });

        exitGame.setOnClickListener(v->{
            CountTimer.pauseTimer();
            Intent intentEndGame = new Intent(getActivity().getApplicationContext(), EndGame.class);
            intentEndGame.putExtra("Time", CountTimer.timeText);
            getActivity().startActivity(intentEndGame);
        });

    }

    private void enterCodeExit() {
        valueCode1Exit();
        valueCode2Exit();
        valueCode3Exit();
        valueCode4Exit();
    }

    private void isCorrect() {
        String codeForExit = "1415";
        String valuesCode = valueCode1 + valueCode2 + valueCode3 + valueCode4;
        if(valuesCode.equals(codeForExit)){
            btnLedCorrectExit.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_btn_green));
            theCodeisCorrect = true;
        }else{
            btnLedCorrectExit.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_btn_red));
            theCodeisCorrect = false;
        }
    }

    private void valueCode4Exit() {
        code4.setOnClickListener(v->{
            Log.i("id:" , String.valueOf(code4.getId()) + " " +String.valueOf(code3.getId()) + " " +String.valueOf(code2.getId()) + " " +String.valueOf(code1.getId()) );
            value = valueCodeClick(code4);
            switch (value) {
                case "0":
                    code4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code0));
                    isCorrect();
                    break;
                case "1":
                    code4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code1));
                    isCorrect();
                    break;
                case "2":
                    code4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code2));
                    isCorrect();
                    break;
                case "3":
                    code4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code3));
                    isCorrect();
                    break;
                case "4":
                    code4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code4));
                    isCorrect();
                    break;
                case "5":
                    code4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code5));
                    isCorrect();
                    break;
                case "6":
                    code4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code6));
                    isCorrect();
                    break;
                case "7":
                    code4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code7));
                    isCorrect();
                    break;
                case "8":
                    code4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code8));
                    isCorrect();
                    break;
                case "9":
                    code4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code9));
                    isCorrect();
                    break;
            }
        });
    }

    private void valueCode3Exit() {
        code3.setOnClickListener(v->{
            value = valueCodeClick(code3);
            switch (value) {
                case "0":
                    code3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code0));
                    isCorrect();
                    break;
                case "1":
                    code3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code1));
                    isCorrect();
                    break;
                case "2":
                    code3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code2));
                    isCorrect();
                    break;
                case "3":
                    code3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code3));
                    isCorrect();
                    break;
                case "4":
                    code3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code4));
                    isCorrect();
                    break;
                case "5":
                    code3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code5));
                    isCorrect();
                    break;
                case "6":
                    code3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code6));
                    isCorrect();
                    break;
                case "7":
                    code3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code7));
                    isCorrect();
                    break;
                case "8":
                    code3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code8));
                    isCorrect();
                    break;
                case "9":
                    code3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code9));
                    isCorrect();
                    break;
            }
        });
    }

    private void valueCode2Exit() {
        code2.setOnClickListener(v->{
            value = valueCodeClick(code2);
            switch (value) {
                case "0":
                    code2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code0));
                    isCorrect();
                    break;
                case "1":
                    code2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code1));
                    isCorrect();
                    break;
                case "2":
                    code2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code2));
                    isCorrect();
                    break;
                case "3":
                    code2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code3));
                    isCorrect();
                    break;
                case "4":
                    code2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code4));
                    isCorrect();
                    break;
                case "5":
                    code2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code5));
                    isCorrect();
                    break;
                case "6":
                    code2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code6));
                    isCorrect();
                    break;
                case "7":
                    code2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code7));
                    isCorrect();
                    break;
                case "8":
                    code2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code8));
                    isCorrect();
                    break;
                case "9":
                    code2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code9));
                    isCorrect();
                    break;
            }
        });
    }

    private void valueCode1Exit() {
        code1.setOnClickListener(v -> {
            value = valueCodeClick(code1);
            switch (value) {
                case "0":
                    code1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code0));
                    isCorrect();
                    break;
                case "1":
                    code1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code1));
                    isCorrect();
                    break;
                case "2":
                    code1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code2));
                    isCorrect();
                    break;
                case "3":
                    code1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code3));
                    isCorrect();
                    break;
                case "4":
                    code1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code4));
                    isCorrect();
                    break;
                case "5":
                    code1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code5));
                    isCorrect();
                    break;
                case "6":
                    code1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code6));
                    isCorrect();
                    break;
                case "7":
                    code1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code7));
                    isCorrect();
                    break;
                case "8":
                    code1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code8));
                    isCorrect();
                    break;
                case "9":
                    code1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.zone3_code9));
                    isCorrect();
                    break;
            }

        });
    }

    private String valueCodeClick(Button code) {

        int codeId = code.getId();
        int aux ;
        String value = "";

        switch(codeId){
            case R.id.z3Code1:
                aux = Integer.parseInt(valueCode1);
                aux++;
                if(aux == 10){
                    aux = 0;
                }
                value = ""+aux;
                valueCode1 = value;
                break;
            case R.id.z3Code2:
                aux = Integer.parseInt(valueCode2);
                aux++;
                if(aux == 10){
                    aux = 0;
                }
                value = ""+aux;
                valueCode2 = value;
                break;
            case R.id.z3Code3:
                aux = Integer.parseInt(valueCode3);
                aux++;
                if(aux == 10){
                    aux = 0;
                }
                value = ""+aux;
                valueCode3 = value;
                break;
            case R.id.z3Code4:
                aux = Integer.parseInt(valueCode4);
                aux++;
                if(aux == 10){
                    aux = 0;
                }
                value = ""+aux;
                valueCode4 = value;
                break;
        }
        return value;
    }


    /* game open the door whith switch */
    @SuppressLint("ClickableViewAccessibility")
    private void gameSwitch() {

        patternSwitchGame = new boolean[]{true, false, false, true, false, true, true, false};
        switchGame = new boolean[]{false, false, false, false, false, false, false, false};
        checkPattern(patternSwitchGame);

        openTheDoorWithSwitch.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                openTheDoorWithSwitch.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                openTheDoorWithSwitch.startAnimation(scaleDown);
                if (checkPattern(patternSwitchGame)) {
                    isCorrectSwitchGame = true;
                    linearLayoutGameSwitch.setVisibility(View.GONE);
                    openDoorLarge.setVisibility(View.VISIBLE);
                    paperWhite.setVisibility(View.VISIBLE);
                    closeDoorLarge.setVisibility(View.GONE);
                    btnBackSwitch.setVisibility(View.GONE);
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
        sA1.setOnCheckedChangeListener(((buttonView, isChecked) -> switchGame[0] = isChecked));
        sA2.setOnCheckedChangeListener(((buttonView, isChecked) -> switchGame[1] = isChecked));
        sB1.setOnCheckedChangeListener(((buttonView, isChecked) -> switchGame[2] = isChecked));
        sB2.setOnCheckedChangeListener(((buttonView, isChecked) -> switchGame[3] = isChecked));
        sC1.setOnCheckedChangeListener(((buttonView, isChecked) -> switchGame[4] = isChecked));
        sC2.setOnCheckedChangeListener(((buttonView, isChecked) -> switchGame[5] = isChecked));
        sD1.setOnCheckedChangeListener(((buttonView, isChecked) -> switchGame[6] = isChecked));
        sD2.setOnCheckedChangeListener(((buttonView, isChecked) -> switchGame[7] = isChecked));

        return Arrays.equals(switchGame, patternSwitchGame);
    }
}