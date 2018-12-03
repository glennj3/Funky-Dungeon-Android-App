package github.com.triplefrequency.funkydungeon.ui.dice;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import github.com.triplefrequency.funkydungeon.R;
import github.com.triplefrequency.funkydungeon.ui.MainActivity;

import java.util.Random;

import static android.content.Context.MODE_PRIVATE;


public class TwoDice_Fragment extends Fragment {

    //private static final String PREFS_NAME = "prefs";

    public Random rando = new Random();

    private TextView total;
    private Button rollDice;
    private ImageView dice1, dice2;
    private SensorManager sense;
    private float acelVal;
    private float acelLast;
    private float shake;
    private int rollTotal;

    public TwoDice_Fragment(){}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        Configuration config = getActivity().getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE)
            return inflater.inflate(R.layout.lm_2dice_roll, container, false);
        else
            return inflater.inflate(R.layout.pm_2dice_roll, container, false);

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        rollDice = view.findViewById(R.id.rollDice);
        dice1 = view.findViewById(R.id.diceOne);
        dice2 = view.findViewById(R.id.diceTwo);
        total = view.findViewById(R.id.Total);

        sense = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sense.registerListener(sensorListener, sense.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

        rollDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diceAnim();
            }
        });
    }
    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            acelLast = acelVal;
            acelVal = (float) Math.sqrt((double)(x*x + y*y + z+z));
            float delta = acelVal - acelLast;
            shake = shake * 0.9f + delta;
            if (shake>12){
                diceAnim();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void onDestroy(){
        super.onDestroy();
        final Intent i = new Intent(getActivity().getBaseContext(),
                MainActivity.class);
        //PACK DATA
        i.putExtra("ROLL_TOTAL", rollTotal);
        getActivity().startActivity(i);
    }

    public final int diceRoll(){
        return rando.nextInt(20)+1;
    }

    private void diceAnim(){
        final Animation diceAnim1 = AnimationUtils.loadAnimation(getActivity()
                .getBaseContext().getApplicationContext(),R.anim.shake);
        final Animation diceAnim2 = AnimationUtils.loadAnimation(getActivity()
                .getBaseContext().getApplicationContext(),R.anim.shake);

        final Animation.AnimationListener animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rollDice.setEnabled(false);
                sense.unregisterListener(sensorListener);
                setDice();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        diceAnim1.setAnimationListener(animationListener);
        diceAnim2.setAnimationListener(animationListener);

        dice1.startAnimation(diceAnim1);
        dice2.startAnimation(diceAnim2);

    }

    private void setDice(){
        int rollOne = diceRoll();
        int rollTwo = diceRoll();
        rollTotal = rollOne + rollTwo;

        String diceFile1 = "d20_side"+String.valueOf(rollOne);
        String diceFile2 = "d20_side"+String.valueOf(rollTwo);

        int resource1 = getResources().getIdentifier(diceFile1, "drawable", "github.com.triplefrequency.funkydungeon");
        int resource2 = getResources().getIdentifier(diceFile2, "drawable", "github.com.triplefrequency.funkydungeon");

        total.setText(String.format("You rolled %s!!!", String.valueOf(rollTotal)));
        total.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


        dice1.setImageResource(resource1);
        dice2.setImageResource(resource2);

        //return rollTotal;

    }

    /*
    //This function returns the total back to the main activity.
    private void returnRoll(int total){
        final Intent i = new Intent(getActivity().getBaseContext(),
                MainActivity.class);



        new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // do something after 1s
            }

            @Override
            public void onFinish() {
                getActivity().startActivity(i);
            }

        }.start();
    }*/


}
