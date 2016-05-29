package com.example.ela.pelinmobile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ela.pelinmobile.Helper.CustomDateFormatter;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.TugasInterface;
import com.example.ela.pelinmobile.Model.SubmitModel;
import com.example.ela.pelinmobile.Model.TugasModel;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e on 30/03/16.
 */
public class AssigntDetail extends AppCompatActivity {
    TextView timer;
    TextView namaFile, title, content, attachment;
    EditText desc;
    Button assigntSend, uploadImg;
    long milis;
    private static final int PICKFILE_RESULT_CODE = 1;
    ProgressBar progressBar;
    MyCountDownTimer myCountDownTimer;
    RequestBody text;
    MultipartBody.Part requestFileBody;
    String _text, attach, dueDate;
    int groupId, tugasId;
    ImageView attachImg;
    String fileName;
    CustomDateFormatter cdf = new CustomDateFormatter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignt_detail);

        timer = (TextView) findViewById(R.id.timer);
        uploadImg = (Button) findViewById(R.id.uploadImg);
        namaFile = (TextView) findViewById(R.id.nmFile);
        assigntSend = (Button) findViewById(R.id.assigntUpload);
        title = (TextView) findViewById(R.id.assigntTitle);
        content = (TextView) findViewById(R.id.assigntContent);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        desc = (EditText) findViewById(R.id.desc);
        attachment = (TextView) findViewById(R.id.attachment);
        attachImg = (ImageView) findViewById(R.id.attachImg);

        groupId = getIntent().getIntExtra("groupId", 0);
        tugasId = getIntent().getIntExtra("tugasId", 0);
        attach = getIntent().getStringExtra("file");
        dueDate = getIntent().getStringExtra("due");
        String _title = getIntent().getStringExtra("title");
        String _content = getIntent().getStringExtra("content");

        if (attach != null) {
            fileName = attach.substring(attach.lastIndexOf('/') + 1);
        }

        milis = cdf.getTimeRemain(dueDate);

        title.setText(_title);
        content.setText(_content);
        attachment.setText(fileName);

        if (attach == null) {
            attachment.setVisibility(View.GONE);
            attachImg.setVisibility(View.GONE);
        }

        progressBar.setProgress(100);
        myCountDownTimer = new MyCountDownTimer(milis, 1000);
        myCountDownTimer.start();
        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, PICKFILE_RESULT_CODE);
            }
        });

        assigntSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _text = desc.getText().toString();

                text = RequestBody.create(MediaType.parse("multipart/form-data"), _text);
                if (_text == null && requestFileBody == null) {
                    Toast.makeText(getApplicationContext(), "Anda belum mengisi jawaban atau memilih file", Toast.LENGTH_SHORT).show();
                }

                reqJson(groupId, tugasId, requestFileBody, text);

                Intent intent = new Intent(getApplicationContext(), HomeDosen.class);
                startActivity(intent);

            }
        });

        attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(attach));
                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == RESULT_OK) {

                    String name = data.getData().getLastPathSegment().toString();

                    namaFile.setText(name);

                    assigntSend.setVisibility(View.VISIBLE);
                    namaFile.setVisibility(View.VISIBLE);

                    Uri uri = data.getData();

                    File file = new File(uri.getPath());

                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    requestFileBody = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                }
                break;
        }
    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long milis, long interval) {
            super(milis, interval);
        }

        @Override
        public void onTick(long millis) {
            int minutes = (int) (millis / (1000 * 60)) % 60;
            int hours = (int) (millis / (1000 * 60 * 60)) % 24;
            int days = (int) (millis / (1000 * 60 * 60 * 24)) ;

            int progress = (int) (millis*100 / milis);

            progressBar.setProgress(progress);

            String txt = String.format("%2d days, %2d hours, %2d minutes", days, hours, minutes);

            timer.setText(txt);
        }

        @Override
        public void onFinish() {
            timer.setText("Time is over");
            progressBar.setProgress(0);
        }
    }

    private void reqJson(int groupId, int assignId, MultipartBody.Part file, RequestBody text) {
        TugasInterface service = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(TugasInterface.class);
        Call<SubmitModel> call = service.submitTugas(groupId, assignId, text, file);
        call.enqueue(new Callback<SubmitModel>() {
            @Override
            public void onResponse(Call<SubmitModel> call, Response<SubmitModel> response) {
                Log.d("respon", "onResponse: submit " + response.code());
                Toast.makeText(getApplicationContext(), "Tugas Anda Sudah Dikirim", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SubmitModel> call, Throwable t) {
                Log.e("respon", "onFailure: ", t);
                Toast.makeText(getApplicationContext(), "Upload Gagal,Coba Lagi", Toast.LENGTH_LONG).show();
            }
        });
    }
}
