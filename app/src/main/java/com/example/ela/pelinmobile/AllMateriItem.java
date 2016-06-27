package com.example.ela.pelinmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.ela.pelinmobile.Adapter.MateriAdapter;
import com.example.ela.pelinmobile.Helper.CustomDateFormatter;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.MateriInterface;
import com.example.ela.pelinmobile.Model.MateriModel;

import java.text.ParseException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e on 28/06/16.
 */
public class AllMateriItem extends AppCompatActivity {

    RecyclerView recyclerView;
    MateriAdapter adapter;
    CustomDateFormatter cdf = new CustomDateFormatter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_materi);

        recyclerView = (RecyclerView) findViewById(R.id.materiRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        reqJson(getIntent().getIntExtra("groupId", 0));

    }

    private void reqJson(int groupId) {
        MateriInterface service = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(MateriInterface.class);
        Call<List<MateriModel>> call = service.getPublicMateri(groupId);
        call.enqueue(new Callback<List<MateriModel>>() {
            @Override
            public void onResponse(Call<List<MateriModel>> call, Response<List<MateriModel>> response) {
                final List<MateriModel> materiList = response.body();
                adapter = new MateriAdapter(materiList, getApplicationContext());

                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(getApplicationContext(), PublicMateriDetail.class);
                        intent.putExtra("materiId", materiList.get(position).getId());
                        intent.putExtra("title", materiList.get(position).getTitle());
                        try {
                            intent.putExtra("date", cdf.getTimeAgo(materiList.get(position).getCreatedAt()));
                        } catch (ParseException e) {
                            intent.putExtra("date", "");
                        }
                        intent.putExtra("desc", materiList.get(position).getDescription());
                        intent.putExtra("groupId", getIntent().getIntExtra("groupId", 0));
                        startActivity(intent);
                    }
                });

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<MateriModel>> call, Throwable t) {

            }
        });
    }
}
