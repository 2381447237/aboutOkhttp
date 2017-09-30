package com.okhttp.zhengbin.okhttpdemo.okhttp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.okhttp.zhengbin.okhttpdemo.model.User;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by ZHengBin on 2017/9/21.
 */

public class SimleHttpTest extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get请求



        //"http://www.imooc.com/api/teacher?type=4&num=30"
      String url = "http://www.imooc.com/api/teacher";

       SimpleHttpClient.newBuilder().addParam("type","4")
               .addParam("num","30").json().url(url)
               .build().enqueue(new BaseCallback() {
           @Override
           public void onSuccess(Object o) {

               Toast.makeText(SimleHttpTest.this,"2017/9/30"+o,Toast.LENGTH_SHORT).show();

           }
       });

    }
}
