package com.hitomi.basicapp.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hitomi.basic.adapter.recycleview.CommonAdapter;
import com.hitomi.basic.adapter.recycleview.ViewHolder;
import com.hitomi.basic.ui.BaseActivity;
import com.hitomi.basicapp.R;
import com.hitomi.basicapp.multirecycler.MultiRecyclerView;
import com.hitomi.basicapp.multirecycler.ScaleXViewMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hitomis on 2018/3/20 0020.
 */

public class MultiRecyclerActivity extends BaseActivity {
    private MultiRecyclerView recyclerView;

    private int[] imgArray = {R.drawable.p1, R.drawable.p2, R.drawable.p3,
            R.drawable.p4, R.drawable.p5, R.drawable.p6,
            R.drawable.p7, R.drawable.p8, R.drawable.p9,
            R.drawable.p10, R.drawable.p11, R.drawable.p12};

    private List<Integer> imgList;

    {
        imgList = new ArrayList<>();
        imgList.add(R.drawable.p1);
        imgList.add(R.drawable.p2);
        imgList.add(R.drawable.p3);
        imgList.add(R.drawable.p4);
        imgList.add(R.drawable.p5);
        imgList.add(R.drawable.p6);
        imgList.add(R.drawable.p7);
        imgList.add(R.drawable.p8);
        imgList.add(R.drawable.p9);
        imgList.add(R.drawable.p10);
        imgList.add(R.drawable.p11);
        imgList.add(R.drawable.p12);
    }


    @Override
    public int getContentViewID() {
        return R.layout.activity_multi_recycler;
    }

    @Override
    public void initView() {
        recyclerView = (MultiRecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setViewMode(new ScaleXViewMode());
        recyclerView.setNeedCenterForce(true);
    }

    @Override
    public void setViewListener() {
        recyclerView.setOnCenterItemClickListener(new MultiRecyclerView.OnCenterItemClickListener() {
            @Override
            public void onCenterItemClick(View v) {
                Toast.makeText(MultiRecyclerActivity.this, "Center Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void dealLogic(Bundle savedInstanceState) {
        recyclerView.setAdapter(new ImageAdapter());
    }

    class ImageAdapter extends CommonAdapter<Integer> {


        public ImageAdapter() {
            super(MultiRecyclerActivity.this, R.layout.item_multi_recycler, imgList);
        }

        @Override
        protected void convert(ViewHolder holder, Integer imgRes, int position) {
            ImageView ivImg = holder.getView(R.id.iv_img);
            Glide.with(MultiRecyclerActivity.this).load(imgRes).into(ivImg);
        }
    }
}
