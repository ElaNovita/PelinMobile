package com.example.ela.pelinmobile.Adapter;

import android.os.Bundle;
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
    Bundle bundle;

    public GroupDetailAdapter(FragmentManager fm, Bundle bundle) {
        super(fm);
        this.bundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new Fragment() ;
//        switch (position) {
//            case 0:
//                fragment = new DiskusiFragment();
//                fragment.setArguments(bundle);
//                break;
//            case 1:
//                fragment = new MateriFragment();
//                fragment.setArguments(bundle);
//                break;
//            case 2:
//                fragment = new TugasFragment();
//                fragment.setArguments(bundle);
//                break;
//            case 3:
//                fragment = new MemberFragment();
//                fragment.setArguments(bundle);
//                break;
//            default:
//                fragment = null;
//                break;
//        }

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