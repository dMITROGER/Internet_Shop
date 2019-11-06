package com.example.dmytro.dmytrogerascomp304_001_assignment4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class RepresActivity extends AppCompatActivity {
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repres);
        SharedPreferences myPref = getSharedPreferences("OrderRepData", MODE_PRIVATE);
        String myString = myPref.getString("userName","");
        TextView tView = (TextView)findViewById(R.id.tvWelcome);
        tView.setText("Welcome: "+myString);
    }

    public void onButtonClicked(View view) {

        switch (view.getId()) {

            case R.id.btOrdersData:

                intent = new Intent(this, RepresViewAllOrders.class);
                startActivity(intent);
                break;

            case R.id.btProductData:

                intent = new Intent(this, ItemActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

    }
}
