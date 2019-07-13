package chapter.android.aweme.ss.com.homework;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * 作业1：旋转之后的生命周期是：pause->stop->destroy->create->start->resume
 * 打印出Activity屏幕切换 Activity会执行什么生命周期？
 */
public class Exercises1 extends AppCompatActivity {

    private static String TAG = Exercises1.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.item_exc2);
        TextView txtNyM = findViewById(R.id.txt_num);
        txtNyM.setText("pause->stop->destroy->create->start->resume");

        Log.d(TAG, "onCreate: savedInstanceState = [" + savedInstanceState + "]");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
