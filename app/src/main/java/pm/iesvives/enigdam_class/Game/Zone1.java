package pm.iesvives.enigdam_class.Game;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import pm.iesvives.enigdam_class.CountDownTimer.CountTimer;
import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.R;

public class Zone1 extends Fragment {

    private PlayerDto player = new PlayerDto();
    private String difficulty;
    private String countTimer;
    protected Animation scaleUp, scaleDown;
    private Button btnNext, btnPrevious;
    private Button btnBriefcaseOpen, btnBriefcaseClose, btnBriefcaseVoid;
    private Button btnDrawerOpen, btnDrawerClose;
    private Button btnAutowiredDrawer, btnAutowired;
    private ImageView binaryTest, screenComputer;
    private boolean haveConexion = false;

    public Zone1() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Zone1.
     */
    // TODO: Rename and change types and number of parameters, TENGO QUE MIRAR BIEN QUE HACE ESTA FUNCION
    public static Zone1 newInstance(PlayerDto player) {
        Zone1 fragment = new Zone1();
        Bundle args = new Bundle();
        args.putSerializable("player", player);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO esto es para coger los parametros que recibamos entre fragments.
        getActivity().getSupportFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                player = (PlayerDto) bundle.getSerializable("player");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_zone1, container, false);

        Bundle bundle = getArguments();
        assert bundle != null;
        player  = (PlayerDto) bundle.getSerializable("player");
        difficulty = bundle.getString("difficulty");
        countTimer = bundle.getString("timer");

        Log.i("username: ", player.getUsername());
        Log.i("difficulty: ", difficulty);

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
        btnAutowired = view.findViewById(R.id.btnAutowired);
        screenComputer = view.findViewById(R.id.imgComputer);



        ActionsButtons();

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void ActionsButtons() {

        btnNext.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnNext.startAnimation(scaleUp);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                btnNext.startAnimation(scaleDown);
                Zone2 z2 = new Zone2();
//                z2.setArguments(bundle);
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
//                z4.setArguments(bundle);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().add(R.id.fragment_nav_game, z4).addToBackStack(null).commit();
            }
            return true;
        });

        btnBriefcaseClose.setOnClickListener(v -> btnBriefcaseOpen.setVisibility(View.VISIBLE));
        btnBriefcaseOpen.setOnClickListener(v -> {
            binaryTest.setVisibility(View.VISIBLE);
            btnBriefcaseVoid.setVisibility(View.VISIBLE);
        });
        binaryTest.setOnClickListener(v -> {
            binaryTest.setVisibility(View.INVISIBLE);
            btnBriefcaseVoid.setVisibility(View.INVISIBLE);
        });
        btnDrawerClose.setOnClickListener(v -> {
            btnDrawerOpen.setVisibility(View.VISIBLE);
            btnAutowiredDrawer.setVisibility(View.VISIBLE);
        });
        btnAutowiredDrawer.setOnClickListener(v -> {
            screenComputer.setVisibility(View.VISIBLE);
            btnAutowiredDrawer.setVisibility(View.INVISIBLE);
            haveConexion = true;
        });
//        if(haveConexion){
//
//        }
        screenComputer.setOnClickListener(v -> {
            Toast.makeText(this.getContext(), "PRUEBA PANTALLA", Toast.LENGTH_LONG).show();
            CountTimer.pauseTimer();
            Log.i("timer: ", CountTimer.timeText);
        });
    }


}