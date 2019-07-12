package chapter.android.aweme.ss.com.homework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Nextpage extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_exc2);
        String data = getIntent().getStringExtra("data");
        TextView txtNyM = findViewById(R.id.txt_num);
        txtNyM.setText("你点击了第" + data + "项");
    }
}
