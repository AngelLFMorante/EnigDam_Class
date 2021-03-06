package pm.iesvives.enigdam_class.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.Fragments.HistoryFragment;
import pm.iesvives.enigdam_class.R;

public class Lobby extends MainActivity implements HistoryFragment.OnFragmentInteractionListener {

    private Button btnExit, btnProfile, btnHistory, btnScores, btnStart;
    private boolean session = false;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editorShared;
    private PlayerDto player = new PlayerDto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        btnExit = findViewById(R.id.buttonsBack);
        btnProfile = findViewById(R.id.btnProfile);
        btnHistory = findViewById(R.id.btnHistory);
        btnScores = findViewById(R.id.btnScore);
        btnStart = findViewById(R.id.buttonStart);
        TextView welcome = findViewById(R.id.textUsername);

        preferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        editorShared = preferences.edit();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        //retrieve user data
        if ((PlayerDto) bundle.getSerializable("player") == null) {
            editorShared.clear();
            editorShared.apply();
            Intent backToHome = new Intent(Lobby.this, Start.class);
            startActivity(backToHome);
        } else {
            player = (PlayerDto) bundle.getSerializable("player");
        }

        //we collect data from logged-in users
        welcome.setText(player.getUsername());

        if (!session && !initialSession()) {
            //we save session data, in sharedPreferences
            editorShared.putString("username", player.getUsername());
            editorShared.putInt("id", player.getId());
            editorShared.apply();
            session = true;
        } else if (initialSession()) {
            session = true;
        }

        //this is a small animation for the button
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        ActionsButtons();

    }

    /**
     * we check whether the user is logging in for the first time or is already logged in
     *
     * @return true or false
     */
    private boolean initialSession() {
        String name = preferences.getString("username", "empty");
        int identity = preferences.getInt("id", 0);
        return !name.equals("empty") && identity != 0;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void ActionsButtons() {
        btnExit.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnExit.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnExit.startAnimation(scaleDown);
                editorShared.clear();
                editorShared.commit();
                Intent intent = new Intent(Lobby.this, Start.class);
                startActivity(intent);
            }
            return true;
        });
        btnProfile.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnProfile.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnProfile.startAnimation(scaleDown);
                Intent intent = new Intent(Lobby.this, Profile.class);
                intent.putExtra("player", player);
                startActivity(intent);
            }
            return true;
        });
        btnHistory.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnHistory.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnHistory.startAnimation(scaleDown);
                openFragment();
            }
            return true;
        });
        btnScores.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnScores.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnScores.startAnimation(scaleDown);
                Intent intent = new Intent(Lobby.this, Scores.class);
                intent.putExtra("player", player);
                intent.putExtra("session", session);
                startActivity(intent);
            }
            return true;
        });

        btnStart.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnStart.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnStart.startAnimation(scaleDown);
                Intent intent = new Intent(Lobby.this, Difficulty.class);
                intent.putExtra("player", player);
                startActivity(intent);
            }
            return true;
        });

    }

    /**
     * we open the fragment with animation
     */
    private void openFragment() {
        HistoryFragment fragment = HistoryFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.addToBackStack(null).setReorderingAllowed(true);
        transaction.add(R.id.fragment_container, fragment, "HISTORY_FRAGMENT").commit();
    }

    /**
     * we close fragment
     */
    @Override
    public void onFragmentInteraction() {
        onBackPressed();
    }
}