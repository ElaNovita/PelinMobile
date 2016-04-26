package com.example.ela.pelinmobile;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ela.pelinmobile.Adapter.HomeDosenAdapter;
import com.example.ela.pelinmobile.Fragment.CreateGroupDialog;
import com.readystatesoftware.viewbadger.BadgeView;

import butterknife.Bind;

/**
 * Created by ela on 13/03/16.
 */
public class HomeDosen extends BaseDrawer {

    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.tablayout)
    TabLayout tabLayout;
    @Bind(R.id.tabViewPager)
    ViewPager tabViewPager;
    String tab_badges;
    @Bind(R.id.navigationView)
    NavigationView navigationView;

    private BadgeView badgeView;
    int buttonCounter = 2;
    Button add, dec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_dosen);
        tabViewPager.setAdapter(new HomeDosenAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(tabViewPager);
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        ImageView imageView = new ImageView(this);
        tab.setCustomView(imageView);
        setupDrawerContent(navigationView);


//        badgeView = new BadgeView(this, imageView);
//        badgeView.setText("1");
//        badgeView.show();
//        add = (Button) findViewById(R.id.plus);
//        dec = (Button) findViewById(R.id.minus);
//
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                buttonCounter += 1;
//                refreshBagde();
//            }
//        });
//
//        dec.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (buttonCounter > 0) {
//                    buttonCounter -= 1;
//                    refreshBagde();
//                }
//
//            }
//        });
        setUpTabIcon();



    }

    final int[] title = new int[]{
            R.drawable.ic_home_white_24dp,
            R.drawable.ic_assignment_white_24dp,
            R.drawable.ic_public_white_24dp,
            R.drawable.ic_email_white_24dp
    };

    public void setUpTabIcon() {
        tabLayout.getTabAt(0).setIcon(title[0]);

//        tabLayout.getTabAt(1).setIcon(title[1]);

        Drawable drawable = getResources().getDrawable(title[1]);
        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(drawable);

        tabLayout.getTabAt(1).setCustomView(imageView);

        badgeView = new BadgeView(this, imageView);
        badgeView.setText(Integer.toString(buttonCounter));
        counter(buttonCounter);

        tabLayout.getTabAt(2).setIcon(title[2]);
        tabLayout.getTabAt(3).setIcon(title[3]);
    }

    public void counter(int count) {
        if (count <= 0) {
            badgeView.hide();
        } else {
            badgeView.show();
        }
    }

    public void refreshBagde() {
        counter(buttonCounter);
        badgeView.setText(Integer.toString(buttonCounter));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }


}

