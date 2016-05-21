package com.example.ela.pelinmobile.Fragment.GroupDetail;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.Resource;
import com.example.ela.pelinmobile.Adapter.MateriDetailAdapter;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.MateriInterface;
import com.example.ela.pelinmobile.Model.FileModel;
import com.example.ela.pelinmobile.Model.MateriModel;
import com.example.ela.pelinmobile.OnItemClickListener;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e on 11/05/16.
 */
public class MateriDetail extends AppCompatActivity {

    ImageView bg;
    TextView title, date, desc;
    LinearLayout lnl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.materi_detail);

        bg = (ImageView) findViewById(R.id.bg);
        title = (TextView) findViewById(R.id.materi_title);
        date = (TextView) findViewById(R.id.created);
        desc = (TextView) findViewById(R.id.desc);
        lnl = (LinearLayout) findViewById(R.id.lnl);

        final Random random = new Random();
        Resources res = getResources();
        final TypedArray myImages = res.obtainTypedArray(R.array.image);

        int randomInt = random.nextInt(myImages.length());
        int drawableID = myImages.getResourceId(randomInt, -1);
        bg.setBackgroundResource(drawableID);


        title.setText(getIntent().getStringExtra("title"));
        date.setText(getIntent().getStringExtra("date"));
        desc.setText(getIntent().getStringExtra("desc"));
        int groupId = getIntent().getIntExtra("groupId", 0);
        int materiId = getIntent().getIntExtra("materiId", 0);

        reqJson(groupId, materiId);

    }

    private void reqJson(int groupId, final int materiId) {
        MateriInterface service = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(MateriInterface.class);
        Call<List<MateriModel>> call = service.getFiles(groupId, materiId);
        call.enqueue(new Callback<List<MateriModel>>() {
            @Override
            public void onResponse(Call<List<MateriModel>> call, Response<List<MateriModel>> response) {
                List<MateriModel> materiModels = response.body();
                Log.d("respon", "onResponse: files " + response.code());

                List<FileModel> files = materiModels.get(materiId).getFileModels();



                final TextView[] txt = new TextView[files.size()];

                for (int i = 0; i < files.size(); i++) {
                    final TextView textView = new TextView(getApplicationContext());

                    textView.setText("materi ke " + i);
                    lnl.addView(textView);
                    txt[i] = textView;
                }

                Log.d("respon", "materidetail/onResponse: " + response.code());
            }

            @Override
            public void onFailure(Call<List<MateriModel>> call, Throwable t) {
                Log.e("respon", "materidetail/onFailure: ", t);
            }
        });
    }

}
