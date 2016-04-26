package com.example.ela.pelinmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ela.pelinmobile.Fragment.GroupDetail.MateriFragment;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.MateriInterface;
import com.example.ela.pelinmobile.Model.MateriModel;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e on 31/03/16.
 */
public class UploadMateri extends AppCompatActivity {

    TextView TextuploadMateri, title;
    Button btnSendMateri;
    ImageButton upMateri;
    private static final int PICKFILE_RESULT_CODE = 1;

    String materiTitle, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_materi);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final int groupId = getIntent().getIntExtra("groupId", 0);

        Log.d("respon", Integer.toString(groupId));

        TextuploadMateri = (TextView) findViewById(R.id.uploadMateri);
        btnSendMateri = (Button) findViewById(R.id.sendMateri);
        upMateri = (ImageButton) findViewById(R.id.upMateri);
        title = (TextView) findViewById(R.id.materi_title);

        upMateri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, PICKFILE_RESULT_CODE);
            }
        });

        btnSendMateri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                materiTitle = title.getText().toString();
                desc = TextuploadMateri.getText().toString();

                MateriInterface materiInterface = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(MateriInterface.class);

                MateriModel materi = new MateriModel();
                materi.setTitle(materiTitle);
                materi.setDescription(desc);

                Call<MateriModel> call = materiInterface.createMateri(groupId);
                call.enqueue(new Callback<MateriModel>() {
                    @Override
                    public void onResponse(Call<MateriModel> call, Response<MateriModel> response) {

                    }

                    @Override
                    public void onFailure(Call<MateriModel> call, Throwable t) {

                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    String FilePath = data.getData().getLastPathSegment();
                    TextuploadMateri.setText(FilePath);
                    btnSendMateri.setVisibility(View.VISIBLE);
                }
                break;
        }
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
}
