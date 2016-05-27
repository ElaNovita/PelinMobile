package com.example.ela.pelinmobile.Fragment.GroupDetail;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.MateriInterface;
import com.example.ela.pelinmobile.Model.FileModel;
import com.example.ela.pelinmobile.Model.MateriModel;
import com.example.ela.pelinmobile.R;

import java.text.DecimalFormat;
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
    TextView title, date, desc, materiTitle;

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

        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        //TODO cannot back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    private String readableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB"};
        int digit = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digit)) + " " + units[digit];
    }

    private void reqJson(int groupId, final int materiId) {
        MateriInterface service = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(MateriInterface.class);
        Call<MateriModel> call = service.getFiles(groupId, materiId);
        call.enqueue(new Callback<MateriModel>() {
            @Override
            public void onResponse(Call<MateriModel> call, Response<MateriModel> response) {
                MateriModel materiModels = response.body();
                Log.d("respon", "onResponse: files " + response.code());

                final List<FileModel> files = materiModels.getFileModels();

                final TextView[] txt = new TextView[files.size()];
                final CardView[] cv = new CardView[files.size()];



                for (int i = 0; i < files.size(); i++) {

                    String sizes = readableFileSize(files.get(i).getSize());

//                    textView.setText(files.get(i).getName() + " " + sizes);
//                    textView.setTextColor(Color.parseColor("#00BCD4"));
//                    textView.setPadding(10, 10, 8, 10);

//                    lnl.addView(textView);
//                    txt[i] = textView;

                    final String url = files.get(i).getFile();

                    addLayout(files.get(i).getName(), sizes);
                    lnl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    });
                }

                Log.d("respon", "materidetail/onResponse: " + response.code());
            }

            @Override
            public void onFailure(Call<MateriModel> call, Throwable t) {
                Log.e("respon", "materidetail/onFailure: ", t);
            }
        });
    }

    private void addLayout(String name, String sizes) {
        View layout2 = LayoutInflater.from(this).inflate(R.layout.list_materi, lnl, false);

        TextView textView = (TextView) layout2.findViewById(R.id.file_name);
        TextView size = (TextView) layout2.findViewById(R.id.size);

        textView.setText(name);
        size.setText(sizes);

        lnl.addView(layout2);
    }

}
