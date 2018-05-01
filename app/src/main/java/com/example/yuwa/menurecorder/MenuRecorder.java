package com.example.yuwa.menurecorder;

import android.app.Application;

import io.realm.Realm;

public class MenuRecorder extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
