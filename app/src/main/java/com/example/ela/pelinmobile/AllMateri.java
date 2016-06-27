package com.example.ela.pelinmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.ela.pelinmobile.Adapter.GroupListAdapter;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.GroupInterface;
import com.example.ela.pelinmobile.Model.Group;
import com.example.ela.pelinmobile.Model.GroupModel;

import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e on 24/06/16.
 */
public class AllMateri extends AppCompatActivity {


    GroupListAdapter mAdapter;
    private List<GroupModel> groupModels;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_group_list);

        recyclerView = (RecyclerView) findViewById(R.id.groupRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        reqJson();
        //TODO how to get group without token?
    }

    private void reqJson() {
        Log.d("reqjson", "reqJson: " );

        GroupInterface service = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(GroupInterface.class);
        Call<List<GroupModel>> call = service.getPublicGroups();
        call.enqueue(new Callback<List<GroupModel>>() {
            @Override
            public void onResponse(Call<List<GroupModel>> call, final Response<List<GroupModel>> response) {
                Log.d("respon", "onResponse: hasil " + response.code());
                groupModels = response.body();

                mAdapter = new GroupListAdapter(groupModels, new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(AllMateri.this, AllMateriItem.class);
                        intent.putExtra("groupId", groupModels.get(position).getId());
                        startActivity(intent);
                    }
                });

                recyclerView.setAdapter(mAdapter);
//                stopAnim();
            }

            @Override
            public void onFailure(Call<List<GroupModel>> call, Throwable t) {
                Log.e("respon", "onFailure: gagal ", t);
//                stopAnim();
            }
        });
    }
}
