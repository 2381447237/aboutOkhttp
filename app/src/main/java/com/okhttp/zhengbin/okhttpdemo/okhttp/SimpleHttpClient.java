package com.okhttp.zhengbin.okhttpdemo.okhttp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by ZHengBin on 2017/9/21.
 */

public class SimpleHttpClient {

    private Builder mBuilder;

    private SimpleHttpClient(Builder builder) {
        this.mBuilder=builder;
    }

    public Request buildRequest(){


          Request.Builder builder=new Request.Builder();

        if(mBuilder.method=="GET"){

            builder.url(buildGetRequestParam());
            builder.get();

        }else if(mBuilder.method=="POST"){

            try {
                builder.post(buildRequestBody());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            builder.url(mBuilder.url);
        }



        return builder.build();
    }

    private String buildGetRequestParam(){

        if(mBuilder.mParam.size()<=0){
            return this.mBuilder.url;
        }

        Uri.Builder builder=Uri.parse(this.mBuilder.url).buildUpon();

        for(RequestParam p:mBuilder.mParam){

            builder.appendQueryParameter(p.getKey(),p.getObj()==null
            ?"":p.getObj().toString());

        }

        String url=builder.build().toString();

        Log.e("2017/9/30","url=="+url);

        return url;

    }

    private RequestBody buildRequestBody() throws JSONException{

        if(mBuilder.isJsonParam){

            JSONObject jsonObj=new JSONObject();

            for(RequestParam p:mBuilder.mParam){

                jsonObj.put(p.getKey(),p.getObj());

            }

            String json=jsonObj.toString();

            return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        }

        FormBody.Builder builder=new FormBody.Builder();

        for(RequestParam p:mBuilder.mParam){

            builder.add(p.getKey(),p.getObj()==null?"":p.getObj().toString());

        }

        return builder.build();
    }

    public void enqueue(BaseCallback callback){

        OKHttpManager.getmInstance().request(this,callback);

    }

    public static Builder newBuilder(){

        return new Builder();
    }

    public static class Builder{

        private String url;
        private String method;
        private boolean isJsonParam=false;

        private List<RequestParam> mParam;

        private Builder(){
            method="GET";
        }

        public SimpleHttpClient build(){

            return new SimpleHttpClient(this);
        }

        public Builder url(String url){

            this.url=url;

            return this;

        }

        public Builder get(){

            method="GET";

            return this;

        }

        //form表单形式
        public Builder post(){

            method="POST";

            return this;

        }

        //json形式
        public Builder json(){

            isJsonParam=true;

            return post();

        }

        public Builder addParam(String key,Object value){

            if(mParam==null){
                mParam=new ArrayList<RequestParam>();
            }

           mParam.add(new RequestParam(key,value));

            return this;
        }

    }

}
