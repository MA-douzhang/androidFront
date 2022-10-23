package com.example.mytest.service;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mytest.R;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ContentService extends AppCompatActivity {
    private EditText titleEdit;
    private EditText contentEdit;
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleEdit =  findViewById(R.id.title_editText);
        contentEdit = findViewById(R.id.content_editText);

    }
    //发布文章
    public void postContent(View view){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String title = titleEdit.getText().toString();
                String content = contentEdit.getText().toString();
                RequestBody requestBody = new FormBody.Builder().add("title",title).add("content",content).build();
                Request request = new Request.Builder()
                        .url("http://10.20.1.246:9091/content/save")
                        .post(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.d("TAG", "onFailure: ");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Log.d("TAG", "r "+response.body().string());
                        ContentService.this.finish();
                    }
                });
            }
        });
        thread.start();
    }


}
