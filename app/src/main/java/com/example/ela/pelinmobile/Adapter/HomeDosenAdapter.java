package com.example.ela.pelinmobile.Adapter;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.TextView;

import com.example.ela.pelinmobile.Fragment.AssigntFragment;
import com.example.ela.pelinmobile.Fragment.GroupListFragment;
import com.example.ela.pelinmobile.Fragment.MessagesFragment;
import com.example.ela.pelinmobile.Fragment.NotifFragment;
import com.example.ela.pelinmobile.R;

import java.util.List;

import butterknife.Bind;

/**
 * Created by ela on 17/03/16.
 */
public class HomeDosenAdapter extends FragmentPagerAdapter {

    @Bind(R.id.tablayout)
    TabLayout tabLayout;
    @Bind(R.id.tab_badge)
    TextView badge;
    String badge_notif;



    public HomeDosenAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }



    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new GroupListFragment();
                break;
            case 1:
                fragment = new AssigntFragment();
                break;

            case 2:
                fragment = new NotifFragment();
                break;
            case 3:
                fragment = new MessagesFragment();
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
