package com.example.ela.pelinmobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.UserInterface;
import com.example.ela.pelinmobile.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e on 2/05/16.
 */
public class EditProfile extends AppCompatActivity {

    EditText name, email, phone, passowrd;
    Button save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.mail);
        phone = (EditText) findViewById(R.id.phone);
        passowrd = (EditText) findViewById(R.id.password);
        save = (Button) findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reqJson();
            }
        });

    }

    public void reqJson() {

        final User user = new User();

        user.setName(name.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(email.getText().toString());


        UserInterface service = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(UserInterface.class);
        Call<User> call = service.editUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                Log.d("respon", "onResponse: res " + response.code());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
