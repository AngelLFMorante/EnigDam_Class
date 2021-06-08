package pm.iesvives.enigdam_class.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import pm.iesvives.enigdam_class.Entity.Game;
import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.Fragments.DialogEditPlayer;
import pm.iesvives.enigdam_class.R;
import pm.iesvives.enigdam_class.Utils.Settings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends MainActivity implements DialogEditPlayer.OnInputListener {

    /*We retrieve the parameters we received from the DialogFragment*/
    @Override
    public void sendInput(PlayerDto playerInput, String usernameInput) {
        username = usernameInput;
        player = playerInput;
        setInputToTextView();
    }

    /**
     * we change the result we receive by means of the fragment
     */
    private void setInputToTextView() {
        usernameText.setText(username);
    }

    private Button btnEdit, btnDelete, btnBack;
    private TextView bestTime, bestScore;
    private Bundle bundle;
    private String username;
    private String time;
    private String score;
    private String deleteTitle;
    private String deleteMessage;
    private String titleSuccess;
    private String titleNotSuccess;
    private boolean delete = true;
    private TextView usernameText;
    private PlayerDto player = new PlayerDto();
    private SharedPreferences preferences;
    private SharedPreferences.Editor editorShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        preferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        editorShared = preferences.edit();

        Intent intentSession = getIntent();
        bundle = intentSession.getExtras();

        if ((PlayerDto) bundle.getSerializable("player") == null) {
            editorShared.clear();
            editorShared.apply();
            Intent backToHome = new Intent(Profile.this, Start.class);
            startActivity(backToHome);
        } else {
            player = (PlayerDto) bundle.getSerializable("player");
        }

        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        btnBack = findViewById(R.id.buttonsBack);
        usernameText = findViewById(R.id.profilePlayer);
        bestTime = findViewById(R.id.profileTime);
        bestScore = findViewById(R.id.profileScore);
        deleteTitle = getResources().getString(R.string.deleteTitle);
        deleteMessage = getResources().getString(R.string.deleteMessage);
        titleSuccess = getResources().getString(R.string.successDelete);
        titleNotSuccess = getResources().getString(R.string.notSuccessDelete);

        usernameText.setText(player.getUsername());

        //we get the best score from the user
        int high = 0;
        int low;
        for (Game best : player.getGames()) {
            low = Integer.parseInt(best.getScore());
            if (low > high) {
                high = low;
                time = best.getTime();
                score = "" + high;
            }
        }

        if (time != null && score != null) {
            bestTime.setText(time);
            bestScore.setText(score);
        }

        //this is a small animation for the button
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        ActionsButtons();
    }

    /**
     * listener actions and button clicks
     */
    @SuppressLint("ClickableViewAccessibility")
    private void ActionsButtons() {
        btnBack.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnBack.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnBack.startAnimation(scaleDown);
                Intent intent = new Intent(Profile.this, Lobby.class);
                intent.putExtra("player", player);
                startActivity(intent);
            }
            return true;
        });

        btnEdit.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnEdit.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnEdit.startAnimation(scaleDown);
                DialogEditPlayer dialog = new DialogEditPlayer();
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "Dialog edit player");
            }
            return true;
        });

        btnDelete.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnDelete.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnDelete.startAnimation(scaleDown);
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle(deleteTitle);
                alert.setMessage(deleteMessage);
                alert.setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    // continue with delete
                    deletePlayer(player.getId());
                });
                alert.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    // close dialog
                    dialog.cancel();
                });
                alert.show();
            }
            return true;
        });
    }

    /**
     * we delete the user
     * @param id id user
     */
    private void deletePlayer(Integer id) {

        Settings.RESPONSE_CLIENT.getService().deletePlayer(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                delete = response.isSuccessful();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
                delete = false;
            }
        });

        if (delete) {
            editorShared.clear();
            editorShared.apply();
            Intent intent = new Intent(Profile.this, Start.class);
            startActivity(intent);
        }
    }

}