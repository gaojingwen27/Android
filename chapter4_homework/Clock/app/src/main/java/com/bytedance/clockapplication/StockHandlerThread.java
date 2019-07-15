package com.bytedance.clockapplication;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.bytedance.clockapplication.widget.Clock;

public class StockHandlerThread extends HandlerThread implements Handler.Callback {
    public static final int MSG_QUERY_STOCK = 1;

    private Handler mWorkerHandler;
    private Clock mClockView;

    public StockHandlerThread(String name) {
        super(name);
    }



    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case MSG_QUERY_STOCK:
                mClockView.setShowAnalog(!mClockView.isShowAnalog());
                mWorkerHandler.sendEmptyMessageDelayed(MSG_QUERY_STOCK,1000);
                break;
        }
        return true;
    }
}
