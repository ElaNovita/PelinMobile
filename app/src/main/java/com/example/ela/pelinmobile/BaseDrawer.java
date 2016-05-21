package com.example.ela.pelinmobile;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ela.pelinmobile.Helper.MySharedPreferences;

import butterknife.Bind;

/**
 * Created by ela on 13/03/16.
 */
public class BaseDrawer extends BaseAcitivty {

    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.navigationView)
    NavigationView navigationView;


    @Override
    public void setContentView(int layoutResID) {
        super.setContentViewWithoutInject(R.layout.base_drawer);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.frameLayout);
        LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
        bindView();
        drawerLayout.closeDrawers();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();
                return true;
            }
        });

        View headerView = navigationView.inflateHeaderView(R.layout.drawer_header);
        ImageView imageView = (ImageView) headerView.findViewById(R.id.user_nav);
        TextView textView = (TextView) headerView.findViewById(R.id.user_nav_name);

        MySharedPreferences mf = new MySharedPreferences(getApplicationContext());

        String userImage = mf.getUserImage();

        Glide.with(getApplicationContext()).load(userImage).into(imageView);
        textView.setText(mf.getUsername());
    }

    @Override
    protected void setUpToolBar() {
        super.setUpToolBar();
        if (getToolbar() != null) {
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            });
        }
    }

    public void goToProfile(View view) {
        Intent intent = new Intent(getApplicationContext(), Profile.class);
        startActivity(intent);
        drawerLayout.closeDrawers();
    }


}
