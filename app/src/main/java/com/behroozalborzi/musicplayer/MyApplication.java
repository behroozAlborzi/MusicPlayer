package com.behroozalborzi.musicplayer;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Behrooz on 7/9/2021.
 * https://behroozalborzi.ir
 * Android Developer
 * Thank you ... :)
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
