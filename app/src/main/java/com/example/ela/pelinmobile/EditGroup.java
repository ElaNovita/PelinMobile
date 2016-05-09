package com.example.ela.pelinmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.GroupInterface;
import com.example.ela.pelinmobile.Model.GroupModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e on 6/05/16.
 */
public class EditGroup extends AppCompatActivity {
    EditText title, kelas, semester, jurusan, desc;
    Button save;
    int groupId;
    String groupTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_group);

        groupId = getIntent().getIntExtra("groupId", 0);
        groupTitle = getIntent().getStringExtra("groupTitle");

        title = (EditText) findViewById(R.id.group_name);
        kelas = (EditText) findViewById(R.id.group_classes);
        semester = (EditText) findViewById(R.id.group_semester);
        jurusan = (EditText) findViewById(R.id.jurusan);
        desc = (EditText) findViewById(R.id.group_desc);
        save = (Button) findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reqJson();
            }
        });

    }

    private void reqJson() {

        GroupInterface service = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(GroupInterface.class);

        GroupModel model = new GroupModel();

        final String titles = title.getText().toString();
        model.setTitle(title.getText().toString());
        model.setDescription(desc.getText().toString());
        model.setSemester(Integer.parseInt(semester.getText().toString()));
        model.setMajor(jurusan.getText().toString());

        Call<GroupModel> call = service.editGroup(groupId, model);
        call.enqueue(new Callback<GroupModel>() {
            @Override
            public void onResponse(Call<GroupModel> call, Response<GroupModel> response) {
                Log.d("respon", "onResponse: res " + response.code());
                Intent intent = new Intent(getApplicationContext(), GroupDetail.class);
                intent.putExtra("groupId", groupId);
                intent.putExtra("groupTitle", titles);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<GroupModel> call, Throwable t) {

            }
        });

    }
}
