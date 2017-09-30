package com.okhttp.zhengbin.okhttpdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ZHengBin on 2017/9/10.
 */

public class PostActivity extends Activity implements View.OnClickListener{

    private Button formBtn,jsonBtn;

    private OkHttpClient httpClient;
   private  String url="http://www.imooc.com/api/teacher";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        httpClient=new OkHttpClient();

        formBtn= (Button) findViewById(R.id.btn_post_form);
        formBtn.setOnClickListener(this);
        jsonBtn= (Button) findViewById(R.id.btn_post_json);
        jsonBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_post_form:

                toForm("4","30");

                break;
            case R.id.btn_post_json:

                toJson("4","30");

                break;
        }

    }

    //form表单方式 注意代码是对的，但是接口是个大傻逼
    private void toForm(String typeStr,String numStr){
        //url="http://www.imooc.com/api/teacher?type=4&num=30";



        RequestBody body=new FormBody.Builder().add("type",typeStr).add("num",numStr).build();

        Request request=new Request.Builder().url(url).post(body).build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(response.isSuccessful()){

                    final String result=response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PostActivity.this,result,Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
    }

    //JSON方式 注意代码是对的，但是接口是个大傻逼
    private void toJson(String typeStr,String numStr){

        JSONObject jsonObj=new JSONObject();
        try {
            jsonObj.put("type",typeStr);
            jsonObj.put("num",numStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jsonParams=jsonObj.toString();

        RequestBody body=RequestBody.create(MediaType.parse("application/json"),jsonParams);

        Request  request=new Request.Builder().url(url).post(body).build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){

                    final String result=response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PostActivity.this,result,Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }
}
