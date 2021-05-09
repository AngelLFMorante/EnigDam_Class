package pm.iesvives.enigdam_class.Game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import pm.iesvives.enigdam_class.Activities.MainActivity;
import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.R;

public class StartGame extends MainActivity {

    FragmentTransaction transaction;
    Fragment fragmentZone1;
    private PlayerDto player = new PlayerDto();
    private String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        player  = (PlayerDto) bundle.getSerializable("player");
        difficulty = bundle.getString("difficulty");

        fragmentZone1 = new Zone1();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_nav_game, fragmentZone1).commit();

    }


}