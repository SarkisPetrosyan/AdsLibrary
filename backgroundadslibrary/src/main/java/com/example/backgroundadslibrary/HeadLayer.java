package com.example.backgroundadslibrary;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import static android.content.Context.WINDOW_SERVICE;

@SuppressLint("ViewConstructor")
public class HeadLayer extends View {

    private Application mApplication;
    private FrameLayout mFrameLayoutBottom, mFrameLayoutTop;
    private WindowManager.LayoutParams params;
    private WindowManager mWindowManager;
    private String mTopUrl, mBottomUrl,mFullScreenUrl;
    private CountDownTimer mCountDownTimerTop, mCountDownTimerBottom;

    public HeadLayer(Application activity, int state, String topUrl, String bottomUrl, String fullScreenUrl) {
        super(activity);
        mApplication = activity;
        mTopUrl = topUrl;
        mBottomUrl = bottomUrl;
        mFullScreenUrl = fullScreenUrl;

        mFrameLayoutBottom = new FrameLayout(mApplication);
        mFrameLayoutTop = new FrameLayout(mApplication);

        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        mWindowManager = (WindowManager) mApplication.getSystemService(WINDOW_SERVICE);

        if (state == StateEnum.TOP_BOTTOM.getInt()) {
            addToWindowManagerBottom();
            addToWindowManagerTop();
        } else if (state == StateEnum.TOP.getInt()) {
            addToWindowManagerTop();
        } else if (state == StateEnum.BOTTOM.getInt()) {
            addToWindowManagerBottom();
        } else if (state == StateEnum.FULL_SCREEN.getInt()) {
            addToWindowManagerFullScreen();
        }
    }

    private void addToWindowManagerTop() {
        params.gravity = Gravity.TOP;
        mWindowManager.addView(mFrameLayoutTop, params);
        LayoutInflater layoutInflater = (LayoutInflater) mApplication.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert layoutInflater != null;
        layoutInflater.inflate(R.layout.head_top, mFrameLayoutTop);

        ImageView imageView = mFrameLayoutTop.findViewById(R.id.top_view_image_close);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mWindowManager.removeView(mFrameLayoutTop);
                mCountDownTimerTop = new CountDownTimer(5000, 5000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                       /* intent.putExtra(Constants.STATE_STRING, StateEnum.TOP.getInt());
                        mContext.startService(intent);
                        mCountDownTimerTop.cancel();*/
                    }
                }.start();
            }
        });

        FrameLayout frameLayout = mFrameLayoutTop.findViewById(R.id.top_view_layout);
        frameLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mTopUrl;
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                webIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mApplication.startActivity(webIntent);
            }
        });

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        // lp.setMargins(100, 200, 100, 220);
        frameLayout.setLayoutParams(lp);
    }

    private void addToWindowManagerBottom() {
        params.gravity = Gravity.BOTTOM;
        mWindowManager.addView(mFrameLayoutBottom, params);
        LayoutInflater layoutInflater = (LayoutInflater) mApplication.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert layoutInflater != null;
        layoutInflater.inflate(R.layout.head_bottom, mFrameLayoutBottom);

        ImageView imageView = mFrameLayoutBottom.findViewById(R.id.bottom_view_image_close);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mWindowManager.removeView(mFrameLayoutBottom);
                mCountDownTimerBottom = new CountDownTimer(5000, 5000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                       /* intent.putExtra(Constants.STATE_STRING, StateEnum.BOTTOM.getInt());
                        mContext.startService(intent);
                        mCountDownTimerBottom.cancel();*/
                    }
                }.start();
            }
        });

        FrameLayout frameLayout = mFrameLayoutBottom.findViewById(R.id.bottom_view_layout);
        frameLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mBottomUrl;
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                webIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mApplication.startActivity(webIntent);
            }
        });
    }

    private void addToWindowManagerFullScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        params.gravity = Gravity.CENTER;
        params.height = height;
        params.width = width;
        mWindowManager.addView(mFrameLayoutBottom, params);
        LayoutInflater layoutInflater = (LayoutInflater) mApplication.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert layoutInflater != null;
        layoutInflater.inflate(R.layout.head_full_screen, mFrameLayoutBottom);

        ImageView imageView = mFrameLayoutBottom.findViewById(R.id.full_screen_view_image_close);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mWindowManager.removeView(mFrameLayoutBottom);
                mCountDownTimerBottom = new CountDownTimer(5000, 5000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                       /* intent.putExtra(Constants.STATE_STRING, StateEnum.BOTTOM.getInt());
                        mContext.startService(intent);
                        mCountDownTimerBottom.cancel();*/
                    }
                }.start();
            }
        });

        FrameLayout frameLayout = mFrameLayoutBottom.findViewById(R.id.full_screen_view_layout);
        frameLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mFullScreenUrl;
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                webIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mApplication.startActivity(webIntent);
            }
        });
    }
}