package com.example.ela.pelinmobile.Fragment.GroupDetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ela.pelinmobile.Adapter.ListTugasAdapter;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.TugasInterface;
import com.example.ela.pelinmobile.Model.Submitted;
import com.example.ela.pelinmobile.OnItemClickListener;
import com.example.ela.pelinmobile.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

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
    LinearLayout detailTugas;
    TextView title,detail, attachment;
    Button downloadAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_tugas);

        int groupId = getIntent().getIntExtra("groupId", 0);
        int tugasId = getIntent().getIntExtra("tugasId", 0);


        detailTugas = (LinearLayout) findViewById(R.id.detail);
        title = (TextView) findViewById(R.id.assigntTitle);
        detail = (TextView) findViewById(R.id.assigntContent);
        attachment = (TextView) findViewById(R.id.attachment);
        downloadAll = (Button) findViewById(R.id.download_all);
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
                final List<Submitted> submittedList = response.body();
                Log.d("respon", "onResponse: submit " + response.code());

                ListTugasAdapter adapter = new ListTugasAdapter(submittedList, getApplicationContext(), new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, boolean isLongClick) {
                        String url = submittedList.get(position).getFile();

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Submitted>> call, Throwable t) {

            }
        });

    }

}
