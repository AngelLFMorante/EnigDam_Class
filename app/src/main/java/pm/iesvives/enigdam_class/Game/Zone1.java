package pm.iesvives.enigdam_class.Game;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;
import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.Fragments.DialogEndZone1;
import pm.iesvives.enigdam_class.R;

public class Zone1 extends Fragment {

    private PlayerDto player = new PlayerDto();
    protected Animation scaleUp, scaleDown;
    private DialogEndZone1 dialog;
    private SharedPreferences state;
    private SharedPreferences difficulty;
    private SharedPreferences.Editor stateEdit;
    private LinearLayout linearPuzzle;
    private Button btnNext, btnPrevious, btnBack;
    private Button btnBriefcaseOpen, btnBriefcaseClose, btnBriefcaseVoid;
    private Button btnDrawerOpen, btnDrawerClose;
    private Button btnAutowiredDrawer, btnAutowiredConnect;
    private Button a1,a2,a3,b1,b2,b3,c1,c2,c3;
    private List<Button> buttonsPuzzle;
    private ImageView binaryTest, screenComputer,lampHint;
    private Bundle bundle;
    private List<Integer> pattern;
    private boolean isComplete = false;
    //TODO ESTA VARIABLE ES PROVISIONAL PARA ACABAR LA PRIMERA PANTALLA
    private boolean endGame = false;

    public Zone1() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_zone1, container, false);

        scaleUp = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_down);

        btnNext = view.findViewById(R.id.btnNext);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnBriefcaseClose = view.findViewById(R.id.btnBriefcaseClose);
        btnBriefcaseOpen = view.findViewById(R.id.btnBriefcaseOpen);
        btnBriefcaseVoid = view.findViewById(R.id.btnBriefcaseTest);
        binaryTest = view.findViewById(R.id.binaryTest);
        btnDrawerClose = view.findViewById(R.id.drawerClose);
        btnDrawerOpen = view.findViewById(R.id.drawerOpen);
        btnAutowiredDrawer = view.findViewById(R.id.btnAutowiredDrawer);
        btnAutowiredConnect = view.findViewById(R.id.btnAutowiredConnect);
        screenComputer = view.findViewById(R.id.imgComputer);
        linearPuzzle = view.findViewById(R.id.linearPuzzle);
        btnBack = view.findViewById(R.id.btnBack);
        lampHint = view.findViewById(R.id.lampHint);

        state = getActivity().getSharedPreferences("States", getContext().MODE_PRIVATE);
        difficulty = getActivity().getSharedPreferences("Difficulty", getContext().MODE_PRIVATE);
        //TODO FALTA POR DESARROLLAR LA LÓGICA DE LAS PISTAS
        if(difficulty.getString("difficulty", "notValue").equals("easy")){
            lampHint.setImageResource(R.drawable.lamp_on);
        }else if(difficulty.getString("difficulty", "notValue").equals("medium")){
            lampHint.setImageResource(R.drawable.lamp_on);
        }else if(difficulty.getString("difficulty", "notValue").equals("hard")){
            lampHint.setImageResource(R.drawable.lamp_off);
        }
        stateEdit = state.edit();

        //load screen status
        loadState(state);

        //actions we have with the buttons in the view
        ActionsButtons();

        //fill in the data of the puzzle test
        puzzleButtons(view);

        return view;
    }

    private void loadState(SharedPreferences state) {
        //VISIBLE == 0 , INVISIBLE == 4, GONE == 8
        if(state.getInt("z1BtnBriefcaseOpen", 8) == 0 ){
            btnBriefcaseOpen.setVisibility(View.VISIBLE);
        }
        if(state.getInt("z1BtnDrawerOpen", 8) == 0 ){
            btnDrawerOpen.setVisibility(View.VISIBLE);
        }
        if(state.getInt("z1BtnAutowiredDrawer", 8) == 0 ){
            if(state.getInt("z1BtnAutowiredDrawerAfterClick", 0) == 8){
                btnAutowiredDrawer.setVisibility(View.GONE);
            }else{
                btnAutowiredDrawer.setVisibility(View.VISIBLE);
            }
        }
        if(state.getInt("z1BtnAutowiredConnect", 8) == 0 ){
            btnAutowiredConnect.setVisibility(View.VISIBLE);
        }
        if(state.getInt("z1ScreenComputer", 8) == 0 ){
            screenComputer.setVisibility(View.VISIBLE);
        }

    }

    private void puzzleButtons(View view) {
        buttonsPuzzle = new ArrayList<>();
        a1 = view.findViewById(R.id.A1);
        a2 = view.findViewById(R.id.A2);
        a3 = view.findViewById(R.id.A3);
        b1 = view.findViewById(R.id.B1);
        b2 = view.findViewById(R.id.B2);
        b3 = view.findViewById(R.id.B3);
        c1 = view.findViewById(R.id.C1);
        c2 = view.findViewById(R.id.C2);
        c3 = view.findViewById(R.id.C3);
        buttonsPuzzle.add(a1);
        buttonsPuzzle.add(a2);
        buttonsPuzzle.add(a3);
        buttonsPuzzle.add(b1);
        buttonsPuzzle.add(b2);
        buttonsPuzzle.add(b3);
        buttonsPuzzle.add(c1);
        buttonsPuzzle.add(c2);
        buttonsPuzzle.add(c3);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void ActionsButtons() {

        btnNext.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnNext.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnNext.startAnimation(scaleDown);
                Zone2 z2 = new Zone2();
                //z2.setArguments(bundle); Comento esto para luego saber como mandar datos para las pistas en cada pantalla
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().add(R.id.fragment_nav_game, z2).addToBackStack(null).commit();
            }
            return true;
        });

        btnPrevious.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnPrevious.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnPrevious.startAnimation(scaleDown);
                Zone4 z4 = new Zone4();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().add(R.id.fragment_nav_game, z4).addToBackStack(null).commit();
            }
            return true;
        });

        btnBriefcaseClose.setOnClickListener(v -> {
            btnBriefcaseOpen.setVisibility(View.VISIBLE);
            stateEdit.putInt("z1BtnBriefcaseOpen", btnBriefcaseOpen.getVisibility());
            stateEdit.commit();
        });
        btnBriefcaseOpen.setOnClickListener(v -> {
            binaryTest.setVisibility(View.VISIBLE);
            btnBriefcaseVoid.setVisibility(View.VISIBLE);
        });
        binaryTest.setOnClickListener(v -> {
            binaryTest.setVisibility(View.GONE);
            btnBriefcaseVoid.setVisibility(View.GONE);
        });
        btnDrawerClose.setOnClickListener(v -> {
            btnDrawerOpen.setVisibility(View.VISIBLE);
            btnAutowiredDrawer.setVisibility(View.VISIBLE);
            stateEdit.putInt("z1BtnDrawerOpen", btnDrawerOpen.getVisibility());
            stateEdit.putInt("z1BtnAutowiredDrawer", btnAutowiredDrawer.getVisibility());
            stateEdit.commit();
        });
        btnAutowiredDrawer.setOnClickListener(v -> {
            btnAutowiredConnect.setVisibility(View.VISIBLE);
            screenComputer.setVisibility(View.VISIBLE);
            btnAutowiredDrawer.setVisibility(View.GONE);
            stateEdit.putInt("z1BtnAutowiredConnect", btnAutowiredConnect.getVisibility());
            stateEdit.putInt("z1BtnAutowiredDrawerAfterClick", btnAutowiredDrawer.getVisibility());
            stateEdit.putInt("z1ScreenComputer", screenComputer.getVisibility());
            stateEdit.commit();
        });
        screenComputer.setOnClickListener(v -> {
            if(!isComplete){
                linearPuzzle.setVisibility(View.VISIBLE);
                btnBack.setVisibility(View.VISIBLE);
                btnBriefcaseOpen.setClickable(false);
                btnBriefcaseClose.setClickable(false);
                puzzle(linearPuzzle);
            }else{
                dialog = new DialogEndZone1();
                dialog.show(getActivity().getSupportFragmentManager(), "DialogZone1");
            }
        });
    }

    private void puzzle(LinearLayout linearPuzzle) {
        pattern = new ArrayList<>();
        //check if it follows the game pattern test buttons
        checkPattern();

        btnBack.setOnClickListener(v-> {
            linearPuzzle.setVisibility(View.INVISIBLE);
            btnBriefcaseOpen.setClickable(true);
            btnBriefcaseClose.setClickable(true);
            resetButtonsPuzzle();
            btnBack.setVisibility(View.GONE);
        });
    }

    private void checkPattern() {
        addPatternPuzzle();

        a3.setOnClickListener(v->{
            if(pattern.size() == 9){
                a3.setBackgroundColor(Color.GREEN);
                pattern.remove(0);
            }else{
                resetButtonsPuzzle();
            }
        });

        a2.setOnClickListener(v-> {
            if(pattern.size() == 8){
                a2.setBackgroundColor(Color.GREEN);
                pattern.remove(0);
            }else{
                resetButtonsPuzzle();
            }
        });

        a1.setOnClickListener(v-> {
            if(pattern.size() == 7){
                a1.setBackgroundColor(Color.GREEN);
                pattern.remove(0);
            }else{
                resetButtonsPuzzle();
            }
        });

        b1.setOnClickListener(v-> {
            if(pattern.size() == 6){
                b1.setBackgroundColor(Color.GREEN);
                pattern.remove(0);
            }else{
                resetButtonsPuzzle();
            }
        });

        c1.setOnClickListener(v-> {
            if(pattern.size() == 5){
                c1.setBackgroundColor(Color.GREEN);
                pattern.remove(0);
            }else{
                resetButtonsPuzzle();
            }
        });

        c2.setOnClickListener(v-> {
            if(pattern.size() == 4){
                c2.setBackgroundColor(Color.GREEN);
                pattern.remove(0);
            }else{
                resetButtonsPuzzle();
            }
        });

        c3.setOnClickListener(v->{
            if(pattern.size() == 3){
                c3.setBackgroundColor(Color.GREEN);
                pattern.remove(0);
            }else{
                resetButtonsPuzzle();
            }
        });

        b3.setOnClickListener(v-> {
            if(pattern.size() == 2){
                b3.setBackgroundColor(Color.GREEN);
                pattern.remove(0);
            }else{
                resetButtonsPuzzle();
            }
        });

        b2.setOnClickListener(v-> {
            if(pattern.size() == 1){
                b2.setBackgroundColor(Color.GREEN);
                pattern.remove(0);
                //Aquí que salga el modal.
                dialog = new DialogEndZone1();
                dialog.show(getActivity().getSupportFragmentManager(), "DialogZone1");
                isComplete = true;
                endGame = true;
                stateEdit.putBoolean("z1EndGame", endGame);
                stateEdit.commit();
                linearPuzzle.setVisibility(View.INVISIBLE);
                btnBriefcaseOpen.setClickable(true);
                btnBriefcaseClose.setClickable(true);
                btnBack.setVisibility(View.GONE);
            }else{
                resetButtonsPuzzle();
            }
        });
    }

    private void resetButtonsPuzzle() {
        pattern.clear();
        addPatternPuzzle();
        for (Button button : buttonsPuzzle) {
            button.setBackgroundColor(Color.parseColor("#494848"));
        }
    }

    private void addPatternPuzzle() {
        pattern.add(2131361794);
        pattern.add(2131361793);
        pattern.add(2131361792);
        pattern.add(2131361796);
        pattern.add(2131361801);
        pattern.add(2131361802);
        pattern.add(2131361803);
        pattern.add(2131361798);
        pattern.add(2131361797);
    }


}