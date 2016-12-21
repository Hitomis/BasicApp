package com.hitomi.basicapp.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.hitomi.basic.manager.cache.CacheHandler;
import com.hitomi.basic.manager.cache.CacheManager;
import com.hitomi.basic.manager.cache.impl.SharedPref;
import com.hitomi.basic.ui.BaseActivity;
import com.hitomi.basicapp.McModel;
import com.hitomi.basicapp.R;

import java.io.InputStream;

public class CacheActivity extends BaseActivity implements View.OnClickListener {

    private Button btnSp1, btnMc1, btnDc1, btnSp2, btnMc2;
    private Button btnExit;
    private ImageView imageView;

    private SharedPref sp = CacheManager.SP();
    private CacheHandler mc = CacheManager.MC();
    private CacheHandler ds = CacheManager.DS();

    @Override
    public int getContentViewID() {
        return R.layout.activity_cache;
    }

    @Override
    public void initView() {
        btnSp1 = (Button) findViewById(R.id.btn_sp1);
        btnMc1 = (Button) findViewById(R.id.btn_mc1);
        btnDc1 = (Button) findViewById(R.id.btn_dc1);

        btnSp2 = (Button) findViewById(R.id.btn_sp2);
        btnMc2 = (Button) findViewById(R.id.btn_mc2);

        btnExit = (Button) findViewById(R.id.btn_exit);
        imageView = (ImageView) findViewById(R.id.image_view);
    }

    @Override
    public void setViewListener() {
        btnSp1.setOnClickListener(this);
        btnMc1.setOnClickListener(this);
        btnDc1.setOnClickListener(this);

        btnSp2.setOnClickListener(this);
        btnMc2.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void dealLogic(Bundle savedInstanceState) {
        sp.put("sp1", "这是测试 SharedPreferences 取出的字符串");
        sp.put("sp2", 20161219);

        McModel model = new McModel();
        model.setName("Hitomi");
        model.setAge(18);
        model.setSex(true);
        model.setWeight(60.2f);
        mc.put("mc2", model);
        mc.put("mc1", "这是测试 MemoryCache 取出的字符串");

        Drawable image = getBaseContext().getResources().getDrawable(R.drawable.img1);
        ds.put("dc1", image);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sp1:
                Toast.makeText(this, sp.getString("sp1", ""), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_sp2:
                Toast.makeText(this, sp.getInt("sp2", -1) + "", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_mc1:
                Toast.makeText(this, mc.get("mc1").toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_mc2:
                Toast.makeText(this, mc.get("mc2").toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_dc1:
                Object value = ds.get("dc1");
                if (value instanceof InputStream) {
                    InputStream resultIs = (InputStream) value;
                    imageView.setImageBitmap(inputStream2Bitmap(resultIs));
                }
                break;
            case R.id.btn_exit:
                postExitApp();
                break;
        }
    }

    public Bitmap inputStream2Bitmap(InputStream is) {
        return BitmapFactory.decodeStream(is);
    }

}
