package com.example.administrator.guosounews.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.Window;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.base.BaseFragment;
import com.example.administrator.guosounews.fragment.HomeFragment;
import com.example.administrator.guosounews.fragment.MenuFragment2;
import com.example.administrator.guosounews.fragment.RightMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends SlidingFragmentActivity {
    SlidingMenu slidingMenu;
    MenuFragment2 menuFragment;
    HomeFragment homeFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.content);
        setBehindContentView(R.layout.menu);

        initFragment();

        initMenuData();

    }

    private void initFragment() {
        menuFragment = new MenuFragment2();
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame,
                menuFragment, "Menu").commit();

        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                homeFragment, "Home").commit();

    }

    private void initMenuData() {
        slidingMenu = getSlidingMenu();
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        slidingMenu.setBehindWidthRes(R.dimen.slidingmenu_offset);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

        RightMenuFragment rightMenuFragment = new RightMenuFragment();
        slidingMenu.setSecondaryMenu(R.layout.right_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.right_menu_frame,
                rightMenuFragment).commit();

        homeFragment.toggleMenu(slidingMenu);
    }

    public void switchFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                fragment).commit();

        slidingMenu.toggle();
    }

    public MenuFragment2 getMenuFragment2(){
        menuFragment = (MenuFragment2) getSupportFragmentManager().findFragmentByTag("Menu");
        return menuFragment;
    }

}
