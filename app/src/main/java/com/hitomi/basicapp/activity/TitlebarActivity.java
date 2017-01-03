package com.hitomi.basicapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hitomi.basic.tool.Kits;
import com.hitomi.basic.ui.BaseActivity;
import com.hitomi.basic.view.titlebar.TitleBarLayout;
import com.hitomi.basicapp.R;

public class TitlebarActivity extends BaseActivity {

    private TitleBarLayout titleBar1, titleBar2, titleBar3, titleBar4, titleBar5, titleBar6, titleBar7, titleBar8;

    @Override
    public int getContentViewID() {
        return R.layout.activity_titlebar;
    }

    @Override
    public void initView() {
        titleBar1 = (TitleBarLayout) findViewById(R.id.title1);
        titleBar2 = (TitleBarLayout) findViewById(R.id.title2);
        titleBar3 = (TitleBarLayout) findViewById(R.id.title3);
        titleBar4 = (TitleBarLayout) findViewById(R.id.title4);
        titleBar5 = (TitleBarLayout) findViewById(R.id.title5);
        titleBar6 = (TitleBarLayout) findViewById(R.id.title6);
        titleBar7 = (TitleBarLayout) findViewById(R.id.title7);
        titleBar8 = (TitleBarLayout) findViewById(R.id.title8);
    }

    @Override
    public void setViewListener() {

    }

    @Override
    public void dealLogic(Bundle savedInstanceState) {
        titlebarDemo1();
        titlebarDemo2();
        titlebarDemo3();
        titlebarDemo4();
        titlebarDemo5();
        titlebarDemo6();
        titlebarDemo7();
        titlebarDemo8();
    }

    private void titlebarDemo8() {
        TextView leftText = new TextView(this);
        leftText.setText("首页");
        leftText.setTextSize(16);
        leftText.setTextColor(Color.WHITE);
        leftText.setCompoundDrawablePadding(Kits.Dimens.dip2Px(this, 5));
        leftText.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.left), null, null, null);

        TextView rightText = new TextView(this);
        rightText.setText("喜欢");
        rightText.setTextSize(16);
        rightText.setTextColor(Color.WHITE);
        rightText.setCompoundDrawablePadding(Kits.Dimens.dip2Px(this, 5));
        rightText.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.right), null);

        new TitleBarLayout.Builder(this)
                .setBarColor(Color.parseColor("#2f008d"))
                .setTitle("左右带图标", Color.WHITE, 18)
                .setLeftTextView(leftText)
                .setLeftMargin(Kits.Dimens.dip2Px(this, 16))
                .setRightTextView(rightText)
                .setRightMargin(Kits.Dimens.dip2Px(this, 16))
                .setup(titleBar8);
    }

    private void titlebarDemo7() {
        TextView tvTitle = new TextView(this);
        tvTitle.setTextSize(18);
        tvTitle.setTextColor(Color.WHITE);
        tvTitle.setText("我的");
        tvTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.right), null);
        tvTitle.setCompoundDrawablePadding(Kits.Dimens.dip2Px(this, 5));
        new TitleBarLayout.Builder(this)
                .setBarColor(Color.parseColor("#2f008d"))
                .setTitleView(tvTitle)
                .setTitleOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TitlebarActivity.this, "switch title display", Toast.LENGTH_SHORT).show();
                    }
                })
                .setup(titleBar7);
    }

    private void titlebarDemo6() {
        new TitleBarLayout.Builder(this)
                .setBarColor(Color.parseColor("#2f008d"))
                .setTitle("文字标题栏", Color.WHITE, 18)
                .setLeftText("返回", Color.WHITE, 16)
                .setRightText("确定", Color.WHITE, 16)
                .setLeftMargin(Kits.Dimens.dip2Px(this, 8))
                .setRightMargin(Kits.Dimens.dip2Px(this, 8))
                .setup(titleBar6);
    }

    private void titlebarDemo5() {
        new TitleBarLayout.Builder(this)
                .setBarColor(Color.parseColor("#2f008d"))
                .setTitle("附带右二操作", Color.WHITE, 18)
                .setLeftIcon(R.mipmap.left)
                .setLeftMargin(Kits.Dimens.dip2Px(this, 8))
                .setLeftRegion(Kits.Dimens.dip2Px(this, 8))
                .setRightIcon(R.mipmap.right)
                .setRightMargin(Kits.Dimens.dip2Px(this, 8))
                .setRightRegion(Kits.Dimens.dip2Px(this, 8))
                .setRightSubIcon(R.mipmap.right)
                .setRightSubMargin(Kits.Dimens.dip2Px(this, 8))
                .setRightSubRegion(Kits.Dimens.dip2Px(this, 8))
                .setup(titleBar5);
    }

    private void titlebarDemo4() {
        new TitleBarLayout.Builder(this)
                .setBarColor(Color.parseColor("#2f008d"))
                .setTitle("右侧按钮可变大", Color.WHITE, 18)
                .setLeftIcon(R.mipmap.left)
                .setLeftMargin(Kits.Dimens.dip2Px(this, 8))
                .setLeftRegion(Kits.Dimens.dip2Px(this, 8))
                .setRightIcon(R.mipmap.right)
                .setRightMargin(Kits.Dimens.dip2Px(this, 8))
                .setRightRegion(Kits.Dimens.dip2Px(this, 8))
                .setRightImageSize(Kits.Dimens.dip2Px(this, 32))
                .setup(titleBar4);
    }

    private void titlebarDemo3() {
        new TitleBarLayout.Builder(this)
                .setBarColor(Color.parseColor("#2f008d"))
                .setTitle("左右按钮和标题可点击", Color.WHITE, 18)
                .setLeftIcon(R.mipmap.left)
                .setLeftMargin(Kits.Dimens.dip2Px(this, 8))
                .setLeftRegion(Kits.Dimens.dip2Px(this, 8))
                .setRightIcon(R.mipmap.right)
                .setRightMargin(Kits.Dimens.dip2Px(this, 8))
                .setRightRegion(Kits.Dimens.dip2Px(this, 8))
                .setLeftOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TitlebarActivity.this, "click left", Toast.LENGTH_SHORT).show();
                    }
                })
                .setRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TitlebarActivity.this, "click right", Toast.LENGTH_SHORT).show();
                    }
                })
                .setTitleOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TitlebarActivity.this, "click title", Toast.LENGTH_SHORT).show();
                    }
                })
                .setup(titleBar3);
    }

    private void titlebarDemo2() {
        new TitleBarLayout.Builder(this)
                .setBarColor(Color.parseColor("#2f008d"))
                .setTitle("左右两侧按钮标题栏", Color.WHITE, 18)
                .setLeftIcon(R.mipmap.left)
                .setLeftMargin(Kits.Dimens.dip2Px(this, 8))
                .setLeftRegion(Kits.Dimens.dip2Px(this, 8))
                .setRightIcon(R.mipmap.right)
                .setRightMargin(Kits.Dimens.dip2Px(this, 8))
                .setRightRegion(Kits.Dimens.dip2Px(this, 8))
                .setup(titleBar2);
    }

    private void titlebarDemo1() {
        new TitleBarLayout.Builder(this)
                .setBarColor(Color.parseColor("#2f008d"))
                .setTitle("普通标题栏", Color.WHITE, 18)
                .setLeftIcon(R.mipmap.left)
                .setLeftMargin(Kits.Dimens.dip2Px(this, 8))
                .setLeftRegion(Kits.Dimens.dip2Px(this, 8))
                .setLeftOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setup(titleBar1);
    }

}
