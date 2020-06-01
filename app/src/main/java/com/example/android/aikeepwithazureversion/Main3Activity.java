package com.example.android.aikeepwithazureversion;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {
    private ImageView homeImg;
    private ImageView payImg;
    private ImageView couponImg;
    private final String CHANNEL_ID = "perstional_notifications";
    private final int NOTIFICATION_ID = 001;
    private TextView timer1 ;
    private Button notify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        homeImg = findViewById(R.id.home);
        payImg = findViewById(R.id.pay);
        couponImg = findViewById(R.id.coupon);
        timer1 = findViewById(R.id.timer);

        homeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHome();
            }
        });
        payImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPay();
            }
        });
        couponImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCoupon();
            }
        });

        notify = findViewById(R.id.notify);
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        notification();
                        // yourMethod();
                    }
                }, 5000);   //5 seconds
            }
        });


        }
    public void notification(){
        Button notify = findViewById(R.id.notify);
        notify.setClickable(false);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        builder.setContentTitle("優惠訊息");
        builder.setContentText("全家京江店(距離15m) 發給您一則限時優惠");
        builder.setColor(Color.RED);
        builder.setDefaults(Notification.DEFAULT_ALL);

        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        builder.setChannelId(CHANNEL_ID).build();
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
        RelativeLayout sales = findViewById(R.id.sales);
        sales.setVisibility(View.VISIBLE);

        CountDownTimer timer = new CountDownTimer(120000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                timer1.setText(millisUntilFinished / 1000 + "秒");
            }
            @Override
            public void onFinish() {
                RelativeLayout sales = findViewById(R.id.sales);
                sales.setVisibility(View.INVISIBLE);

            }
        };
        timer.start();

    }

    public void openHome(){
        Intent intent = new Intent(this, Main2Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
    public void openPay(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
    public void openCoupon(){
        Intent intent = new Intent(this, Main3Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
