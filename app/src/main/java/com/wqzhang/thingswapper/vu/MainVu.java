package com.wqzhang.thingswapper.vu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.dao.BusinessProcessImpl;
import com.wqzhang.thingswapper.fragment.PersonSetFragment;
import com.wqzhang.thingswapper.fragment.PoolFragment;
import com.wqzhang.thingswapper.fragment.ShowThingsFragment;

/**
 * Created by wqzhang on 17-1-5.
 */

public class MainVu implements Vu {
    private View view;
    private LinearLayout navigationShowThings, navigationCharts, navigationSetting;
    private Toolbar toolbar;
    private FrameLayout containerView;
    private TextView toolbarTitle;
    private ImageView navigationSettingImage, navigationShowThingsImage, navigationChartImage;
    private FloatingActionButton fab;
    private Fragment currentFragment;

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.activity_main, container, false);
        navigationSetting = (LinearLayout) view.findViewById(R.id.setting);
        navigationShowThings = (LinearLayout) view.findViewById(R.id.show_things);
        navigationCharts = (LinearLayout) view.findViewById(R.id.pool);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        containerView = (FrameLayout) view.findViewById(R.id.main_content);
        toolbarTitle = (TextView) view.findViewById(R.id.toolbartitle);
        navigationSettingImage = (ImageView) view.findViewById(R.id.navigation_setting_icon);
        navigationChartImage = (ImageView) view.findViewById(R.id.navigation_chart_icon);
        navigationShowThingsImage = (ImageView) view.findViewById(R.id.navigation_showthings_icon);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
    }

    @Override
    public View getView() {
        return view;
    }

    //第一次初始化fragmenmt的时候调用,否则会造成switchContent 方法nullPoint
    public void initFragment(Fragment initFragment, FragmentManager fm) {
        fm.beginTransaction().replace(getContainerId(), initFragment).commit();
        currentFragment = initFragment;
        setToolbarTittle("事项清单");
        navigationShowThingsImage.setBackgroundResource(R.drawable.icon_navigation_list_selected);
    }

    public void switchContent(Fragment to, FragmentManager fm) {

        if (currentFragment != to) {
            //添加渐隐渐现的动画
            android.app.FragmentTransaction ft = fm.beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                ft.hide(currentFragment).add(containerView.getId(), to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                ft.hide(currentFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
                if (to instanceof PoolFragment) {
                    ((PoolFragment) to).resetData();
                }
            }

            //设置跳转后 条目的提示语和图标
            if (to instanceof ShowThingsFragment) {
                setToolbarTittle("事项清单");
                navigationShowThingsImage.setBackgroundResource(R.drawable.icon_navigation_list_selected);
                fab.setVisibility(View.VISIBLE);
                if (BusinessProcessImpl.getInstance().needRefreshDisplayData()) {
                    ((ShowThingsFragment) to).refreshData();
                }
            } else if (to instanceof PoolFragment) {
                setToolbarTittle("汇总");
                navigationChartImage.setBackgroundResource(R.drawable.icon_navigation_chart_selected);
            } else if (to instanceof PersonSetFragment) {
                setToolbarTittle("个性化设置");
                navigationSettingImage.setBackgroundResource(R.drawable.icon_navigation_set_selected);
                if (BusinessProcessImpl.getInstance().needRefreshDisplayData()) {
                    ((PersonSetFragment) to).refreshData();
                }
            }

            //修改之前 所在 条目 的图标
            if (currentFragment instanceof ShowThingsFragment) {
                navigationShowThingsImage.setBackgroundResource(R.drawable.icon_navigation_list);
                fab.setVisibility(View.INVISIBLE);
            } else if (currentFragment instanceof PoolFragment) {
                navigationChartImage.setBackgroundResource(R.drawable.icon_navigation_chart);
            } else if (currentFragment instanceof PersonSetFragment) {
                navigationSettingImage.setBackgroundResource(R.drawable.icon_navigation_set);
            }
            currentFragment = to;
        }
    }

    public Toolbar getToolBar() {
        return toolbar;
    }


    public int getContainerId() {
        return containerView.getId();
    }


    public LinearLayout getNavigationSetting() {
        return navigationSetting;
    }

    public LinearLayout getNavigationCharts() {
        return navigationCharts;
    }

    public FrameLayout getContainerView() {
        return containerView;
    }

    public LinearLayout getNavigationShowThings() {
        return navigationShowThings;
    }

    public void setToolbarTittle(String title) {
        toolbarTitle.setText(title);
    }

    public FloatingActionButton getFab() {
        return fab;
    }
}
