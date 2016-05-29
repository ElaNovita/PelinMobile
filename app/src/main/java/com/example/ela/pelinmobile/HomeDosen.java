package com.example.ela.pelinmobile;

import android.content.Context;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ela.pelinmobile.Adapter.HomeDosenAdapter;
import com.example.ela.pelinmobile.Fragment.CreateGroupDialog;
import com.example.ela.pelinmobile.Helper.MySharedPreferences;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.FragmentComunicator;
import com.example.ela.pelinmobile.Interface.MyInterface;
import com.example.ela.pelinmobile.Model.User;
import com.readystatesoftware.viewbadger.BadgeView;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ela on 13/03/16.
 */
public class HomeDosen extends BaseDrawer implements FragmentComunicator{

    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.tablayout)
    TabLayout tabLayout;
    @Bind(R.id.tabViewPager)
    ViewPager tabViewPager;
    String tab_badges;
    @Bind(R.id.navigationView)
    NavigationView navigationView;

    private BadgeView badgeView, notifBadge;
    public int buttonCounter, badgePosition;
    String TAG = "respon";
    boolean isTeacher;
    ImageView imageView, notif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_dosen);

        tabViewPager.setAdapter(new HomeDosenAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(tabViewPager);

        MySharedPreferences mf = new MySharedPreferences(getApplicationContext());
        mf.getStatus();

        Log.d(TAG, "onCreate: log " + mf.getStatus());

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

        Drawable drawable = getResources().getDrawable(title[1]);
        Drawable drawable1 = getResources().getDrawable(title[2]);

        imageView = new ImageView(this);
        notif = new ImageView(this);

        imageView.setImageDrawable(drawable);
        notif.setImageDrawable(drawable1);

        tabLayout.getTabAt(0).setIcon(title[0]);
        tabLayout.getTabAt(1).setCustomView(imageView);
        tabLayout.getTabAt(2).setCustomView(notif);
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

    @Override
    public void sendDataToActivity(int value) {
        buttonCounter = value;
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        TabLayout.Tab tab2 = tabLayout.getTabAt(2);
        imageView = new ImageView(this);
        notif = new ImageView(this);
        tab.setCustomView(imageView);
        tab2.setCustomView(notif);
        setupDrawerContent(navigationView);

        badgeView = new BadgeView(this, imageView);
        notifBadge = new BadgeView(this, notif);
        counter(buttonCounter);
        notifBadge.show();
        //TODO if counter has been fixed, set show when counter > 0

        badgeView.setText(Integer.toString(buttonCounter));
        notifBadge.setText("0");
        notifBadge.setTextColor(getResources().getColor(R.color.red));
        Log.d(TAG, "sendDataToActivity: " + value);
    }

}

