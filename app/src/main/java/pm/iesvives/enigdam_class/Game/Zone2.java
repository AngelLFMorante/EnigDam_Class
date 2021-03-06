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

import pm.iesvives.enigdam_class.Fragments.DialogEndZone2;
import pm.iesvives.enigdam_class.Fragments.DialogFragmentPassword;
import pm.iesvives.enigdam_class.R;

public class Zone2 extends Fragment {

    protected Animation scaleUp, scaleDown;
    private Button btnNext, btnPrevious;
    private ImageView lampHint;
    private ImageView paperRed, paperYellow, paperGreen;
    private ImageView paperOpenRed, paperOpenYellow, paperOpenGreen;
    private ImageView bookShelf, openBookShelf, closeBookShelf;
    private ImageView drawerClose, drawerOpen, key, keyScreen;
    private boolean haveTheKey = false;
    private boolean zone3HaveThePendrive = false;
    private boolean isCorrectPassword = false;
    private ImageView computerPassword, screenComputer;
    private ImageView penDriveScreen;
    private SharedPreferences state;
    private SharedPreferences difficulty;
    private SharedPreferences.Editor stateEdit;
    private DialogEndZone2 dialog;
    private DialogFragmentPassword dialogPassword;

    private String[] hints = new String[8];

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
        paperRed = view.findViewById(R.id.zone2paperRed);
        paperYellow = view.findViewById(R.id.zone2paperYellow);
        paperGreen = view.findViewById(R.id.zone2paperGreen);
        paperOpenRed = view.findViewById(R.id.zone2paperOpenRed);
        paperOpenYellow = view.findViewById(R.id.zone2paperOpenYellow);
        paperOpenGreen = view.findViewById(R.id.zone2paperOpenGreen);
        drawerClose = view.findViewById(R.id.zone2CloseDoor);
        drawerOpen = view.findViewById(R.id.zone2OpenDoor);
        key = view.findViewById(R.id.zone2KeyDrawer);
        keyScreen = view.findViewById(R.id.zone2KeyScreen);
        computerPassword = view.findViewById(R.id.zone2ComputerPassword);
        screenComputer = view.findViewById(R.id.zone2ScreenComputer);

        state = getActivity().getSharedPreferences("States", getContext().MODE_PRIVATE);
        stateEdit = state.edit();
        //Difficulty game
        difficulty = getActivity().getSharedPreferences("Difficulty", getContext().MODE_PRIVATE);
        clickHintDifficulty();

        penDriveScreen = view.findViewById(R.id.zone3PenDriveScreen);

        //load screen status
        loadState(state);

        ActionsButtons();

        return view;
    }

    private void hintsZone2() {
        hints[0] = getResources().getString(R.string.z2hintCloset);
        hints[1] = getResources().getString(R.string.z2hintKey);
        hints[2] = getResources().getString(R.string.z2hintComputer);
        hints[3] = getResources().getString(R.string.z2hintPasswordComputer);
        hints[4] = getResources().getString(R.string.z2hintTakeBoard);
        hints[5] = getResources().getString(R.string.z2hintBehindPaper);
    }

    private void clickHintDifficulty() {
        if (difficulty.getString("difficulty", "notValue").equals("normal")) {
            lampHint.setImageResource(R.drawable.lamp_on);
            hintsZone2();
            lampHint.setOnClickListener(v -> {
                if (drawerOpen.getVisibility() == View.GONE) {
                    Toast.makeText(getContext(), hints[0], Toast.LENGTH_LONG).show();
                } else if (key.getVisibility() == View.VISIBLE) {
                    Toast.makeText(getContext(), hints[1], Toast.LENGTH_LONG).show();
                } else if (zone3HaveThePendrive) {
                    Toast.makeText(getContext(), hints[2], Toast.LENGTH_LONG).show();
                } else if (computerPassword.getVisibility() == View.VISIBLE && !isCorrectPassword) {
                    Toast.makeText(getContext(), hints[3], Toast.LENGTH_LONG).show();
                } else if (state.getInt("z3PaperWhite", 8) != 0) {
                    Toast.makeText(getContext(), hints[4], Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), hints[5], Toast.LENGTH_LONG).show();
                }
            });
        } else if (difficulty.getString("difficulty", "notValue").equals("hard")) {
            lampHint.setImageResource(R.drawable.lamp_off);
            lampHint.setOnClickListener(v -> {
                Toast.makeText(getContext(), getResources().getString(R.string.z4hintNoClues), Toast.LENGTH_LONG).show();
            });
        }
    }

    private void loadState(SharedPreferences state) {
        //VISIBLE == 0 , INVISIBLE == 4, GONE == 8
        if (state.getInt("z2DrawerOpen", 8) == 0) {
            drawerOpen.setVisibility(View.VISIBLE);
        }
        if (state.getInt("z2key", 8) == 0) {
            key.setVisibility(View.VISIBLE);
        }
        if (state.getInt("z2keyScreen", 8) == 0) {
            keyScreen.setVisibility(View.VISIBLE);
        }
        if (state.getInt("z2keyAfterClick", 0) == 8) {
            key.setVisibility(View.GONE);
        }
        if (state.getBoolean("z2HaveTheKey", false)) {
            haveTheKey = true;
        }
        if (state.getInt("z3PendriveScreen", 8) == 0) {
            penDriveScreen.setVisibility(View.VISIBLE);
        }
        if (state.getBoolean("z3HaveThePendrive", false)) {
            zone3HaveThePendrive = state.getBoolean("z3HaveThePendrive", false);
        }

        if (state.getInt("z2ScreenComputer", 8) == 0) {
            screenComputer.setVisibility(View.VISIBLE);
        }
        if (state.getInt("z2ComputerPassword", 8) == 0) {
            computerPassword.setVisibility(View.VISIBLE);
        }
        if (state.getBoolean("z2IsCorrectPassword", false)) {
            isCorrectPassword = state.getBoolean("z2IsCorrectPassword", false);
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
        bookShelf.setOnClickListener(v -> {
            if(paperOpenGreen.getVisibility() == View.VISIBLE){
                paperOpenGreen.setVisibility(View.GONE);
                paperGreen.setVisibility(View.VISIBLE);
            }else if( paperOpenYellow.getVisibility() == View.VISIBLE){
                paperOpenYellow.setVisibility(View.GONE);
                paperYellow.setVisibility(View.VISIBLE);
            }else if( paperOpenRed.getVisibility() == View.VISIBLE){
                paperOpenRed.setVisibility(View.GONE);
                paperRed.setVisibility(View.VISIBLE);
            }
            openBookShelf.setVisibility(View.VISIBLE);
            closeBookShelf.setVisibility(View.VISIBLE);
        });
        openBookShelf.setOnClickListener(v -> {
            openBookShelf.setVisibility(View.GONE);
            closeBookShelf.setVisibility(View.GONE);
        });
        /* endRegion */
        /* region paper */
        paperRed.setOnClickListener(v -> {
            if(paperOpenGreen.getVisibility() == View.VISIBLE){
                paperOpenGreen.setVisibility(View.GONE);
                paperGreen.setVisibility(View.VISIBLE);
            }else if( paperOpenYellow.getVisibility() == View.VISIBLE){
                paperOpenYellow.setVisibility(View.GONE);
                paperYellow.setVisibility(View.VISIBLE);
            }
            paperOpenRed.setVisibility(View.VISIBLE);
            paperRed.setVisibility(View.GONE);
        });
        paperOpenRed.setOnClickListener(v -> {
            paperRed.setVisibility(View.VISIBLE);
            paperOpenRed.setVisibility(View.GONE);
        });
        paperYellow.setOnClickListener(v -> {
            if(paperOpenGreen.getVisibility() == View.VISIBLE){
                paperOpenGreen.setVisibility(View.GONE);
                paperGreen.setVisibility(View.VISIBLE);
            }else if( paperOpenRed.getVisibility() == View.VISIBLE){
                paperOpenRed.setVisibility(View.GONE);
                paperRed.setVisibility(View.VISIBLE);
            }
            paperOpenYellow.setVisibility(View.VISIBLE);
            paperYellow.setVisibility(View.GONE);
        });
        paperOpenYellow.setOnClickListener(v -> {
            paperYellow.setVisibility(View.VISIBLE);
            paperOpenYellow.setVisibility(View.GONE);
        });
        paperGreen.setOnClickListener(v -> {
            if(paperOpenYellow.getVisibility() == View.VISIBLE){
                paperOpenYellow.setVisibility(View.GONE);
                paperYellow.setVisibility(View.VISIBLE);
            }else if( paperOpenRed.getVisibility() == View.VISIBLE){
                paperOpenRed.setVisibility(View.GONE);
                paperRed.setVisibility(View.VISIBLE);
            }
            paperOpenGreen.setVisibility(View.VISIBLE);
            paperGreen.setVisibility(View.GONE);
        });
        paperOpenGreen.setOnClickListener(v -> {
            paperGreen.setVisibility(View.VISIBLE);
            paperOpenGreen.setVisibility(View.GONE);
        });
        /* endRegion */
        /* region drawer and key */
        drawerClose.setOnClickListener(v -> {
            drawerOpen.setVisibility(View.VISIBLE);
            key.setVisibility(View.VISIBLE);
            stateEdit.putInt("z2DrawerOpen", drawerOpen.getVisibility());
            stateEdit.putInt("z2key", key.getVisibility());
            stateEdit.commit();
        });

        key.setOnClickListener(v -> {
            keyScreen.setVisibility(View.VISIBLE);
            key.setVisibility(View.GONE);
            haveTheKey = true;
            stateEdit.putInt("z2keyScreen", keyScreen.getVisibility());
            stateEdit.putInt("z2keyAfterClick", key.getVisibility());
            stateEdit.putBoolean("z2HaveTheKey", haveTheKey);
            stateEdit.commit();
        });
        /* endRegion */

        screenComputer.setOnClickListener(v -> {
            if (zone3HaveThePendrive) {
                screenComputer.setVisibility(View.GONE);
                computerPassword.setVisibility(View.VISIBLE);
                penDriveScreen.setVisibility(View.GONE);
                zone3HaveThePendrive = false;
                stateEdit.putInt("z3PendriveScreen", penDriveScreen.getVisibility());
                stateEdit.putInt("z2ScreenComputer", screenComputer.getVisibility());
                stateEdit.putInt("z2ComputerPassword", computerPassword.getVisibility());
                stateEdit.putBoolean("z3HaveThePendrive", zone3HaveThePendrive);
                stateEdit.commit();
            }
        });

        computerPassword.setOnClickListener(v -> {

            isCorrectPassword = state.getBoolean("z2IsCorrectPassword", false);

            if (!isCorrectPassword) {
                dialogPassword = new DialogFragmentPassword();
                dialogPassword.show(getActivity().getSupportFragmentManager(), "Dialog password");
            } else {
                dialog = new DialogEndZone2();
                dialog.show(getActivity().getSupportFragmentManager(), "DialogZone2");
            }

        });
    }

}