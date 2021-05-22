package pm.iesvives.enigdam_class.Game;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.R;

public class Zone2 extends Fragment {

    protected Animation scaleUp, scaleDown;
    private Button btnNext, btnPrevious;
    private ImageView lampHint;
    private ImageView papperRed,papperYellow,papperGreen;
    private ImageView papperOpenRed,papperOpenYellow,papperOpenGreen;
    private ImageView bookShelf, openBookShelf, closeBookShelf;
    private ImageView drawerClose, drawerOpen, key, keyScreen;
    private boolean haveTheKey = false;
    private SharedPreferences state;
    private SharedPreferences difficulty;
    private SharedPreferences.Editor stateEdit;
    private Bundle bundle;
    private PlayerDto player = new PlayerDto();

    public Zone2() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zone2, container, false);

        scaleUp = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_down);

        btnNext = view.findViewById(R.id.btnNext);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        lampHint = view.findViewById(R.id.lampHint);
        bookShelf = view.findViewById(R.id.zone2Bookshelf);
        openBookShelf = view.findViewById(R.id.zon2OpenBook);
        closeBookShelf = view.findViewById(R.id.zone2NotBook);
        papperRed = view.findViewById(R.id.zone2PapperRed);
        papperYellow = view.findViewById(R.id.zone2PapperYellow);
        papperGreen = view.findViewById(R.id.zone2PapperGreen);
        papperOpenRed = view.findViewById(R.id.zone2PapperOpenRed);
        papperOpenYellow = view.findViewById(R.id.zone2PapperOpenYellow);
        papperOpenGreen = view.findViewById(R.id.zone2PapperOpenGreen);
        drawerClose = view.findViewById(R.id.zone2CloseDoor);
        drawerOpen = view.findViewById(R.id.zone2OpenDoor);
        key = view.findViewById(R.id.zone2KeyDrawer);
        keyScreen = view.findViewById(R.id.zone2KeyScreen);

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

        ActionsButtons();

        return view;
    }

    private void loadState(SharedPreferences state) {
        //VISIBLE == 0 , INVISIBLE == 4, GONE == 8
        if(state.getInt("z2DrawerOpen", 8) == 0 ){
            drawerOpen.setVisibility(View.VISIBLE);
        }
        if(state.getInt("z2key", 8) == 0 ){
            key.setVisibility(View.VISIBLE);
        }
        if(state.getInt("z2keyScreen", 8) == 0 ){
            keyScreen.setVisibility(View.VISIBLE);
        }
        if(state.getInt("z2keyAfterClick", 0) == 8 ){
            key.setVisibility(View.GONE);
        }
        if(state.getBoolean("z2HaveTheKey", false)){
            haveTheKey = true;
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private void ActionsButtons() {

        btnNext.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnNext.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnNext.startAnimation(scaleDown);
                Zone3 z3 = new Zone3();
//                z3.setArguments(bundle);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().add(R.id.fragment_nav_game, z3).addToBackStack(null).commit();
            }
            return true;
        });

        btnPrevious.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnPrevious.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnPrevious.startAnimation(scaleDown);
                Zone1 z1 = new Zone1();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().add(R.id.fragment_nav_game, z1).addToBackStack(null).commit();
            }
            return true;
        });

        /* region book */
        bookShelf.setOnClickListener(v->{
            openBookShelf.setVisibility(View.VISIBLE);
            closeBookShelf.setVisibility(View.VISIBLE);
        });
        openBookShelf.setOnClickListener(v->{
            openBookShelf.setVisibility(View.GONE);
            closeBookShelf.setVisibility(View.GONE);
        });
        /* endRegion */
        /* region papper */
        papperRed.setOnClickListener(v->{
            papperOpenRed.setVisibility(View.VISIBLE);
            papperRed.setVisibility(View.GONE);
        });
        papperOpenRed.setOnClickListener(v->{
            papperRed.setVisibility(View.VISIBLE);
            papperOpenRed.setVisibility(View.GONE);
        });
        papperYellow.setOnClickListener(v->{
            papperOpenYellow.setVisibility(View.VISIBLE);
            papperYellow.setVisibility(View.GONE);
        });
        papperOpenYellow.setOnClickListener(v->{
            papperYellow.setVisibility(View.VISIBLE);
            papperOpenYellow.setVisibility(View.GONE);
        });
        papperGreen.setOnClickListener(v->{
            papperOpenGreen.setVisibility(View.VISIBLE);
            papperGreen.setVisibility(View.GONE);
        });
        papperOpenGreen.setOnClickListener(v->{
            papperGreen.setVisibility(View.VISIBLE);
            papperOpenGreen.setVisibility(View.GONE);
        });
        /* endRegion */
        /* region drawer and key */
        drawerClose.setOnClickListener(v->{
            drawerOpen.setVisibility(View.VISIBLE);
            key.setVisibility(View.VISIBLE);
            stateEdit.putInt("z2DrawerOpen", drawerOpen.getVisibility());
            stateEdit.putInt("z2key", key.getVisibility());
            stateEdit.commit();
        });

        key.setOnClickListener(v->{
            keyScreen.setVisibility(View.VISIBLE);
            key.setVisibility(View.GONE);
            haveTheKey = true;
            stateEdit.putInt("z2keyScreen", keyScreen.getVisibility());
            stateEdit.putInt("z2keyAfterClick", key.getVisibility());
            stateEdit.putBoolean("z2HaveTheKey", haveTheKey);
            stateEdit.commit();
        });
        /* endRegion */
    }
}