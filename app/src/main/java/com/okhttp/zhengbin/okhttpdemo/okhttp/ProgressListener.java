package com.okhttp.zhengbin.okhttpdemo.okhttp;

/**
 * Created by ZHengBin on 2017/9/13.
 */

public interface ProgressListener {

    public void onProgress(int progress);

    public void onDone(long totalSize);//下载完成

}
