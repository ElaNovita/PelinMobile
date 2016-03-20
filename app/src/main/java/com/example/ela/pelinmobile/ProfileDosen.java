package com.example.ela.pelinmobile;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import butterknife.Bind;

/**
 * Created by ela on 13/03/16.
 */
public class ProfileDosen extends BaseDrawer {

    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_dosen);
    }
}
