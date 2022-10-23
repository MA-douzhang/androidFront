package com.example.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.*;
import androidx.annotation.NonNull;
import com.alibaba.fastjson.JSON;
import com.example.mytest.adapter.ContentsAdapter;
import com.example.mytest.entity.Content;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.example.mytest.service.ContentService;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private OkHttpClient client = new OkHttpClient();
    private ListView listView;
    private List<Content> contents;
    private Button publishButton;

    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_list);

        publishButton = findViewById(R.id.publish_content);
        listView = findViewById(R.id.list_item);
        //标题

        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ContentService.class));
            }
        });
        handler = new Handler(){

            @Override
            public void handleMessage(@NonNull @NotNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0x80:
                        ContentsAdapter newsAdapter = new ContentsAdapter(MainActivity.this,R.layout.contents_item,contents);
                        listView.setAdapter(newsAdapter);
                        break;
                }
            }
        };
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Content content = contents.get(position);
                Intent i =new Intent(MainActivity.this,ContentActivity.class);
                i.putExtra("contentTitle",content.getTitle());
                i.putExtra("contentContent",content.getContent());
                startActivity(i);
            }
        });
    }
    public void getList(View view){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url("http://10.20.1.246:9091/content/")
                        .get()
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.d("TAG", "onFailure: ");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                        Log.d("TAG", "onResponse: " + response.body().string());
                        contents = JSON.parseArray(response.body().string(), Content.class);
                        Message message = handler.obtainMessage();
                        message.what = 0x80;
                        handler.sendMessage(message);
                    }
                });
            }
        });
        thread.start();
    }

}