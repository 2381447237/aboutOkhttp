package com.okhttp.zhengbin.okhttpdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button getRequestBtn,btn,postRequestBtn,fileBtn,downBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getRequestBtn= (Button) findViewById(R.id.btn_get);
        getRequestBtn.setOnClickListener(this);
        postRequestBtn= (Button) findViewById(R.id.btn_post);
        postRequestBtn.setOnClickListener(this);
        btn= (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
        fileBtn= (Button) findViewById(R.id.btn_file);
        fileBtn.setOnClickListener(this);
        downBtn= (Button) findViewById(R.id.btn_download);
        downBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){

            case R.id.btn:

                getRequest();

                break;
            case R.id.btn_get:

                intent=new Intent(this,GetActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_post:

                intent=new Intent(this,PostActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_file:
                intent=new Intent(this,FileActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_download:
                intent=new Intent(this,DownActivity.class);
                startActivity(intent);
                break;
        }

    }


    //enqueue是异步 execute是同步
    private void getRequest() {

        OkHttpClient client=new OkHttpClient();

        String url="http://www.imooc.com/api/teacher?type=4&num=30";

        Request request=new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.e("2017/9/9","失败=="+e.getLocalizedMessage());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String result=response.body().string();
                Log.e("2017/9/9","成功=="+result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                    }
                });

                if(response.body()!=null){
                    response.close();
                }
            }
        });

    }
}
