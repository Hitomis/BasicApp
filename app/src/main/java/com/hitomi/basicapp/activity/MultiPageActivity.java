package com.hitomi.basicapp.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.hitomi.basic.tool.Kits;
import com.hitomi.basic.ui.BaseActivity;
import com.hitomi.basic.view.RoundImageView;
import com.hitomi.basicapp.R;
import com.hitomi.basicapp.multipage.MultiViewPager;
import com.hitomi.basicapp.multipage.ZoomOutPageTransformer;

/**
 * Created by Hitomis on 2018/3/20 0020.
 */

public class MultiPageActivity extends BaseActivity {
    private MultiViewPager multiViewPager;

    private int[] imgArray = {R.drawable.p1, R.drawable.p2, R.drawable.p3,
            R.drawable.p4, R.drawable.p5, R.drawable.p6,
            R.drawable.p7, R.drawable.p8, R.drawable.p9,
            R.drawable.p10, R.drawable.p11, R.drawable.p12};


    @Override
    public int getContentViewID() {
        return R.layout.activity_multi_page;
    }

    @Override
    public void initView() {
        multiViewPager = (MultiViewPager) findViewById(R.id.multi_view_page);
        ViewpagerAdapter mAdapter = new ViewpagerAdapter();
        multiViewPager.setAdapter(mAdapter);
        //设置缓存数为展示的数目
        multiViewPager.setOffscreenPageLimit(imgArray.length / 3);
        multiViewPager.setPageMargin(Kits.Dimens.dip2Px(this, 2));
        //设置切换动画
        multiViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    @Override
    public void setViewListener() {

    }

    @Override
    public void dealLogic(Bundle savedInstanceState) {

    }

    class ViewpagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            RoundImageView imageView = new RoundImageView(container.getContext());
            Glide.with(MultiPageActivity.this).load(imgArray[position]).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public int getCount() {
            return imgArray.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

    }
}
