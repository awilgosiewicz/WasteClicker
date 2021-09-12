package com.example.wasteclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvPoints;
    private int points;
    private int cps;
    private TrashCounter trashCounter = new TrashCounter();
    private Typeface ttf;
    private Random random;
    private TextView tvCps;

    private int[] Images = {R.drawable.grabber};
    private String[] Names = {"Additional grabber \n cost:100"};
    private String[] Description = {"+100 waste per second"};


    Handler handler = new Handler();
    Runnable runnable;
    int delay = 10 * 1000; //  * 1000 miliseconds //loop every x seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        tvPoints = findViewById(R.id.tvPoints);
        ttf = Typeface.createFromAsset(getAssets(), "28DaysLater.ttf");
        tvPoints.setTypeface(ttf);
        tvCps = findViewById(R.id.tvCps);
        tvCps.setTypeface(ttf);

        random = new Random();
        open();
    }

    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                OnUpdate();
            }
        }, delay);
        super.onResume();
    }

    @Override

    public void onPause(){
        handler.removeCallbacks(runnable);
        super.onPause();
        save();
    }

    public void onClick(View v){
        if(v.getId() == R.id.imgTrash){
            Animation a = AnimationUtils.loadAnimation(this, R.anim.trash_animation);
            a.setAnimationListener(new SimpleAnimationListener(){
                @Override
                public void onAnimationEnd(Animation animation){
                    trashClick();

                }
            });
            v.startAnimation(a);
        } else if (v.getId() == R.id.btnShop){
            showShopFragment();
            save();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            showTrashFragment();
            startActivity(new Intent(this, GameActivity.class));
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showTrashFragment(){
        ViewGroup container = findViewById(R.id.container);
        container.removeAllViews();
        container.addView(getLayoutInflater().inflate(R.layout.activity_main, null));

    }

    private void trashClick(){
        points++;
        tvPoints.setText(Integer.toString(points));
        showToast(R.string.clicked);
    }

    private void showToast(int stringID){

        final Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER|Gravity.LEFT, random.nextInt(600) + 80, random.nextInt(600) - 250);
        toast.setDuration(Toast.LENGTH_SHORT);
        TextView textView = new TextView(this);
        textView.setText(stringID);
        textView.setTextSize(40f);
        textView.setTextColor(Color.BLACK);
        textView.setTypeface(ttf);
        toast.setView(textView);
        CountDownTimer toastCountDown;

        toastCountDown = new CountDownTimer(500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                toast.show();
            }

            @Override
            public void onFinish() {
                toast.cancel();
            }
        };

        toast.show();
        toastCountDown.start();
    }

    private void update(){
        points += cps/1;
        tvPoints.setText(Integer.toString(points));
        tvCps.setText(Integer.toString(cps) + "cps");
    }

    private void save(){
        SharedPreferences preferences = getSharedPreferences("GAME", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("cps", cps);
        editor.putInt("trash", points);
        editor.commit();
    }

    private void open(){
        SharedPreferences preferences = getSharedPreferences("GAME", 0);
        cps = preferences.getInt("cps" , 0);
        points = preferences.getInt("trash", 0);

    }

    private void showShopFragment(){
        ViewGroup container = findViewById(R.id.container);
        ShopAdapter shopAdapter = new ShopAdapter();
        container.removeAllViews();
        container.addView(getLayoutInflater().inflate(R.layout.shop_activity, null));
        ((ListView)findViewById(R.id.listShop)).setAdapter(shopAdapter);
    }

    private void updateCps(int i){
        cps += i;
    }

    private void updatePoints(int i){
        points -= i;
    }

    public class TrashCounter {
        private Timer timer;

        public TrashCounter(){
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask(){
                @Override
                public void run(){
                    runOnUiThread(() -> update());
                }
            }, 1000, 10);
        }
    }

    public class ShopAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return Images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.item_listview, null);

            ((ImageView)convertView.findViewById(R.id.imgItem)).setImageResource(Images[position]);
            ((TextView)convertView.findViewById(R.id.tvName)).setText(Names[position]);
            ((TextView)convertView.findViewById(R.id.tvDescription)).setText(Description[position]);

            convertView.setOnClickListener(v -> {
                if(getCount() == 1){
                    if(points >= 100){
                        updateCps(2);
                        updatePoints(2);
                        save();
                    } else {
                        (new androidx.appcompat.app.AlertDialog.Builder(GameActivity.this)).setMessage("Not enough points.").show();
                    }
                }
            });

            return convertView;
        }
    }

    public void OnShow(View view){
        String type = "scoreboard";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type);
    }

    public void OnUpdate(){

        Intent intent = getIntent();

        String str_username = intent.getStringExtra("name_key");
        String str_score = tvPoints.getText().toString();

        String type = "update";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, str_username, str_score);

    }


}

