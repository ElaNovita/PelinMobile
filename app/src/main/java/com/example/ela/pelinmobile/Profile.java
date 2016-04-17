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
import android.widget.TextView;

import com.example.ela.pelinmobile.Adapter.GroupListAdapter;
import com.example.ela.pelinmobile.Interface.UserInterface;
import com.example.ela.pelinmobile.Model.Group;
import com.example.ela.pelinmobile.Model.User;

import java.io.IOException;
import java.util.List;

import com.example.ela.pelinmobile.Helper.RetrofitBuilder;

import butterknife.Bind;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ela on 14/03/16.
 */
public class Profile extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    String TAG = "respon";
    public static final String myPref = "myPrefs";
    public static final String BaseUrl = "http://pelinapi-edsproject.rhcloud.com/api/";

    private List<Group> groups;

    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        groups = Group.initData();

        context = getApplicationContext();

        final TextView kode = (TextView) findViewById(R.id.code);
        final TextView username = (TextView) findViewById(R.id.user_name);

//        com.example.ela.pelinmobile.Interface.UserInterface user =
//                RetrofitBuilder.retrofit.create(com.example.ela.pelinmobile.Interface.UserInterface.class);
//        Interceptor interceptor = new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                Request req = chain.request().newBuilder().addHeader("Authorization", "Token f0367a1b6b0b68356775626b0e2a99991dd3861f").build();
//                return chain.proceed(req);
//            }
//        };

//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.interceptors().add(interceptor);
//        OkHttpClient client = builder.build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BaseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();

        UserInterface user = new RetrofitBuilder(Profile.this).getRetrofit().create(UserInterface.class);
        Call<User> call = user.getUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    User user = response.body();
                    String name = user.getName();
                    String nik = user.getEmail();
                    kode.setText(nik);
                    username.setText(name);
                } catch (Exception e) {
                    Log.e(TAG, "error", e);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("respon", "gagal", t);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.joinedGroupRv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        GroupListAdapter adapter = new GroupListAdapter(groups, new OnItemClickListener() {
            @Override
            public void onItemClick(Group group) {
                Intent intent = new Intent(Profile.this, GroupDetail.class);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);

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
