package com.example.ela.pelinmobile;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ela.pelinmobile.Adapter.GroupListAdapter;
import com.example.ela.pelinmobile.Helper.MySharedPreferences;
import com.example.ela.pelinmobile.Interface.GroupInterface;
import com.example.ela.pelinmobile.Interface.MyGroups;
import com.example.ela.pelinmobile.Interface.MyInterface;
import com.example.ela.pelinmobile.Interface.UserInterface;
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
public class OtherProfile extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    String TAG = "respon";
    int userId;
    public static final String myPref = "myPrefs";
    public static final String BaseUrl = "http://pelinapi-edsproject.rhcloud.com/api/";
    FloatingActionButton mail, phone, info;
    boolean isTeacher;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView kode, username, failed;
    ImageView user_img;
    boolean isMe;
    int UserID;

    private List<GroupModel> groups;

    RecyclerView recyclerView;

    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_profile);

        context = getApplicationContext();

        UserID = getIntent().getIntExtra("userId", 0);

        

        kode = (TextView) findViewById(R.id.code);
        username = (TextView) findViewById(R.id.user_name);
        info = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.user_detail);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        failed = (TextView) findViewById(R.id.failed);
        user_img = (ImageView) findViewById(R.id.user_photo);
        mail = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.mail);
        phone = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.phone);


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserDetail.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.joinedGroupRv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        reqUser();

        reqGroup();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reqUser();
                reqGroup();
            }
        });
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


    private void reqUser() {
        UserInterface user = new RetrofitBuilder(OtherProfile.this).getRetrofit().create(UserInterface.class);
        Call<User> call = user.getSingleUser(UserID);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    final User user = response.body();
                    String nik;

                    if (user.isTeacher()) {
                        nik = user.getTeacher().getNik();
                    } else {
                        nik = user.getStudent().getNim();
                    }

                    phone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (user.getPhone() != null) {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + user.getPhone()));
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), user.getName() + " tidak menambahkan info no ponsel", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    String name = user.getName();
                    userId = user.getId();

                    username.setText(name);
                    kode.setText(nik);

                    if (user.getPhoto().getMedium() == null) {
                        user_img.setImageResource(R.drawable.purple1);
                    } else {
                        Glide.with(getApplicationContext()).load(user.getPhoto().getMedium()).into(user_img);
                    }


                } catch (Exception e) {
                    Log.e(TAG, "gagal", e);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "failure: " + t.getMessage() , t);
            }
        });
    }

    private void reqGroup() {
        MyGroups groupInterface = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(MyGroups.class);
        Call<List<GroupModel>> callGroup = groupInterface.getMyGroups();
        callGroup.enqueue(new Callback<List<GroupModel>>() {
            @Override
            public void onResponse(Call<List<GroupModel>> call, Response<List<GroupModel>> response) {
                try {
                    final List<GroupModel> groups = response.body();

                    GroupListAdapter adapter = new GroupListAdapter(groups, new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, boolean isLongClick) {
                            if (isLongClick) {
                                Toast.makeText(getApplicationContext(), "tes", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(getApplicationContext(), GroupDetail.class);
                                intent.putExtra("groupId", groups.get(position).getId());
                                startActivity(intent);
                            }


                        }
                    });

                    swipeRefreshLayout.setRefreshing(false);
                    failed.setVisibility(View.GONE);

                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    Log.e("respon", "onResponse: error", e);
                }
            }

            @Override
            public void onFailure(Call<List<GroupModel>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                failed.setVisibility(View.VISIBLE);
            }
        });
    }

}
