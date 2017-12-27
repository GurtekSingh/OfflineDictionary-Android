package com.gurtek.offlinedictonary;

import android.app.Application;

import gurtek.com.offlinedictionary.Dictionary;

/**
 * * Created by Gurtek Singh on 12/27/2017.
 * Sachtech Solution
 * gurtek@protonmail.ch
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Dictionary.init(this);

    }
}
