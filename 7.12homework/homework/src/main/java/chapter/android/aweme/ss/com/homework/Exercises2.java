package chapter.android.aweme.ss.com.homework;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * 作业2：一个抖音笔试题：统计页面所有view的个数
 * Tips：ViewGroup有两个API
 * {@link android.view.ViewGroup #getChildAt(int) #getChildCount()}
 * 用一个TextView展示出来
 */
public class Exercises2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View inflate = LayoutInflater.from(this).inflate(R.layout.im_list_item,null);
        //setContentView(inflate);//展示出来
        int summ = getViewCount(inflate);

        setContentView(R.layout.item_exc2);
        TextView txtNyM = findViewById(R.id.txt_num);
        txtNyM.setText(String.valueOf(summ)+"\n“官方”那个imageview被我删了，所以少一项");

//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        layoutParams.topMargin=100;
//        TextView textView = new TextView(this);
//        textView.setLayoutParams(layoutParams);
//        textView.setBackgroundColor(0xFFFFFFFF);
//        textView.setText(String.valueOf(summ));
//        textView.setTextSize(30);
//        ((RelativeLayout)inflate).addView(textView);
//        setContentView(inflate);

    }

    public static int getViewCount(View view) {
        //todo 补全你的代码
        int sum = 0;
        if(view == null){
            return sum;
        }
        if(view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            //sum = sum + viewGroup.getChildCount();
            LinkedList<ViewGroup> queue = new LinkedList<ViewGroup>();
            queue.add(viewGroup);
            while(!queue.isEmpty()) {
                ViewGroup current = queue.removeFirst();
                sum = sum + current.getChildCount();
                for(int i = 0; i < current.getChildCount(); i ++) {
                    if(current.getChildAt(i) instanceof ViewGroup) {
                        queue.addLast((ViewGroup) current.getChildAt(i));
                    }
                }
            }
        }
        System.out.println(sum);
        return sum;
    }
}
