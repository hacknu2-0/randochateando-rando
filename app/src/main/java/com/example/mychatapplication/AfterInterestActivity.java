package com.example.mychatapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AfterInterestActivity extends AppCompatActivity {
    Button next;
    ImageView politics;
    ImageView technology;
    ImageView music;
    ImageView science;
    ImageView dance;
    ImageView finance;
    ImageView gym;
    ImageView cars;
    ImageView cooking;
    ImageView travel;
    public void onClickButton(View view) {
        setColorButton();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
    public void onClickImage(View view) {
        setColorImage();
    }
    private void setColorButton(){
        next.setBackgroundResource(R.color.colorPrimary);
    }
    private void setColorImage(){
        next.setBackgroundResource(R.color.colornew);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_interest);
        next = (Button) findViewById(R.id.btn_next);
    }
}