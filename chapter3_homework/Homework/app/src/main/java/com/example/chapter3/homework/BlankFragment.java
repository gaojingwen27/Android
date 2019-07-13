package com.example.chapter3.homework;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {


    public BlankFragment() {
        // Required empty public constructor
    }

    private List<ListClass> mData = null;
    private BlankFragment mContext;
    private ListClassAdapter mAdapter = null;
    private ListView list_people;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        list_people = view.findViewById(R.id.list_people);
        // Inflate the layout for this fragment
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
        mAdapter = new ListClassAdapter((LinkedList<ListClass>)mData, getActivity());
        list_people.setAdapter(mAdapter);

        list_people.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "你点击了第" + position + "项", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
