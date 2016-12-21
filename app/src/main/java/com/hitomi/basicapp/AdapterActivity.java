package com.hitomi.basicapp;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hitomi.basic.adapter.recycleview.CommonAdapter;
import com.hitomi.basic.adapter.recycleview.DividerItemDecoration;
import com.hitomi.basic.adapter.recycleview.HeaderAndFooterWrapper;
import com.hitomi.basic.adapter.recycleview.LoadMoreWrapper;
import com.hitomi.basic.adapter.recycleview.ViewHolder;
import com.hitomi.basic.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class AdapterActivity extends BaseActivity {

    private RecyclerView recyclerView;

    @Override
    public int getContentViewID() {
        return R.layout.activity_adapter;
    }

    @Override
    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void setViewListener() {

    }

    @Override
    public void dealLogic(Bundle savedInstanceState) {
        RecyclerView.Adapter adapter = new CommonAdapter<String>(this, android.R.layout.simple_list_item_1, makeTestData()){

            @Override
            protected void convert(ViewHolder holder, final String s, int position) {
                holder.setText(android.R.id.text1, s);
                // 添加 item 的点击事件
                holder.setOnClickListener(android.R.id.text1, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(AdapterActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);

        HeaderAndFooterWrapper decorWrapperAdapter = new HeaderAndFooterWrapper(adapter);
        // 添加多个 header
        TextView t1 = new TextView(this);
        t1.setText("Header 1");
        TextView t2 = new TextView(this);
        t2.setText("Header 2");
        decorWrapperAdapter.addHeaderView(t1);
        decorWrapperAdapter.addHeaderView(t2);

        // 添加多个 footer
        TextView t3 = new TextView(this);
        t3.setText("footer 1");
        TextView t4 = new TextView(this);
        t4.setText("footer 2");
        decorWrapperAdapter.addFootView(t3);
        decorWrapperAdapter.addFootView(t4);

        // 添加加载更多【当滑动到最后一个item 的时候触发 onLoadMoreRequested 回调方法】
        LoadMoreWrapper<String> loadMore = new LoadMoreWrapper<>(decorWrapperAdapter);
        loadMore.setLoadMoreView(R.layout.item_recycle);
        loadMore.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Toast.makeText(AdapterActivity.this, "loadmore...", Toast.LENGTH_SHORT).show();
            }
        });


        recyclerView.setAdapter(loadMore);
    }

    private List<String> makeTestData() {
        List<String> strList = new ArrayList<>();
        strList.add("测试item1");
        strList.add("测试item2");
        strList.add("测试item3");
        strList.add("测试item4");
        strList.add("测试item5");
        strList.add("测试item6");
        strList.add("测试item7");
        strList.add("测试item8");
        strList.add("测试item8");
        strList.add("测试item8");
        strList.add("测试item8");
        strList.add("测试item8");
        strList.add("测试item8");
        strList.add("测试item8");
        strList.add("测试item8");

        return strList;
    }
}
