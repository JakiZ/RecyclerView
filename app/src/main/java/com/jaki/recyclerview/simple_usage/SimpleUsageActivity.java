package com.jaki.recyclerview.simple_usage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jaki.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

public class SimpleUsageActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private List<SimpleUsageBean> list;
    private SimpleUsageAdapter adapter;
    private Button add;
    private Button delete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_usage);

        initView();
        initData();
        setListener();
    }

    private void initView() {
        add = ((Button) findViewById(R.id.add));
        delete = ((Button) findViewById(R.id.delete));


        //初始化控件
        mRecyclerView = ((RecyclerView) findViewById(R.id.rv_simple_usage));
        //设置布局管理器
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL,false);//方向可以是 HORIZONTAL
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
//        SimpleUsageDecoration decoration = new SimpleUsageDecoration(this,RecyclerView.VERTICAL);
        GridDecoration decoration = new GridDecoration(this);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());



    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            SimpleUsageBean bean = new SimpleUsageBean();
            bean.setId(i);
            bean.setName("name " + i);
            bean.setScore(68 + i);
            bean.setGender(i % 3 == 0 ? true :false);
            list.add(bean);
        }
        adapter = new SimpleUsageAdapter(this, list);
        mRecyclerView.setAdapter(adapter);
    }

    private void setListener() {
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        adapter.setOnItemClick(new SimpleUsageAdapter.OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(SimpleUsageActivity.this, "点击 " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(SimpleUsageActivity.this, "长按 " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:
                SimpleUsageBean bean = new SimpleUsageBean();
                bean.setId(100);
                bean.setName("name " + 100);
                bean.setScore(68 + 10);
                bean.setGender(100 % 3 == 0 ? true :false);
                adapter.addData(bean);
                break;
            case R.id.delete:
                adapter.deleteData(0);
                break;
        }
    }
}
