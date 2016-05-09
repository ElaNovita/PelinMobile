package com.example.ela.pelinmobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.UserInterface;
import com.example.ela.pelinmobile.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e on 1/05/16.
 */
public class UserDetail extends AppCompatActivity {

    int userId;
    TextView name, nim, status, major, email, phone, majorTxt;
    ImageView image, majorIcon;
    String _name, _nim, _status, _major, _email, _phone;
    boolean isTeacher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail);

        userId = getIntent().getIntExtra("userId", 0);

        reqJson();

        name = (TextView) findViewById(R.id.name);
        nim = (TextView) findViewById(R.id.nim);
        status = (TextView) findViewById(R.id.status);
        major = (TextView) findViewById(R.id.major);
        email = (TextView) findViewById(R.id.mail);
        phone = (TextView) findViewById(R.id.phone);
        majorTxt = (TextView) findViewById(R.id.majorTxt);
        majorIcon = (ImageView) findViewById(R.id.majorIcon);
        image = (ImageView) findViewById(R.id.img);
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

    private void reqJson() {

        UserInterface service = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(UserInterface.class);
        Call<User> call = service.getSingleUser(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();

                name.setText(user.getName());
                status.setText("I am a " + user.getStatus());

                if (user.isTeacher()) {
                    nim.setText(user.getTeacher().getNik());
                    major.setVisibility(View.GONE);
                    majorTxt.setVisibility(View.GONE);
                    majorIcon.setVisibility(View.GONE);
                } else {
                    nim.setText(user.getStudent().getNim());
                    major.setText(user.getStudent().getMajor());
                }

                if (user.getPhone() == null) {
                    phone.setText("No Info");
                } else {
                    phone.setText(user.getPhone());
                }

                if (user.getEmail() == null) {
                    email.setText("No Info");
                } else {
                    email.setText(user.getEmail());
                }

                if (user.getPhoto().getSmall() == null) {
                    image.setImageResource(R.drawable.eren);
                } else {
                    Glide.with(getApplicationContext()).load(user.getPhoto().getSmall()).into(image);
                }

                Log.d("respon", "onResponse: photo " + user.getPhoto().getSmall());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
