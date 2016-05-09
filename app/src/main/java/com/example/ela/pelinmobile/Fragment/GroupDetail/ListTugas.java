package com.example.ela.pelinmobile.Fragment.GroupDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.ela.pelinmobile.Adapter.ListTugasAdapter;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.TugasInterface;
import com.example.ela.pelinmobile.Model.Submitted;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e on 8/04/16.
 */
public class ListTugas extends AppCompatActivity {
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_tugas);

        int groupId = getIntent().getIntExtra("groupId", 0);
        int tugasId = getIntent().getIntExtra("tugasId", 0);

        recyclerView = (RecyclerView) findViewById(R.id.listtugasRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reqJson(groupId, tugasId);

    }

    private void reqJson(int groupId, int assignId) {
        TugasInterface service = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(TugasInterface.class);
        Call<List<Submitted>> call = service.getSubmitted(groupId, assignId);
        call.enqueue(new Callback<List<Submitted>>() {
            @Override
            public void onResponse(Call<List<Submitted>> call, Response<List<Submitted>> response) {
                List<Submitted> submittedList = response.body();
                Log.d("respon", "onResponse: submit " + response.code());

                ListTugasAdapter adapter = new ListTugasAdapter(submittedList, getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Submitted>> call, Throwable t) {

            }
        });

    }

}
