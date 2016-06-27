package com.example.ela.pelinmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.ela.pelinmobile.Adapter.ListVideoAdapter;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.ListVideInterface;
import com.example.ela.pelinmobile.Model.ListVidModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e on 17/06/16.
 */
public class ListVideo extends AppCompatActivity {
    RecyclerView recyclerView;
    ListVideoAdapter adapter;
    List<ListVidModel> vidModels;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_video);

        recyclerView = (RecyclerView) findViewById(R.id.list_vidRv);



//        adapter = new ListVideoAdapter(getApplicationContext(), vidModels);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());

//        adapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position, boolean isLongClick) {
//                Intent intent = new Intent(getApplicationContext(), VideoPlayer.class);
//                startActivity(intent);
//            }
//        });

        reqJson();

        recyclerView.setLayoutManager(llm);
//        recyclerView.setAdapter(adapter);

    }

    private void reqJson() {
        ListVideInterface service = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(ListVideInterface.class);
        Call<List<ListVidModel>> call = service.getVideos();
        call.enqueue(new Callback<List<ListVidModel>>() {
            @Override
            public void onResponse(final Call<List<ListVidModel>> call, Response<List<ListVidModel>> response) {
                final List<ListVidModel> vidModels = response.body();



                adapter = new ListVideoAdapter(getApplicationContext(), vidModels);
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, boolean isLongClick) {

                        ArrayList<String> categoris = new ArrayList<String>();
                        for (int i = 0; i < vidModels.get(position).getCategory().size(); i++) {
                            categoris.add(vidModels.get(position).getCategory().get(i));
                        }

                        Log.d("respon", "onItemClick: " + categoris);
//                        categoris.add(vidModels.get(position).getCategory());

                        Intent intent = new Intent(getApplicationContext(), VideoPlayer.class);
                        intent.putExtra("youtubeId", vidModels.get(position).getYoutubeId());
                        intent.putExtra("title", vidModels.get(position).getTitle());
                        intent.putExtra("user", vidModels.get(position).getUser().getName());
                        intent.putExtra("created", vidModels.get(position).getCreatedAt());
                        intent.putExtra("desc", vidModels.get(position).getDescription());
                        intent.putStringArrayListExtra("tags", categoris);
                        startActivity(intent);
                    }
                });

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ListVidModel>> call, Throwable t) {

            }
        });
    }

}
