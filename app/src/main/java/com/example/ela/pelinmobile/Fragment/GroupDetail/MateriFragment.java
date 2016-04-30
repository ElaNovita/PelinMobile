package com.example.ela.pelinmobile.Fragment.GroupDetail;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ela.pelinmobile.Adapter.MateriAdapter;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.MateriInterface;
import com.example.ela.pelinmobile.Model.MateriModel;
import com.example.ela.pelinmobile.R;
import com.example.ela.pelinmobile.UploadMateri;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MateriFragment extends Fragment {

    int groupId, materiId;
    RecyclerView recyclerView;
    MateriAdapter adapter;
    Button delete;


    public MateriFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groupId = getArguments().getInt("groupId");
        reqJson();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflated =  inflater.inflate(R.layout.fragment_materi, container, false);

        delete = (Button) inflated.findViewById(R.id.delete);
        recyclerView = (RecyclerView) inflated.findViewById(R.id.materiRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.materi);

        FloatingActionButton floatingActionButton = (FloatingActionButton) inflated.findViewById(R.id.addMateri);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadMateri.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
            }
        });

        return inflated;
    }

    private void uploadFile(Uri fileUri) {

        MateriInterface materiInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(MateriInterface.class);

    }

    private void reqJson() {
        MateriInterface materiInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(MateriInterface.class);

        Call<List<MateriModel>> call = materiInterface.getMateri(groupId);
        call.enqueue(new Callback<List<MateriModel>>() {
            @Override
            public void onResponse(Call<List<MateriModel>> call, Response<List<MateriModel>> response) {
                try {
                    final List<MateriModel> materiModels = response.body();

                    adapter = new MateriAdapter(materiModels);

                    adapter.setOnItemClickListener(new MateriAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(View view, int position, boolean isLongClick) {
                            materiId = materiModels.get(position).getId();
                            if (isLongClick) {
                                delete.setVisibility(View.VISIBLE);
                                delete.setText("Delete " + materiModels.get(position).getTitle() + "?");
                                delete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        deleteMateri();
                                    }
                                });
                            }

                        }
                    });

                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    Log.e("respon", "onResponse: ", e);
                }
            }

            @Override
            public void onFailure(Call<List<MateriModel>> call, Throwable t) {
                Log.e("respon", "onFailure: ", t);
            }
        });
    }

    private void deleteMateri() {
        MateriInterface materiInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(MateriInterface.class);
        Call<ResponseBody> call = materiInterface.deleteMateri(groupId, materiId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                delete.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Deleting...", Toast.LENGTH_SHORT).show();
                reqJson();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
