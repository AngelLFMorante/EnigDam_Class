package pm.iesvives.enigdam_class.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.HistoryFragment;
import pm.iesvives.enigdam_class.R;

public class Lobby extends MainActivity implements HistoryFragment.OnFragmentInteractionListener {

    private FrameLayout fragmentContainer;
    private Button btnExit, btnProfile, btnHistory, btnScores, btnMusic, btnStart;
    private boolean session = false;
    private  SharedPreferences preferences;
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
        btnMusic = findViewById(R.id.buttonsSound);
        btnStart = findViewById(R.id.buttonStart);
        TextView welcome = findViewById(R.id.textUsername);
        fragmentContainer = findViewById(R.id.fragment_container);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        player  = (PlayerDto) bundle.getSerializable("player");

        //we collect data from logged-in users
        welcome.setText(player.getUsername());

        preferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        editorShared = preferences.edit();

        if(!session && !initialSession()){
            //we save session data, in sharedPreferences
            editorShared.putString("username", player.getUsername());
            editorShared.putInt("id", player.getId());
            editorShared.apply();
            session = true;
        }

        //this is a small animation for the button
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        ActionsButtons();

    }

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
                //TODO hacer para que llege a la lógica del juego.
//                Intent intent = new Intent(Lobby.this, Game.class);
//                intent.putExtra("player", player);
//                startActivity(intent);
            }
            return true;
        });
        btnMusic.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnMusic.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnMusic.startAnimation(scaleDown);
                //TODO parar la musica y si le vuelve a dar que se encienda la música
/*                Intent intent = new Intent(Lobby.this, MainActivity.class);
                startActivity(intent);*/
            }
            return true;
        });
    }

    private void openFragment() {
        HistoryFragment fragment = HistoryFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.addToBackStack(null).setReorderingAllowed(true);
        transaction.add(R.id.fragment_container, fragment, "HISTORY_FRAGMENT").commit();
    }

    @Override
    public void onFragmentInteraction() {
        onBackPressed();
    }
}