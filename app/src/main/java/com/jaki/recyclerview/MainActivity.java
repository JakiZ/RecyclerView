package com.jaki.recyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jaki.recyclerview.simple_usage.SimpleUsageActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView simpleUsage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        setListener();


    }

    private void initView() {
        simpleUsage = ((TextView) findViewById(R.id.simple_usage));
    }

    private void initData() {

    }

    private void setListener() {
        simpleUsage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.simple_usage:
                startActivity(new Intent(this, SimpleUsageActivity.class));
                break;
        }
    }
}
