package com.example.mytest;

import android.content.Intent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ContentActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        titleTextView = findViewById(R.id.title_text);
        contentTextView = findViewById(R.id.content_text);
        //接收Intent信息
        Intent i =getIntent();
        titleTextView.setText(i.getStringExtra("contentTitle"));
        contentTextView.setText(i.getStringExtra("contentContent"));




    }
}