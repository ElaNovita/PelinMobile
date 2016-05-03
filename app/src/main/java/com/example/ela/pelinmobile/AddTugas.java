package com.example.ela.pelinmobile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.TugasInterface;
import com.example.ela.pelinmobile.Model.TugasModel;

import java.io.File;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e on 1/04/16.
 */
public class AddTugas extends AppCompatActivity {

    DatePicker datePicker;
    Calendar calendar;
    int year, month, day;
    TextView TextuploadMateri, txtTime, txtDate;
    Button btnSendMateri, upMateri, dueTime, dueDate;
    private static final int PICKFILE_RESULT_CODE = 1;
    MultipartBody.Part requestFileBody;
    EditText title, description;
    String tugasTitle, desc;
    RequestBody titles, descriptions;
    int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tugas);

        groupId = getIntent().getIntExtra("groupId", 0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextuploadMateri = (TextView) findViewById(R.id.uploadMateri);
        txtTime = (TextView) findViewById(R.id.dueTime);
        txtDate = (TextView) findViewById(R.id.dueDate);

        btnSendMateri = (Button) findViewById(R.id.sendMateri);
        upMateri = (Button) findViewById(R.id.upMateri);
        dueDate = (Button) findViewById(R.id.selectDueDate);
        dueTime = (Button) findViewById(R.id.selectDueTime);

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
                tugasTitle = title.getText().toString();
                desc = description.getText().toString();

                titles = RequestBody.create(MediaType.parse("multipart/form-data"), tugasTitle);
                descriptions = RequestBody.create(MediaType.parse("multipart/form-data"), desc);

                sendFile(requestFileBody, titles, descriptions);

                Log.d("respon", "onClick: " + Integer.toString(groupId));

                Intent intent = new Intent(getApplicationContext(), GroupDetail.class);
                intent.putExtra("groupId", groupId);
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
        TugasInterface service = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(TugasInterface.class);
        Call<TugasModel> call = service.createTugas(groupId, files, titles, descriptions);
        call.enqueue(new Callback<TugasModel>() {
            @Override
            public void onResponse(Call<TugasModel> call, Response<TugasModel> response) {
                Log.d("respon", "onResponse: respon " + response.code());
                Toast.makeText(getApplicationContext(), "Tugas Sudah di Upload", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<TugasModel> call, Throwable t) {
                Log.e("respon", "onFailure: salah ", t);
                Toast.makeText(getApplicationContext(), "Tugas Gagal di Upload", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDate() {

    }
}
