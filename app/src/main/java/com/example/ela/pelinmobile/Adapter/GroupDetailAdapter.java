package com.example.ela.pelinmobile.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ela.pelinmobile.Fragment.GroupDetail.DiskusiFragment;
import com.example.ela.pelinmobile.Fragment.GroupDetail.MateriFragment;
import com.example.ela.pelinmobile.Fragment.GroupDetail.MemberFragment;
import com.example.ela.pelinmobile.Fragment.GroupDetail.TugasFragment;


/**
 * Created by ela on 18/03/16.
 */
public class GroupDetailAdapter extends FragmentPagerAdapter {

    public GroupDetailAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new DiskusiFragment();
                break;
            case 1:
                fragment = new MateriFragment();
                break;
            case 2:
                fragment = new TugasFragment();
                break;
            case 3:
                fragment = new MemberFragment();
                break;
            default:
                fragment = null;
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
