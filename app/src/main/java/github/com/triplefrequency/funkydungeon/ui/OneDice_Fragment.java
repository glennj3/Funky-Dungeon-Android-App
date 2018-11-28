package github.com.triplefrequency.funkydungeon.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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

import java.util.Random;

public class OneDice_Fragment extends Fragment {
    public Random rando = new Random();

    private TextView total;
    private Button rollDice;
    private ImageView dice1;
    private SensorManager sense;
    private float acelVal;
    private float acelLast;
    private float shake;

    public OneDice_Fragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        Configuration config = getActivity().getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE)
            return inflater.inflate(R.layout.lm_1dice_roll, container, false);
        else
            return inflater.inflate(R.layout.pm_1dice_roll, container, false);

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        rollDice = view.findViewById(R.id.rollDice);
        dice1 = view.findViewById(R.id.diceOne);
        total = view.findViewById(R.id.Total);

        sense = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sense.registerListener(sensorListener, sense.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

        rollDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation diceAnim1 = AnimationUtils.loadAnimation(getActivity()
                        .getBaseContext().getApplicationContext(),R.anim.shake);

                final Animation.AnimationListener animationListener = new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rollDice.setEnabled(false);
                        int roll = setDice();
                        returnRoll(roll);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                };
                diceAnim1.setAnimationListener(animationListener);

                dice1.startAnimation(diceAnim1);

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
                final Animation diceAnim1 = AnimationUtils.loadAnimation(getActivity()
                        .getBaseContext().getApplicationContext(),R.anim.shake);

                final Animation.AnimationListener animationListener = new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        rollDice.setEnabled(false);
                        int roll = setDice();
                        returnRoll(roll);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                };
                diceAnim1.setAnimationListener(animationListener);

                dice1.startAnimation(diceAnim1);

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    public final int diceRoll(){
        return rando.nextInt(20)+1;
    }

    private int setDice(){
        int rollOne = diceRoll();
        int rollTotal = rollOne;

        String diceFile1 = "d20_side"+String.valueOf(rollOne);


        int resource1 = getResources().getIdentifier(diceFile1, "drawable", "github.com.triplefrequency.funkydungeon");

        total.setText(String.format("You rolled %s!!!", String.valueOf(rollTotal)));
        total.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        dice1.setImageResource(resource1);

        return rollTotal;

    }
    //This function returns the total back to the main activity.
    private void returnRoll(int total){
        final Intent i = new Intent(getActivity().getBaseContext(),
                MainActivity.class);

        //PACK DATA
        i.putExtra("ROLL_TOTAL", total);
        //try { Thread.sleep(5000); }
        //catch (InterruptedException ex) { android.util.Log.d("YourApplicationName", ex.toString()); }
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
    }


}