package pm.iesvives.enigdam_class.Activities;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import pm.iesvives.enigdam_class.Entity.Game;
import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.R;

public class Profile extends MainActivity{

    private Button btnEdit, btnDelete, btnBack;
    private TextView usernameText,bestTime,bestScore;
    private Bundle bundle;
    private int id;
    private String username;
    private String time;
    private String score;
    public PlayerDto player = new PlayerDto();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intentSession = getIntent();
        bundle = intentSession.getExtras();

        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        btnBack = findViewById(R.id.buttonsBack);
        usernameText = findViewById(R.id.profilePlayer);
        bestTime = findViewById(R.id.profileTime);
        bestScore = findViewById(R.id.profileScore);

        player  = (PlayerDto) bundle.getSerializable("player");
        id = bundle.getInt("id");
        username = bundle.getString("username");
        usernameText.setText(player.getUsername());

        int high = 0;
        int low = 0;
        for(Game best : player.getGames()){
            low = Integer.parseInt(best.getScore());
            if(low > high){
                high = low;
                time = best.getTime();
                score = ""+high;
            }
        }
        bestTime.setText(time);
        bestScore.setText(score);

        //this is a small animation for the button
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        ActionsButtons();
    }


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
/*              Intent intent = new Intent(Profile.this, DialogEditPlayer.class);
                intent.putExtra("player", player);
                startActivity(intent);*/
            }
            return true;
        });
    }
}