package com.okhttp.zhengbin.okhttpdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
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

/**
 * Created by ZHengBin on 2017/9/10.
 */

public class GetActivity extends Activity{

    private Button getRequestBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);

        getRequestBtn= (Button) findViewById(R.id.btn_get);
        getRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getInfo();
            }
        });
    }

    private void getInfo(){

        String url="http://www.imooc.com/api/teacher?type=4&num=30";

        OkHttpClient client=new OkHttpClient();

        Request request=new Request.Builder().get().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("2017/9/9","失败=="+e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if (response.isSuccessful()) {//状态码在200到300之间

                    final String result = response.body().string();


                    Log.e("2017/9/9", "成功==" + result);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GetActivity.this, result, Toast.LENGTH_SHORT).show();

                        }
                    });

                    if (response.body() != null) {
                        response.close();
                    }


                }
            }
        });

    }

}
