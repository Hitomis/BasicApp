package com.hitomi.basicapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hitomi.basic.manager.cache.CacheHandler;
import com.hitomi.basic.manager.cache.CacheManager;
import com.hitomi.basic.manager.cache.impl.SharedPref;
import com.hitomi.basic.ui.BaseActivity;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private Button btnSp1, btnMc1, btnDc1, btnSp2, btnMc2, btnDc2;

    private SharedPref sp = CacheManager.SP();
    private CacheHandler mc = CacheManager.MC();
    private CacheHandler ds = CacheManager.DS();

    @Override
    public int getContentViewID() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        btnSp1 = (Button) findViewById(R.id.btn_sp1);
        btnMc1 = (Button) findViewById(R.id.btn_mc1);
        btnDc1 = (Button) findViewById(R.id.btn_dc);

        btnSp2 = (Button) findViewById(R.id.btn_sp2);
        btnMc2 = (Button) findViewById(R.id.btn_mc2);
    }

    @Override
    public void setViewListener() {
        btnSp1.setOnClickListener(this);
        btnMc1.setOnClickListener(this);
        btnDc1.setOnClickListener(this);

        btnSp2.setOnClickListener(this);
        btnMc2.setOnClickListener(this);
    }

    @Override
    public void dealLogic(Bundle savedInstanceState) {
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_sp1:
                sp.put("sp1", "这是测试 SharedPreferences 取出的字符串");
                Toast.makeText(this, sp.getString("sp1", ""), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_sp2:
                sp.put("sp2", 20161219);
                Toast.makeText(this, sp.getInt("sp2", -1) + "", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_mc1:
                mc.put("mc1", "这是测试 MemoryCache 取出的字符串");
                Toast.makeText(this, mc.get("mc1").toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_mc2:
                McModel model = new McModel();
                model.setName("Hitomi");
                model.setAge(18);
                model.setSex(true);
                model.setWeight(60.2f);
                mc.put("mc2", model);
                Toast.makeText(this, mc.get("mc2").toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_dc:

                break;
        }
    }
}
