package com.example.ela.pelinmobile.Fragment.GroupDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ela.pelinmobile.Adapter.ConfirmAdapter;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.RequestInterface;
import com.example.ela.pelinmobile.Model.ApproveModel;
import com.example.ela.pelinmobile.Model.RequestModel;
import com.example.ela.pelinmobile.Model.User;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e on 9/04/16.
 */
public class ConfirmMember extends AppCompatActivity {
    RecyclerView recyclerView;
    Button confirmAll;
    int groupId = 4;
    ConfirmAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_member);

        RequestInterface requestInterface = new RetrofitBuilder(this).getRetrofit().create(RequestInterface.class);

        Call<List<RequestModel>> call = requestInterface.getUsers(4);
        call.enqueue(new Callback<List<RequestModel>>() {
            @Override
            public void onResponse(Call<List<RequestModel>> call, Response<List<RequestModel>> response) {
                List<RequestModel> requestModels = response.body();

                adapter = new ConfirmAdapter(requestModels, getApplicationContext(), groupId);
                recyclerView.setAdapter(adapter);
                Log.d("respon", "onResponse: kode " + response.code());
            }

            @Override
            public void onFailure(Call<List<RequestModel>> call, Throwable t) {

            }
        });

        confirmAll = (Button) findViewById(R.id.confirm_all_btn);
        recyclerView = (RecyclerView) findViewById(R.id.confirmRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        confirmAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmAll();
            }
        });

    }

    private void confirmAll() {
        RequestInterface requestInterface = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(RequestInterface.class);
        Call<ApproveModel> call = requestInterface.confirmAll(4);
        call.enqueue(new Callback<ApproveModel>() {
            @Override
            public void onResponse(Call<ApproveModel> call, Response<ApproveModel> response) {
                Log.d("respon", "onResponse: all " + response.code());
            }

            @Override
            public void onFailure(Call<ApproveModel> call, Throwable t) {

            }
        });
    }


}
