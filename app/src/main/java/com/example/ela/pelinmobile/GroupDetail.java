package com.example.ela.pelinmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ela.pelinmobile.Adapter.GroupDetailAdapter;
import com.example.ela.pelinmobile.Fragment.CreateGroupDialog;
import com.example.ela.pelinmobile.Fragment.GroupDetail.DiskusiFragment;
import com.example.ela.pelinmobile.Fragment.GroupDetail.MateriFragment;
import com.example.ela.pelinmobile.Fragment.GroupDetail.MemberFragment;
import com.example.ela.pelinmobile.Fragment.GroupDetail.TugasFragment;
import com.example.ela.pelinmobile.Fragment.GroupInfo;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.GroupInterface;
import com.example.ela.pelinmobile.Model.ApproveModel;
import com.example.ela.pelinmobile.Model.Group;
import com.example.ela.pelinmobile.Model.GroupModel;


import java.util.ArrayList;
import java.util.List;

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
    TabHost tabHost;
    Bundle bundle;
    int semester, member;
    String groupTitle, nmDosen, desc;
    boolean isOwner;

    private String TAG = "respon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_detail);

        groupId = getIntent().getIntExtra("groupId", 0);
        groupTitle = getIntent().getStringExtra("groupTitle");
        isOwner = getIntent().getBooleanExtra("owner", false);

        Log.d(TAG, Integer.toString(groupId));



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(groupTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bundle = new Bundle();
        bundle.putInt("groupId", groupId);
        bundle.putString("groupTitle", groupTitle);
        bundle.putBoolean("owner", isOwner);

        reqJson();


        Log.d(TAG, "onCreate: own " + isOwner);

        tabViewPager.setAdapter(new GroupDetailAdapter(getSupportFragmentManager(), bundle));
        setupViewPager(tabViewPager);
        tabLayout.setupWithViewPager(tabViewPager);
        setupDrawerContent(navigationView);
        setTabIcon();

        Log.d(TAG, "onCreate: bundle " + bundle + groupTitle);

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
//        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        tabOne.setText("Diskusi");
//        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_question_answer_white_24dp, 0, 0);
//        tabLayout.getTabAt(0).setCustomView(tabOne);
//
//        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        tabTwo.setText("Materi");
//        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_dns_white_24dp, 0, 0);
//        tabLayout.getTabAt(1).setCustomView(tabTwo);
//
//        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        tabTwo.setText("Tugas");
//        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_assignment_white_24dp, 0, 0);
//        tabLayout.getTabAt(2).setCustomView(tabThree);
//
//        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        tabTwo.setText("Member");
//        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_people_white_24dp, 0, 0);
//        tabLayout.getTabAt(3).setCustomView(tabFour);

    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTtleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTtleList.get(position);
        }

        public void addFrag(Fragment fragment, String title, Bundle bundle) {
            mFragmentList.add(fragment);
            mFragmentTtleList.add(title);
            fragment.setArguments(bundle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                this.finish();
            case R.id.action_settings:
                showDialog();
                break;
            case R.id.edit:
                Intent intent = new Intent(getApplicationContext(), EditGroup.class);
                intent.putExtra("groupId", groupId);
                intent.putExtra("groupTitle", groupTitle);
                startActivity(intent);
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
        MenuItem item3 = menu.findItem(R.id.edit);
        item.setTitle(R.string.about);
        item1.setTitle("Leave Group");
        if (isOwner) {
            item3.setTitle("Edit Group");
        } else {
            item3.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }



    private void leave(int id) {
        GroupInterface service = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(GroupInterface.class);
        Call<ApproveModel> call = service.leave(id);
        call.enqueue(new Callback<ApproveModel>() {
            @Override
            public void onResponse(Call<ApproveModel> call, Response<ApproveModel> response) {
                Log.d(TAG, "onResponse: leave" + response.code());
                reqJson();
            }

            @Override
            public void onFailure(Call<ApproveModel> call, Throwable t) {

            }
        });
    }

    public void showDialog() {
        //TODO gmn biar ga nullpaonter soalnya soalnya get valuenya setelah reqJson
        android.app.FragmentManager fragmentManager = getFragmentManager();

        Bundle bundles = new Bundle();
////        bundles.putBundle();
//        bundles.putString("group", "groupTitle");
//        bundles.putString("dosen", "nmDosen");
//        bundles.putInt("semester", 2);
//        bundles.putString("desc", "desc");
//        bundles.putInt("member", 2);
        GroupInfo groupInfo = GroupInfo.newInstance("Group Info", groupTitle, nmDosen, desc, member, semester);
        groupInfo.show(fragmentManager, "title");
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(new DiskusiFragment(), "Diskusi", bundle);
        adapter.addFrag(new MateriFragment(), "Materi", bundle);
        adapter.addFrag(new TugasFragment(), "Tugas", bundle);
        adapter.addFrag(new MemberFragment(), "Member", bundle);

        Log.d(TAG, "setupViewPager: " + bundle);

        viewPager.setAdapter(adapter);
    }

    private void reqJson() {
        GroupInterface groupInterface = new RetrofitBuilder(this).getRetrofit().create(GroupInterface.class);
        Call<GroupModel> call = groupInterface.getSingleGroup(groupId);
        call.enqueue(new Callback<GroupModel>() {
            @Override
            public void onResponse(Call<GroupModel> call, Response<GroupModel> response) {
                GroupModel group = response.body();

                Log.d(TAG, "onResponse: title " + group.getTitle());

                nmDosen = group.getTeacher().getName();
                member = group.getMember();
                semester = group.getSemester();
                desc = group.getDescription();
            }

            @Override
            public void onFailure(Call<GroupModel> call, Throwable t) {
                Log.e("bundle", "onFailure: ", t);
            }
        });
    }

}