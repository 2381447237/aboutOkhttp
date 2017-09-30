package com.okhttp.zhengbin.okhttpdemo.okhttp;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by ZHengBin on 2017/9/13.
 */

public class ProgressResponseBody extends ResponseBody{

    private ResponseBody mResponseBody;

    private BufferedSource mBufferedSource;

    private ProgressListener mProgressListener;

    public ProgressResponseBody(ResponseBody mResponseBody, ProgressListener mProgressListener) {
        this.mResponseBody = mResponseBody;
        this.mProgressListener = mProgressListener;
    }

    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {

        if(mBufferedSource==null){
            mBufferedSource=Okio.buffer(getsource(mResponseBody.source()));
        }

        return mBufferedSource;
    }

    private Source getsource(Source source){

        return  new ForwardingSource(source) {

            long totalSize=0L;

            long sum=0L;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {

                if(totalSize==0){

                    totalSize=contentLength();

                }

                long len= super.read(sink, byteCount);

               sum+=(len==-1?0:len);

                int progress=(int)((sum*1.0f/totalSize)*100);

                if(len==-1){
                    mProgressListener.onDone(totalSize);
                }else {
                    mProgressListener.onProgress(progress);
                }
                return len;
            }
        };

    }

}
