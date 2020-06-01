package com.example.android.aikeepwithazureversion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import cn.pedant.SweetAlert.SweetAlertDialog;

// 注意 MainActivity 是繼承自 AppCompatActivity
// 可能套件的作者當時也是這麼做的吧
// 要繼承自 Activity 的方法再另外找時間研究了...
public class MainActivity extends AppCompatActivity
{
    private IntentIntegrator scanIntegrator;
    private ImageView homeImg;
    private ImageView payImg;
    private ImageView couponImg;
    private ImageView scan;
    private ImageView purchaseButton;
    private TextView item;
    private ImageView itemImg;
    private TextView price;
    private String priceSum;
    private String itemName;
    private String tagName;
    private TextView tag;
    private JSONObject mCurrentItemJsonObject;
    private TextView title;
    String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    String order1 =
            "燻雞沙拉飯糰 28元" +
                    "\n愛之味麥仔茶 250ml 15元" +
                    "\n雀巢焙煎有機茶 550ml 14元" +
                    "\n一日野菜-凱薩沙拉 55元" +
                    "\n總額: 112元";
    String order2 =
            "3M隱形膠帶 68元" +
                    "\n施德樓金屬製圖自動鉛筆 544元" +
                    "\nSOMSOM可愛雲便利貼 110元" +
                    "\n三菱自動鉛筆芯 149元" +
                    "\n白金牌電腦閱卷鉛筆 99元" +
                    "\nPLUS 電動削鉛筆機 699元" +
                    "\n總額: 1601元";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeImg = findViewById(R.id.home);
        payImg = findViewById(R.id.pay);
        couponImg = findViewById(R.id.coupon);
        
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

    //購買按鈕處理
    public void purchase(View v){



        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("確認支付");
        dialog.setMessage("請確認商品無誤後再按下支付");
        dialog.setPositiveButton("支付", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                process();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dialog.show();
    }

    public void process() {
    Intent intent = new Intent(this, Main2Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("item", itemName);
    intent.putExtra("price", priceSum);
    intent.putExtra("tag", tagName);

    try {
        String jsonArrayString = getSharedPreferences("data", MODE_PRIVATE)
                .getString("data_array", "[]");

        Log.d("Kawa", "Read from  SP: " + jsonArrayString);

        JSONArray jArray = new JSONArray(jsonArrayString);

        jArray.put(mCurrentItemJsonObject);


        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        pref.edit()
                .putString("data_array", jArray.toString())
                .commit();

        Log.d("Kawa", "Sava to SP: " + jArray.toString());

        startActivity(intent);

    } catch (JSONException e) {
        e.printStackTrace();
    }
    }
    public void order1(View view) throws JSONException {
        JSONObject obj1 = new JSONObject();
        obj1.put("itemName","展示訂單一");
        obj1.put("id",0);
        itemImg = findViewById(R.id.itemPhoto);
        itemImg.setImageDrawable(getResources().getDrawable(R.drawable.order1small));
        title = findViewById(R.id.title);
        title.setText(mydate+"\n"+order1);
        obj1.put("mydate",mydate);

        mCurrentItemJsonObject = obj1;

        scan = findViewById(R.id.scan);
        scan.setVisibility(View.INVISIBLE);
        purchaseButton = findViewById(R.id.purchase);
        purchaseButton.setVisibility(View.VISIBLE);
        RelativeLayout itemlayout = findViewById(R.id.itemlayout);
        itemlayout.setVisibility(View.VISIBLE);
        Button order1 = findViewById(R.id.order1Button);
        order1.setClickable(false);
        Button order2 = findViewById(R.id.order2Button);
        order2.setClickable(false);

    }
    public void order2(View view) throws JSONException {
        JSONObject obj2 = new JSONObject();
        obj2.put("itemName","展示訂單二");
        obj2.put("id",1);
        itemImg = findViewById(R.id.itemPhoto);
        itemImg.setImageDrawable(getResources().getDrawable(R.drawable.order2));
        title = findViewById(R.id.title);
        title.setText(mydate+"\n"+order2);
        obj2.put("mydate",mydate);

        mCurrentItemJsonObject = obj2;

        scan = findViewById(R.id.scan);
        scan.setVisibility(View.INVISIBLE);
        purchaseButton = findViewById(R.id.purchase);
        purchaseButton.setVisibility(View.VISIBLE);
        RelativeLayout itemlayout = findViewById(R.id.itemlayout);
        itemlayout.setVisibility(View.VISIBLE);

        Button order1 = findViewById(R.id.order1Button);
        order1.setClickable(false);
        Button order2 = findViewById(R.id.order2Button);
        order2.setClickable(false);

    }
//    public void onbuttonclick(View v)
//    {
//
//        scanIntegrator = new IntentIntegrator(MainActivity.this);
//        scanIntegrator.setPrompt("請將QRcode放入框內，即可進行掃描");
//        scanIntegrator.setTimeout(300000);
//        scanIntegrator.initiateScan();
//        scanIntegrator.setCameraId(0);
//        scanIntegrator.setOrientationLocked(false);
//
//
//    }


//    public void onActivityResult(int requestCode, int resultCode, Intent intent)
//    {
//        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
////        item = findViewById(R.id.item);
//        itemImg = findViewById(R.id.itemPhoto);
////        price = findViewById(R.id.price);
//        purchaseButton = findViewById(R.id.purchase);
//        scan = findViewById(R.id.scan);
////        tag = findViewById(R.id.tag);
//        title = findViewById(R.id.title);
//        RelativeLayout itemlayout                   = findViewById(R.id.itemlayout);
//
//        if (scanningResult != null)
//        {
//           try{
//               //這邊處理QR code內JSON
//
//               if (scanningResult.getContents() == null)
//                   return;
//
//               JSONObject obj = new JSONObject(scanningResult.getContents());
//               itemName = obj.getString("itemName");
//               if(itemName.equals("展示訂單一")) {
//                   itemImg.setImageDrawable(getResources().getDrawable(R.drawable.order1small));
//
//                   title.setText(mydate+"\n"+order1);
//                   obj.put("mydate",mydate);
//               }else if(itemName.equals("展示訂單二")){
//                   itemImg.setImageDrawable(getResources().getDrawable(R.drawable.order2));
//
//                   title.setText(mydate+"\n"+order2);
//                   obj.put("mydate",mydate);
//               }
//
//               mCurrentItemJsonObject = obj;
//
//               scan.setVisibility(View.INVISIBLE);
//               purchaseButton.setVisibility(View.VISIBLE);
//               itemlayout.setVisibility(View.VISIBLE);
//           }catch (JSONException e){
//               e.printStackTrace();
//           }
//        }
//        else
//        {
//            super.onActivityResult(requestCode, resultCode, intent);
//            Toast.makeText(getApplicationContext(),"發生錯誤",Toast.LENGTH_LONG).show();
//        }
//    }
}