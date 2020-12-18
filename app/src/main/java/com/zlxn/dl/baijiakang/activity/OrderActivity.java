package com.zlxn.dl.baijiakang.activity;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.nuogao.titlebar.widget.CommonTitleBar;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.adapter.FmPagerAdapter;
import com.zlxn.dl.baijiakang.base.BaseActivity;
import com.zlxn.dl.baijiakang.fragment.AllFragment;
import com.zlxn.dl.baijiakang.fragment.CompleteFragment;
import com.zlxn.dl.baijiakang.fragment.EvaluateFragment;
import com.zlxn.dl.baijiakang.fragment.PayFragment;
import com.zlxn.dl.baijiakang.fragment.SendFragment;
import com.zlxn.dl.baijiakang.fragment.TakeFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/13 16:55
 */
public class OrderActivity extends BaseActivity {

    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private int activeColor = Color.parseColor("#ffffff");
    private int normalColor = Color.parseColor("#ffffff");
    private int normalSize = 15;

    private ArrayList<Fragment> fragments;
    private TabLayoutMediator mediator;


    final String[] tabs = new String[]{"全部", "待支付", "待发货", "待收货", "待评价", "已完成"};

    @Override
    protected void initView() {
        setContentView(R.layout.act_order);
    }

    @Override
    protected void initData() {

        commonBar.setBackgroundResource(R.color.main_color);

        commonBar.getLeftImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fragments = new ArrayList<>();


        fragments.add(new AllFragment());
        fragments.add(new PayFragment());
        fragments.add(new SendFragment());
        fragments.add(new TakeFragment());
        fragments.add(new EvaluateFragment());
        fragments.add(new CompleteFragment());


        for (int i = 0; i < tabs.length; i++) {
            tabLayout.addTab(tabLayout.newTab());
        }


        viewPager.setAdapter(new FmPagerAdapter(fragments, getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        for (int j = 0; j < tabs.length; j++) {
            tabLayout.getTabAt(j).setText(tabs[j]);
        }

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 5, 5);
            }
        });


       /* //禁用预加载
        viewPager.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        //Adapter
        viewPager.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                //FragmentStateAdapter内部自己会管理已实例化的fragment对象。
                // 所以不需要考虑复用的问题
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return tabs.length;
            }
        });

        //viewPager 页面切换监听
        viewPager.registerOnPageChangeCallback(changeCallback);

        mediator = new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //这里可以自定义TabView
                TextView tabView = new TextView(OrderActivity.this);

                int[][] states = new int[2][];
                states[0] = new int[]{android.R.attr.state_selected};
                states[1] = new int[]{};

                int[] colors = new int[]{activeColor, normalColor};
                ColorStateList colorStateList = new ColorStateList(states, colors);
                tabView.setText(tabs[position]);
                tabView.setTextSize(normalSize);
                tabView.setTextColor(colorStateList);

                tab.setCustomView(tabView);
            }
        });
        //要执行这一句才是真正将两者绑定起来
        mediator.attach();*/

    }



    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("slidingTabIndicator");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }


   /* private ViewPager2.OnPageChangeCallback changeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            //可以来设置选中时tab的大小
            int tabCount = tabLayout.getTabCount();
            for (int i = 0; i < tabCount; i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                TextView tabView = (TextView) tab.getCustomView();
                tabView.setGravity(Gravity.CENTER);
            }
        }
    };*/


}
