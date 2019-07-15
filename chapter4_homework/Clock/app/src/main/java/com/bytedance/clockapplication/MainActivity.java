package com.bytedance.clockapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bytedance.clockapplication.widget.Clock;

public class MainActivity extends AppCompatActivity {

    private View mRootView;
    private Clock mClockView;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this,1000);
            mClockView.invalidate();
        }
    };

    private static final  int MSG_START = 0;    //消息（what）
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private Handler UIHandler = new Handler();  //线程Handler，用于更新UI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRootView = findViewById(R.id.root);
        mClockView = findViewById(R.id.clock);

        //createBackThread();

        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClockView.setShowAnalog(!mClockView.isShowAnalog());
            }
        });
        handler.postDelayed(runnable,1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(runnable);
    }

//    private void createBackThread() {
//        //创建HandlerThread，名字为"gettime"
//        mHandlerThread = new HandlerThread("gettime");
//        mHandlerThread.start();
//        //开启HandlerThread
//        //在该Handler中创建一个Handler对象
//        mHandler = new Handler(mHandlerThread.getLooper()){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                //在这里可以进行耗时操作，是在线程中运行的//
//                UIHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        //mClockView.setShowAnalog(!mClockView.isShowAnalog());
//                        mClockView.invalidate();
//                    }
//                });
//                //向mHandler发送延时消息
//                mHandler.sendEmptyMessageDelayed(MSG_START,1000);
//            }
//
//        };
//        mHandler.sendEmptyMessageDelayed(MSG_START,1000);
//    }
}