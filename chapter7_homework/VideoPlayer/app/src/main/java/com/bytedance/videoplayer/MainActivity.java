package com.bytedance.videoplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ImageButton buttonP;
    private ImageButton buttonB;
    private SeekBar seekBar;
    private TextView tvStart;
    private TextView tvEnd;
    private SurfaceView surfaceView;
    private MediaPlayer player;
    private SurfaceHolder holder;
    private DisplayMetrics displayMetrics;
    private boolean flag = true;
    private boolean p = false;//是否开始暂停
    private boolean b = false;//是否全屏


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surfaceView = findViewById(R.id.surfaceView);
        tvStart = findViewById(R.id.tv_start);
        tvEnd = findViewById(R.id.tv_end);

        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        player = new MediaPlayer();
        try{
            player.setDataSource(getResources().openRawResourceFd(R.raw.bytedance));
            holder = surfaceView.getHolder();
            holder.addCallback(new PlayerCallBack());
            player.prepare();

            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    //player.start();
                    //player.setLooping(true);
                    int duration2 = player.getDuration() / 1000;
                    int position = player.getCurrentPosition() /1000;
                    tvStart.setText(calculateTime(position));
                    tvEnd.setText(calculateTime(duration2));

                    new Thread(){
                        public void run(){
                            flag = true;
                            while (flag){
                                int progress = player.getCurrentPosition();
                                seekBar.setProgress(progress);
                                tvStart.setText(calculateTime(progress / 1000));
                                try {
                                    Thread.sleep(100);
                                }catch (InterruptedException e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }.start();
                }
            });

            player.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                @Override
                public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                    changeVideoSize();
                }
            });
            player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    System.out.println(percent);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


        buttonP = findViewById(R.id.buttonP);
        buttonP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(p == false){
                    p = true;
                    buttonP.setImageResource(R.drawable.pause);
                    player.start();
                }
                else{
                    p = false;
                    buttonP.setImageResource(R.drawable.play);
                    player.pause();
                }
            }
        });

        buttonB = findViewById(R.id.buttonB);
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b == false){
                    b = true;
                    buttonB.setImageResource(R.drawable.suoxiao );
                    //getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    changeVideoSize();
                        //player.start();
                }
                else{
                    b = false;
                    buttonB.setImageResource(R.drawable.quanping);
                    //player.pause();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    changeVideoSize();
                }
            }
        });

        seekBar = findViewById(R.id.seekbar);
        seekBar.setMax(player.getDuration());
        seekBar.setProgress(0);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               if(fromUser){
                   System.out.println(progress);
                   player.seekTo(progress);
                   int duration2 = player.getDuration()/1000;
                   int position = player.getCurrentPosition();
                   tvStart.setText(calculateTime(position/1000));
                   tvEnd.setText(calculateTime(duration2));

               }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.seekTo(seekBar.getProgress());
                tvStart.setText(calculateTime(player.getCurrentPosition()/1000));
            }
        });
    }


    public void changeVideoSize() {

        int videoWidth = player.getVideoWidth();
        int videoHeight = player.getVideoHeight();

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int surfaceWidth = displayMetrics.widthPixels;
        int surfaceHeight = displayMetrics.heightPixels;

        //根据视频尺寸去计算->视频可以在sufaceView中放大的最大倍数。
        float max;
        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            //竖屏模式下按视频宽度计算放大倍数值
            max = Math.max((float) videoWidth / (float) surfaceWidth, (float) videoHeight / (float) surfaceHeight);
            videoWidth = (int) Math.ceil((float) videoWidth / max);
            videoHeight = (int) Math.ceil((float) videoHeight / max);
            surfaceView.setLayoutParams(new LinearLayout.LayoutParams(videoWidth, videoHeight));

        } else {
            //横屏模式下按视频高度计算放大倍数值
            //videoHeight = videoHeight - 175;
            max = Math.max((float) videoWidth / (float) surfaceWidth, (float) videoHeight / (float) surfaceHeight);
            //max = Math.max(((float) videoWidth / (float) surfaceHeight), (float) videoHeight / (float) surfaceWidth);
            surfaceView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        }

        //视频宽高分别/最大倍数值 计算出放大后的视频尺寸
//        videoWidth = (int) Math.ceil((float) videoWidth / max);
//        videoHeight = (int) Math.ceil((float) videoHeight / max);

        //无法直接设置视频尺寸，将计算出的视频尺寸设置到surfaceView 让视频自动填充。
        //surfaceView.setLayoutParams(new LinearLayout.LayoutParams(videoWidth, videoHeight));
       // surfaceView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public String calculateTime(int time){
        int minute;
        int second;
        if(time > 60){
            minute = time / 60;
            second = time % 60;
            //分钟再0~9
            if(minute >= 0 && minute < 10){
                //判断秒
                if(second >= 0 && second < 10){
                    return "0"+minute+":"+"0"+second;
                }else {
                    return "0"+minute+":"+second;
                }
            }else {
                //分钟大于10再判断秒
                if(second >= 0 && second < 10){
                    return minute+":"+"0"+second;
                }else {
                    return minute+":"+second;
                }
            }
        }else if(time < 60){
            second = time;
            if(second >= 0 && second < 10){
                return "00:"+"0"+second;
            }else {
                return "00:"+ second;
            }
        }
        return null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changeVideoSize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag = false;
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private class PlayerCallBack implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            player.setDisplay(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }


}
