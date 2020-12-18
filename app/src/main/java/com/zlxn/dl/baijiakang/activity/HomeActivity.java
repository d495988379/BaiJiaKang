package com.zlxn.dl.baijiakang.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.fragment.HomeFragment;
import com.zlxn.dl.baijiakang.fragment.HyqyFragment;
import com.zlxn.dl.baijiakang.fragment.JyFragment;
import com.zlxn.dl.baijiakang.fragment.MineFragment;
import com.zlxn.dl.baijiakang.fragment.XXFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/13 17:41
 */
public class HomeActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,HomeFragment.FragmentListener {

    @BindView(R.id.act_home_rg)
    RadioGroup actHomeRg;
    @BindView(R.id.act_home_rb_sc)
    RadioButton actHomeRbSc;
    @BindView(R.id.act_home_rb_jy)
    RadioButton actHomeRbJy;
    @BindView(R.id.act_home_rb_hyqy)
    RadioButton actHomeRbHyqy;
    @BindView(R.id.act_home_rb_xx)
    RadioButton actHomeRbXx;
    @BindView(R.id.act_home_rb_wd)
    RadioButton actHomeRbWd;
    private Unbinder mUnbinder;
    public static final String fragment1Tag = "fragment1";
    public static final String fragment2Tag = "fragment2";
    public static final String fragment3Tag = "fragment3";
    public static final String fragment4Tag = "fragment4";
    public static final String fragment5Tag = "fragment5";
    private Fragment fragment;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    private Fragment fragment4;
    private Fragment fragment5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        mUnbinder = ButterKnife.bind(this);
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragment = new HomeFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.act_home_fl, fragment, fragment1Tag).commit();
        }

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //设置状态栏颜色为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }




        RadioButton[] rbs = new RadioButton[5];
        rbs[0] = actHomeRbSc;
        rbs[1] = actHomeRbJy;
        rbs[2] = actHomeRbHyqy;
        rbs[3] = actHomeRbXx;
        rbs[4] = actHomeRbWd;

        for (RadioButton rb : rbs) {
            Drawable[] drawables = rb.getCompoundDrawables();
            //获取drawables
            Rect r = new Rect(0, 0, drawables[1].getMinimumWidth() + 15, (int) drawables[1].getMinimumHeight() + 15);
            //定义一个Rect边界
            drawables[1].setBounds(r);

            rb.setCompoundDrawables(null, drawables[1], null, null);
        }


        initListener();


    }


    private void initListener() {
        actHomeRg.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        fragment1 = fm.findFragmentByTag(fragment1Tag);
        fragment2 = fm.findFragmentByTag(fragment2Tag);
        fragment3 = fm.findFragmentByTag(fragment3Tag);
        fragment4 = fm.findFragmentByTag(fragment4Tag);
        fragment5 = fm.findFragmentByTag(fragment5Tag);

        if (fragment1 != null) {
            ft.hide(fragment1);
        }
        if (fragment2 != null) {
            ft.hide(fragment2);
        }
        if (fragment3 != null) {
            ft.hide(fragment3);
        }
        if (fragment4 != null) {
            ft.hide(fragment4);
        }
        if (fragment5 != null) {
            ft.hide(fragment5);
        }

        switch (checkedId) {
            case R.id.act_home_rb_sc:
                if (fragment1 == null) {
                    fragment1 = new HomeFragment();
                    ft.add(R.id.act_home_fl, fragment1, fragment1Tag);
                } else {
                    ft.show(fragment1);
                }
                break;

            case R.id.act_home_rb_jy:
                if (fragment2 == null) {
                    fragment2 = new JyFragment();
                    ft.add(R.id.act_home_fl, fragment2, fragment2Tag);
                } else {
                    ft.show(fragment2);
                }
                break;
            case R.id.act_home_rb_hyqy:
                if (fragment3 == null) {
                    fragment3 = new HyqyFragment();
                    ft.add(R.id.act_home_fl, fragment3, fragment3Tag);
                } else {
                    ft.show(fragment3);
                }

                break;
            case R.id.act_home_rb_xx:
                if (fragment4 == null) {
                    fragment4 = new XXFragment();
                    ft.add(R.id.act_home_fl, fragment4, fragment4Tag);
                } else {
                    ft.show(fragment4);
                }
                break;
            case R.id.act_home_rb_wd:
                if (fragment5 == null) {
                    fragment5 = new MineFragment();
                    ft.add(R.id.act_home_fl, fragment5, fragment5Tag);
                } else {
                    ft.show(fragment5);
                }
                break;
            default:
                break;
        }
        ft.commitAllowingStateLoss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }

    }

    @Override
    public void onFragment(String content) {
        if (content.equals("加油")){
            actHomeRbJy.setChecked(true);
        }else if (content.equals("会员权益")){
            actHomeRbHyqy.setChecked(true);
        }
    }
}
