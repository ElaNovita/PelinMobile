package com.example.ela.pelinmobile.Fragment.GroupDetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ela.pelinmobile.GroupDetail;
import com.example.ela.pelinmobile.Helper.CustomDateFormatter;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.TugasInterface;
import com.example.ela.pelinmobile.Model.SubmitModel;
import com.example.ela.pelinmobile.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e on 13/05/16.
 */
public class EditTugas extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    String title, desc, due, file;
    EditText txtTitle, txtDesc;
    TextView txtFileName, txtDate, txtTime, dueDate;
    Button selectFile, selectDate, selectTime, send;
    CustomDateFormatter cdf = new CustomDateFormatter();
    MultipartBody.Part requestFileBody;
    RequestBody _title, _desc, _due;
    private static final int PICKFILE_RESULT_CODE = 1;
    Calendar calendar = Calendar.getInstance();
    int groupId, assignId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_tugas);

        txtTitle = (EditText) findViewById(R.id.assignTitle);
        txtDesc = (EditText) findViewById(R.id.desc);
        txtFileName = (TextView) findViewById(R.id.file_name);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtTime = (TextView) findViewById(R.id.txtTime);
        send = (Button) findViewById(R.id.send);
        selectFile = (Button) findViewById(R.id.select_file);
        selectDate = (Button) findViewById(R.id.selectDate);
        selectTime = (Button) findViewById(R.id.selectTime);
        dueDate = (TextView) findViewById(R.id.dueDate);

        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        due = getIntent().getStringExtra("due");
        file = getIntent().getStringExtra("file");
        groupId = getIntent().getIntExtra("groupId", 0);
        assignId = getIntent().getIntExtra("assignId", 0);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        requestFileBody = MultipartBody.Part.createFormData("files", file, requestFile);

        txtTitle.setText(title);
        txtDesc.setText(desc);
        try {
            dueDate.setText(cdf.format(due));
        } catch (ParseException e) {
            //
        }
        txtFileName.setText(file);

        _title = RequestBody.create(MediaType.parse("multipart/form-data"), title);
        _desc = RequestBody.create(MediaType.parse("multipart/form-data"), desc);
        _due = RequestBody.create(MediaType.parse("multipart/form-data"), due);

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, PICKFILE_RESULT_CODE);
            }
        });

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });

        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimeZone timeZone = calendar.getTimeZone();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
                dateFormat.setTimeZone(timeZone);
                String hasil = dateFormat.format(calendar.getTimeInMillis());
                Log.d("respon", "onDateSet: iso " + hasil);

                title = txtTitle.getText().toString();
                desc = txtDesc.getText().toString();

                _title = RequestBody.create(MediaType.parse("multipart/form-data"), title);
                _desc = RequestBody.create(MediaType.parse("multipart/form-data"), desc);
                _due = RequestBody.create(MediaType.parse("multipart/form-data"), hasil);

                reqJson(groupId, assignId);

                Intent intent = new Intent(getApplicationContext(), GroupDetail.class);
                intent.putExtra("groupId", groupId);
                intent.putExtra("groupTitle", getIntent().getStringExtra("groupTitle"));
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

                    txtFileName.setText(name);

                    Uri uri = data.getData();

                    Log.d("respon", "onActivityResult: " + uri.toString());


                    File file = new File(uri.getPath());

                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    requestFileBody = MultipartBody.Part.createFormData("files", file.getName(), requestFile);


                    send.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void reqJson(int groupId, int assignId) {
        TugasInterface service = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(TugasInterface.class);
        Call<SubmitModel> call = service.editTugas(groupId, assignId, requestFileBody, _title, _desc, _due);
        call.enqueue(new Callback<SubmitModel>() {
            @Override
            public void onResponse(Call<SubmitModel> call, Response<SubmitModel> response) {
                Log.d("respon", "onResponse: res " + response.code());
            }

            @Override
            public void onFailure(Call<SubmitModel> call, Throwable t) {

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

        //TODO why get wrong time?
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
