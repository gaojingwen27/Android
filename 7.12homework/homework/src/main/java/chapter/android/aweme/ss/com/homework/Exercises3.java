package chapter.android.aweme.ss.com.homework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

/**
 * 大作业:实现一个抖音消息页面,所需资源已放在res下面
 */
public class Exercises3 extends AppCompatActivity {

    private List<ListClass> mData = null;
    private Context mContext;
    private ListClassAdapter mAdapter = null;
    private ListView list_people;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        mContext = Exercises3.this;
        list_people = (ListView)findViewById(R.id.list_people);
        mData = new LinkedList<ListClass>();
        mData.add(new ListClass("游戏小助手", "抖出好游戏","刚刚", R.drawable.session_stranger));
        mData.add(new ListClass("抖音小助手", "收下我的双下巴祝福", "昨天",R.drawable.session_robot));
        mData.add(new ListClass("系统消息", "账号登陆提醒", "12:28",R.drawable.session_system_notice));
        mData.add(new ListClass("豆豆", "转发[视频]","12.21", R.drawable.icon_girl));
        mData.add(new ListClass("抖音小助手", "收下我的双下巴祝福", "昨天",R.drawable.session_robot));
        mData.add(new ListClass("系统消息", "账号登陆提醒", "12:28",R.drawable.session_system_notice));
        mData.add(new ListClass("豆豆", "转发[视频]","12.21", R.drawable.icon_girl));
        mData.add(new ListClass("抖音小助手", "收下我的双下巴祝福", "昨天",R.drawable.session_robot));
        mData.add(new ListClass("系统消息", "账号登陆提醒", "12:28",R.drawable.session_system_notice));
        mData.add(new ListClass("豆豆", "转发[视频]","12.21", R.drawable.icon_girl));
        mAdapter = new ListClassAdapter((LinkedList<ListClass>) mData, mContext);
        list_people.setAdapter(mAdapter);

        list_people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(Exercises3.this, Nextpage.class);
                intent.putExtra("data", ""+position);
                startActivity(intent);
                System.out.println(position);
                //setContentView(R.layout.item_exc2);
                //TextView txtNyM = findViewById(R.id.txt_num);
                //txtNyM.setText(String.valueOf(position));
                Toast.makeText(mContext,"你点击了第" + position + "项",Toast.LENGTH_SHORT).show();
            }
        });

    }


}
