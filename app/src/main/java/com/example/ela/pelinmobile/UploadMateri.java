package com.example.ela.pelinmobile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.MateriInterface;
import com.example.ela.pelinmobile.Model.MateriModel;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e on 31/03/16.
 */
public class UploadMateri extends AppCompatActivity {

    EditText title, description;
    TextView TextuploadMateri;
    Button btnSendMateri, upMateri;
    private static final int PICKFILE_RESULT_CODE = 1;
    File file;
    RequestBody requestBody;
    MediaType MEDIA_TYPE_PNG;
    String materiTitle, desc, groupTitle;
    MultipartBody.Part requestFileBody;
    RequestBody titles, descriptions;
    int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_materi);

        groupId = getIntent().getIntExtra("groupId", 0);
        groupTitle = getIntent().getStringExtra("groupTitle");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextuploadMateri = (TextView) findViewById(R.id.uploadMateri);
        btnSendMateri = (Button) findViewById(R.id.sendMateri);
        upMateri = (Button) findViewById(R.id.upMateri);
        title = (EditText) findViewById(R.id.materi_title);
        description = (EditText) findViewById(R.id.desc);

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
                desc = description.getText().toString();

                titles = RequestBody.create(MediaType.parse("multipart/form-data"), materiTitle);
                descriptions = RequestBody.create(MediaType.parse("multipart/form-data"), desc);

                sendFile(requestFileBody, titles, descriptions);



                Intent intent = new Intent(getApplicationContext(), GroupDetail.class);
                intent.putExtra("groupId", groupId);
                intent.putExtra("groupTitle", groupTitle);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == RESULT_OK) {


                    String name = data.getData().getLastPathSegment().toString();

                    TextuploadMateri.setText(name);

                    Uri uri = data.getData();

                    Log.d("respon", "onActivityResult: " + uri.toString());

                    File file = new File(uri.getPath());

                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    requestFileBody = MultipartBody.Part.createFormData("files", file.getName(), requestFile);

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

    private void sendFile(MultipartBody.Part files, RequestBody titles, RequestBody descriptions) {

        MateriInterface materiInterface = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(MateriInterface.class);
        Call<MateriModel> call = materiInterface.createMateri(groupId, files, titles, descriptions);
        call.enqueue(new Callback<MateriModel>() {
            @Override
            public void onResponse(Call<MateriModel> call, Response<MateriModel> response) {
                Log.d("respon", "onResponse: respon " + response.code());
                Log.d("respon", "onClick: description" + response.body().getDescription());
                Toast.makeText(getApplicationContext(), "Materi Sudah di Upload", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MateriModel> call, Throwable t) {
                Log.e("respon", "onFailure: salah ", t);
                Toast.makeText(getApplicationContext(), "Materi Gagal di Upload", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
