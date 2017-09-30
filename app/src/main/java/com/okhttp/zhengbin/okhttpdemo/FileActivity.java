package com.okhttp.zhengbin.okhttpdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ZHengBin on 2017/9/12.
 */

public class FileActivity extends Activity implements View.OnClickListener{

    private Button btn;
    private ProgressBar pb;
    private OkHttpClient httpClient;

   private String url="https://search.maven.org/remote_content?g=com.squareup.okhttp&a=okhttp&v=LATEST";
   private String fileName="okhttp-2.7.5.jar";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        btn= (Button) findViewById(R.id.btn_download);
        btn.setOnClickListener(this);

        pb= (ProgressBar) findViewById(R.id.pb);

        initOkHttp();
    }

    private void initOkHttp(){

        httpClient=new OkHttpClient();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_download:

                downLoadFile();

                break;

        }

    }

    private void downLoadFile(){



        Request request=new Request.Builder().url(url).build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(FileActivity.this,"出错了",Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                writeFile(response);

            }
        });
    }

    Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            if(msg.what==1){

               // int progress=0;
                pb.setProgress(msg.arg1);
            }

        }
    };

    private void writeFile(Response response){

        InputStream is=null;
        FileOutputStream fos=null;

        is=response.body().byteStream();

        String path= Environment.getExternalStorageDirectory().getAbsolutePath();

        File file=new File(path,fileName);

        try {
            fos=new FileOutputStream(file);

            byte[] bytes=new byte[1024];

            int len=0;

          long totalSize= response.body().contentLength();//文件大小

            long sum=0;

             while ((len=is.read(bytes))!=-1){


                 fos.write(bytes);
                 sum+=len;
                 int progress=(int)((sum*1.0f/totalSize)*100);

                 Message msg=mHandler.obtainMessage(1);
                 msg.arg1=progress;
                 mHandler.sendMessage(msg);


            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {

                try {
                    if(is!=null) {
                        is.close();
                    }
                    if(fos!=null){
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


        }
    }

}
