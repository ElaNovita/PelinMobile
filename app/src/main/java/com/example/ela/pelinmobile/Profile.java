package com.example.ela.pelinmobile;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ela.pelinmobile.Adapter.GroupListAdapter;
import com.example.ela.pelinmobile.Interface.GroupInterface;
import com.example.ela.pelinmobile.Interface.MyGroups;
import com.example.ela.pelinmobile.Interface.MyInterface;
import com.example.ela.pelinmobile.Model.GroupModel;
import com.example.ela.pelinmobile.Model.User;
import java.util.List;

import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.github.clans.fab.FloatingActionButton;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ela on 14/03/16.
 */
public class Profile extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    String TAG = "respon";
    int userId;
    public static final String myPref = "myPrefs";
    public static final String BaseUrl = "http://pelinapi-edsproject.rhcloud.com/api/";
    FloatingActionButton mail, phone, info;

    private List<GroupModel> groups;

    RecyclerView recyclerView;

    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);


        context = getApplicationContext();

        final TextView kode = (TextView) findViewById(R.id.code);
        final TextView username = (TextView) findViewById(R.id.user_name);
        info = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.user_detail);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserDetail.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });


        MyInterface user = new RetrofitBuilder(Profile.this).getRetrofit().create(MyInterface.class);
        Call<User> call = user.getUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    User user = response.body();

                    String name = user.getName();
                    String nik = user.getStudent().getNim();
                    userId = user.getId();

                    username.setText(name);
                    kode.setText(nik);

                } catch (Exception e) {
                    Log.e(TAG, "gagal", e);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "failure: " + t.getMessage() , t);
            }
        });

        MyGroups groupInterface = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(MyGroups.class);
        Call<List<GroupModel>> callGroup = groupInterface.getMyGroups();
        callGroup.enqueue(new Callback<List<GroupModel>>() {
            @Override
            public void onResponse(Call<List<GroupModel>> call, Response<List<GroupModel>> response) {
                try {
                    List<GroupModel> groups = response.body();

                    GroupListAdapter adapter = new GroupListAdapter(groups, new OnItemClickListener() {
                        @Override
                        public void onItemClick(GroupModel group, int position) {
                            Intent intent = new Intent(getApplicationContext(), GroupDetail.class);
                            startActivity(intent);
                        }
                    });

                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    Log.e("respon", "onResponse: error", e);
                }
            }

            @Override
            public void onFailure(Call<List<GroupModel>> call, Throwable t) {

            }
        });



        recyclerView = (RecyclerView) findViewById(R.id.joinedGroupRv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                this.finish();
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static Context getContext() {
        return context;
    }

}
