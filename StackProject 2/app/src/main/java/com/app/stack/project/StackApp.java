package com.app.stack.project;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class StackApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
