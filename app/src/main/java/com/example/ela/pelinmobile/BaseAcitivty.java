package com.example.ela.pelinmobile;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ela.pelinmobile.Helper.MySharedPreferences;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ela on 13/03/16.
 */
public class BaseAcitivty extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.navigationView)
    NavigationView navigationView;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                Intent intent = new Intent(getApplicationContext(), AllGroups.class);
                startActivity(intent);
                return true;
            }
        });

        bindView();
        setupDrawerContent(navigationView);
    }

    protected void bindView() {
        ButterKnife.bind(this);
        setUpToolBar();
    }

    public void setContentViewWithoutInject(int layoutResId) {
        super.setContentView(layoutResId);
    }

    protected void setUpToolBar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.allGroupMenu:
                Intent intent = new Intent(BaseAcitivty.this, AllGroups.class);
                startActivity(intent);
                break;
            case R.id.search_group_menu:
                Intent intent1 = new Intent(BaseAcitivty.this, AllGroups.class);
                startActivity(intent1);
                break;
            case R.id.logout:
                logout();
                Intent intent2 = new Intent(BaseAcitivty.this, VideoActivity.class);
                startActivity(intent2);
                break;
            case R.id.video:
                Intent intent4 = new Intent(BaseAcitivty.this, VideoActivity.class);
                startActivity(intent4);
                break;
            case R.id.materi:
                Intent intent5 = new Intent(BaseAcitivty.this, AllMateri.class);
                startActivity(intent5);
                break;
            case R.id.home:
                Intent intent3 = new Intent(BaseAcitivty.this, HomeDosen.class);
                startActivity(intent3);
        }
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
    }


    public Toolbar getToolbar() {
        return toolbar;
    }

    public void logout() {
        MySharedPreferences sharedPreferences = new MySharedPreferences(getApplicationContext());
        String token = sharedPreferences.getToken();
        sharedPreferences.deleteToken();
        if (token == null) {
            Intent intent = new Intent(BaseAcitivty.this, Login.class);
            startActivity(intent);
        }

    }
}
