package com.example.chapter3.homework;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PlaceholderFragment extends Fragment {

    private String[] data = { "Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango" };
    private View view;
    private View animation_view;
    private AnimatorSet animatorSet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件

        view = inflater.inflate(R.layout.fragment_placeholder, container, false);
        animation_view = view.findViewById(R.id.animation_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
                ListView listView;
                listView = view.findViewById(R.id.list_item);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        getActivity(), android.R.layout.simple_list_item_1, data);
                listView.setAdapter(adapter);

                ObjectAnimator animator1 = ObjectAnimator.ofFloat(animation_view,"alpha",1,0);
                animator1.setDuration(1000);

                ObjectAnimator animator2 = ObjectAnimator.ofFloat(listView,"alpha",0,1);
                animator2.setDuration(1000);

                animatorSet = new AnimatorSet();
                animatorSet.playTogether(animator1,animator2);
                animatorSet.start();

            }
        }, 5000);
    }


}
