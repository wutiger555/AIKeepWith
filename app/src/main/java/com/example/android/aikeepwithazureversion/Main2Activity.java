package com.example.android.aikeepwithazureversion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private ImageView homeImg;
    private ImageView payImg;
    private ImageView couponImg;
    private ImageView chart;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Integer> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        homeImg = findViewById(R.id.home);
        payImg = findViewById(R.id.pay);
        couponImg = findViewById(R.id.coupon);
        chart = findViewById(R.id.chart);

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



        initImageBitmaps();
        //initRecyclerView();

    }

    private void initImageBitmaps(){


        try {
            String jsonArrayString = getSharedPreferences("data", MODE_PRIVATE)
                    .getString("data_array", "[]");
            JSONArray jArray = new JSONArray(jsonArrayString);


            for (int i = jArray.length()-1; i >=0; i--) {
                JSONObject obj = (JSONObject)jArray.get(i);

                int id = obj.getInt("id");

                mImageUrls.add(getItemDrawableResId(id));
                String itemName = obj.getString("itemName");
                String mydate = obj.getString("mydate");
                String itemContent = getItemContent(itemName,mydate);
                mNames.add(itemContent);
                if(jArray.length()==1){
                    chart.setImageResource(R.drawable.chart2);
                }else if(jArray.length()==2){
                    chart.setImageResource(R.drawable.chart3);
                }
            }

            Log.d("Kawa", "Read from  SP: " + jsonArrayString);


        } catch (JSONException e) {
            e.printStackTrace();
        }



        initRecyclerView();
    }
    private String getItemContent(String itemName,String mydate) {
        switch (itemName) {
            case "展示訂單一":
                return mydate + "\n燻雞沙拉飯糰 28元" +
                        "\n愛之味麥仔茶 250ml 15元" +
                        "\n雀巢焙煎有機茶 550ml 14元" +
                        "\n一日野菜-凱薩沙拉 55元";
            case "展示訂單二":
                return mydate + "\n3M隱形膠帶 68元" +
                        "\n施德樓金屬製圖自動鉛筆 544元" +
                        "\nSOMSOM可愛雲便利貼 110元" +
                        "\n三菱自動鉛筆芯 149元" +
                        "\n白金牌電腦閱卷鉛筆 99元" +
                        "\nPLUS 電動削鉛筆機 699元";
            default:
                return "";
        }
    }

    private int getItemDrawableResId(int id) {
        switch(id) {
            case 0:
                return R.drawable.order1small;
            case 1:
                return R.drawable.order2;
            case 2:
                return R.drawable.cake;
            case 3:
                return R.drawable.pen;
            case 4:
                return R.drawable.tshirt;
            default:
                return R.drawable.bubble_tea;
        }
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
