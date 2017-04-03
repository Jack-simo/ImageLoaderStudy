package com.example.imageloaderdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by zhouhongmin on 2017/4/2.
 */

public class ImageLoader {

    //从网络下载一张图片，显示到ImageView上

    //1、提供一个构造方法
    //2、

    Handler mHandler=new Handler();
    ExecutorService mExecutorService=Executors.newFixedThreadPool(1);
    LruCache<String,Bitmap> mLruCache;
    public ImageLoader(){

       init();
    }

    private void init() {
        int maxMemory = (int)(Runtime.getRuntime().maxMemory()/(1024*1024));
        Log.i("ImageLoader",""+maxMemory);
        int cacheSize=maxMemory/4;
        mLruCache=new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };
    }


    public void displayImage(final String imageUrl, final ImageView imageView){

        if(TextUtils.isEmpty(imageUrl)){
            return;
        }

        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                final Bitmap mBitMap=downloadImage(imageUrl);
                if(mBitMap!=null){
                   mHandler.post(new Runnable() {
                       @Override
                     public void run() {
                           Log.i("ImageLoader",""+mBitMap.getWidth());
                           Log.i("ImageLoader",""+mBitMap.getHeight());
                           Log.i("ImageLoader",""+mBitMap.getRowBytes()*mBitMap.getHeight()/1024+"KB");
                           Log.i("ImageLoader",""+mBitMap.getByteCount()/1024+"KB");
                           imageView.setImageBitmap(mBitMap);
                       }
                   });
                    mLruCache.put(imageUrl,mBitMap);
                }
            }
        });

    }

    public Bitmap downloadImage(String imageUrl){
        Bitmap bitmap=null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            bitmap=BitmapFactory.decodeStream(connection.getInputStream());
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return bitmap;
    }

    private Bitmap scaleBitmap(Bitmap bitmap){
        Bitmap mBitmap=null;


        return mBitmap;
    }
}
