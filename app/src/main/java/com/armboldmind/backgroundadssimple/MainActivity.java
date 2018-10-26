package com.armboldmind.backgroundadssimple;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.backgroundadslibrary.Ads;
import com.example.backgroundadslibrary.StateEnum;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isRequiredPermissionGranted(getApplication())) {
            Intent intent = createRequiredPermissionIntent(getApplication());
            startActivityForResult(intent, 10);
        }else {
            Ads.showHeadLayer(getApplication(), StateEnum.FULL_SCREEN.getInt(),"https://armlon.co.uk/",
                    "https://armlon.co.uk/","https://armlon.co.uk/");
        }
    }

    public static boolean isRequiredPermissionGranted(Application activity) {
        return !isMarshmallowOrHigher() || Settings.canDrawOverlays(activity);
    }

    public static Intent createRequiredPermissionIntent(Application activity) {
        if (isMarshmallowOrHigher()) {
            return new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + activity.getPackageName()));
        }
        return null;
    }

    private static boolean isMarshmallowOrHigher() {
        return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (!isRequiredPermissionGranted(getApplication())) {
                Toast.makeText(this, "Required permission is not granted. Please restart the app and grant required permission.", Toast.LENGTH_LONG).show();
            } else {
                Ads.showHeadLayer(getApplication(), StateEnum.FULL_SCREEN.getInt(),"https://armlon.co.uk/",
                        "https://armlon.co.uk/","https://armlon.co.uk/");
            }
        }
    }
}
