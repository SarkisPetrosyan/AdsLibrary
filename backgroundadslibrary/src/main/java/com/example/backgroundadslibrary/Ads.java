package com.example.backgroundadslibrary;

import android.app.Application;

public class Ads {
    public static void showHeadLayer(Application application, int state, String topUrl, String bottomUrl, String fullScreenUrl) {
        new HeadLayer(application, state, topUrl, bottomUrl, fullScreenUrl);
    }
}
