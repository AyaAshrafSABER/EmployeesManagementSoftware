package com.example.android.employeesmanagementsoftware;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/*
made by Aya
 */
// see how to connect 3 main activity ** department ** employies **tasks
public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 750;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home = new Intent(MainActivity.this,StartingPageActivity.class);
                startActivity(home);
                finish();
            }
        },SPLASH_TIME_OUT);

    }
}
