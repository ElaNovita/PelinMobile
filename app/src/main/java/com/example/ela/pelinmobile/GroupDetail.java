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
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.GroupInterface;
import com.example.ela.pelinmobile.Model.ApproveModel;
import com.example.ela.pelinmobile.Model.Group;
import com.example.ela.pelinmobile.Model.GroupModel;

import java.util.ArrayList;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    int groupId;

    private String TAG = "respon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Diskusi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        groupId = getIntent().getIntExtra("groupId", 0);
        Log.d(TAG, Integer.toString(groupId));
        Bundle bundle = new Bundle();
        bundle.putInt("groupId", groupId);


        tabViewPager.setAdapter(new GroupDetailAdapter(getSupportFragmentManager(), bundle));
        tabLayout.setupWithViewPager(tabViewPager);
        setupDrawerContent(navigationView);
        setTabIcon();

        GroupInterface groupInterface = new RetrofitBuilder(this).getRetrofit().create(GroupInterface.class);
        Call<GroupModel> call = groupInterface.getSingleGroup(groupId);
        call.enqueue(new Callback<GroupModel>() {
            @Override
            public void onResponse(Call<GroupModel> call, Response<GroupModel> response) {
                GroupModel group = response.body();
                Log.d(TAG, "onResponse: " + response.code());
            }

            @Override
            public void onFailure(Call<GroupModel> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });




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
            case R.id.leave:
                leave(groupId);
                this.finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_settings);
        MenuItem item1 = menu.findItem(R.id.leave);
        item.setTitle(R.string.about);
        item1.setTitle("Leave Group");
        return super.onPrepareOptionsMenu(menu);
    }

    public void showDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        GroupInfo groupInfo = GroupInfo.newInstance("Info Group");
        groupInfo.show(fragmentManager, "title");
    }

    private void leave(int id) {
        GroupInterface service = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(GroupInterface.class);
        Call<ApproveModel> call = service.leave(id);
        call.enqueue(new Callback<ApproveModel>() {
            @Override
            public void onResponse(Call<ApproveModel> call, Response<ApproveModel> response) {
                Log.d(TAG, "onResponse: leave" + response.code());
            }

            @Override
            public void onFailure(Call<ApproveModel> call, Throwable t) {

            }
        });
    }

}