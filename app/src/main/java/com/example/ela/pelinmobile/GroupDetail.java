package com.example.ela.pelinmobile;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ela.pelinmobile.Adapter.GroupDetailAdapter;
import com.example.ela.pelinmobile.Fragment.CreateGroupDialog;
import com.example.ela.pelinmobile.Fragment.GroupInfo;
import com.example.ela.pelinmobile.Model.Group;

import butterknife.Bind;

/**
 * Created by ela on 18/03/16.
 */
public class GroupDetail extends BaseDrawer {
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.tablayout)
    TabLayout tabLayout;
    @Bind(R.id.tabViewPager)
    ViewPager tabViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Diskusi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabViewPager.setAdapter(new GroupDetailAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(tabViewPager);
        setTabIcon();
    }

    final int tabIcon[] = new int[]{
            R.drawable.ic_question_answer_white_24dp,
            R.drawable.ic_dns_white_24dp,
            R.drawable.ic_assignment_white_24dp,
            R.drawable.ic_people_white_24dp
    };

    public void setTabIcon() {
        tabLayout.getTabAt(0).setIcon(tabIcon[0]);
        tabLayout.getTabAt(1).setIcon(tabIcon[1]);
        tabLayout.getTabAt(2).setIcon(tabIcon[2]);
        tabLayout.getTabAt(3).setIcon(tabIcon[3]);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                this.finish();
            case R.id.action_settings:
                showDialog();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_settings);
        item.setTitle(R.string.about);
        return super.onPrepareOptionsMenu(menu);
    }

    public void showDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        GroupInfo groupInfo = GroupInfo.newInstance("Info Group");
        groupInfo.show(fragmentManager, "title");
    }
}
