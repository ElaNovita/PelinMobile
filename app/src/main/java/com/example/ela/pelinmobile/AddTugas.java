package com.example.ela.pelinmobile;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.TugasInterface;
import com.example.ela.pelinmobile.Model.TugasModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e on 1/04/16.
 */
public class AddTugas extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    TextView TextuploadMateri, txtTime, txtDate;
    Button btnSendMateri, upMateri, dueTime, dueDate;
    ImageView attach, attach1, attach2;
    private static final int PICKFILE_RESULT_CODE = 1;
    MultipartBody.Part requestFileBody;
    EditText title, description;
    String tugasTitle, desc, groupTitle;
    RequestBody titles, descriptions, iso;
    int groupId;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tugas);

        groupId = getIntent().getIntExtra("groupId", 0);
        groupTitle = getIntent().getStringExtra("groupTitle");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextuploadMateri = (TextView) findViewById(R.id.uploadMateri);
        txtTime = (TextView) findViewById(R.id.dueTime);
        txtDate = (TextView) findViewById(R.id.dueDate);
        attach = (ImageView) findViewById(R.id.attachIcon);
        attach1 = (ImageView) findViewById(R.id.attachIcon1);
        attach2 = (ImageView) findViewById(R.id.attachIcon2);

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

                TimeZone timeZone = calendar.getTimeZone();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
                dateFormat.setTimeZone(timeZone);
                String hasil = dateFormat.format(calendar.getTimeInMillis());
                Log.d("respon", "onDateSet: iso " + hasil);

                tugasTitle = title.getText().toString();
                desc = description.getText().toString();

                titles = RequestBody.create(MediaType.parse("multipart/form-data"), tugasTitle);
                descriptions = RequestBody.create(MediaType.parse("multipart/form-data"), desc);
                iso = RequestBody.create(MediaType.parse("multipart/form-data"), hasil);

                sendFile(requestFileBody, titles, descriptions, iso);

                Log.d("respon", "onClick: " + requestFileBody.toString());

                Intent intent = new Intent(getApplicationContext(), GroupDetail.class);
                intent.putExtra("groupId", groupId);
                intent.putExtra("groupTitle", groupTitle);
                startActivity(intent);
            }
        });

        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });

        dueTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime();
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

                    File file = new File(uri.getPath());

                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    requestFileBody = MultipartBody.Part.createFormData("file", file.getName(), requestFile);


                    btnSendMateri.setVisibility(View.VISIBLE);
                    Log.d("respon", "onActivityResult: " + file.toString());
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

    private void sendFile(MultipartBody.Part files, RequestBody titles, RequestBody descriptions, RequestBody due_date) {
        TugasInterface service = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(TugasInterface.class);
        Call<TugasModel> call = service.createTugas(groupId, files, titles, descriptions, due_date);
        call.enqueue(new Callback<TugasModel>() {
            @Override
            public void onResponse(Call<TugasModel> call, Response<TugasModel> response) {
                Log.d("respon", "onResponse: respon tugas  " + response.code());
                Toast.makeText(getApplicationContext(), "Tugas Sudah di Upload", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<TugasModel> call, Throwable t) {
                Log.e("respon", "onFailure: salah ", t);
                Toast.makeText(getApplicationContext(), "Tugas Gagal di Upload", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = Integer.toString(dayOfMonth) + "-" + Integer.toString(monthOfYear) + "-" + Integer.toString(year);
        txtDate.setText(date);

        calendar.set(year, monthOfYear , dayOfMonth);

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        //TODO fix the displayed time
        String time = Integer.toString(hourOfDay) + "-" + Integer.toString(minute);
        txtTime.setText(time);

        calendar.set(Calendar.HOUR, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

    }

    private void showDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        dialog.show(getFragmentManager(), "DatePickerDialog");
    }

    private void showTime() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog dialog = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                true
        );
        dialog.show(getFragmentManager(), "TimePickerDialog");
    }

}
