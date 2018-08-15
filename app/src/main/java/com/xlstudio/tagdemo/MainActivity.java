package com.xlstudio.tagdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    RelativeLayout rootRl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        rootRl = findViewById(R.id.root);

        TagView tagViewGroup = new TagView(this);
        tagViewGroup.setPercent(0.3f,0.5f);

        rootRl.addView(tagViewGroup);

    }
}
