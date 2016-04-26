package com.example.ela.pelinmobile.Fragment.GroupDetail;


import android.content.Intent;
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

import com.example.ela.pelinmobile.Adapter.TugasAdapter;
import com.example.ela.pelinmobile.AddTugas;
import com.example.ela.pelinmobile.AssigntDetail;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.TugasInterface;
import com.example.ela.pelinmobile.Model.TugasModel;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TugasFragment extends Fragment {

    private List<TugasModel> tugases;
    RecyclerView recyclerView;
    Bundle bundle;
    int groupId;

    public TugasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groupId = getArguments().getInt("groupId");


        TugasInterface tugasInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(TugasInterface.class);
        Call<List<TugasModel>> call = tugasInterface.getTugas(groupId);
        call.enqueue(new Callback<List<TugasModel>>() {
            @Override
            public void onResponse(Call<List<TugasModel>> call, Response<List<TugasModel>> response) {
                try {
                    List<TugasModel> tugasModels = response.body();

                    TugasAdapter adapter = new TugasAdapter(tugasModels, new TugasAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(TugasModel tugas) {
                            Intent intent = new Intent(getActivity(), AssigntDetail.class);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    Log.e("respon", "onResponse: ", e);
                }
            }

            @Override
            public void onFailure(Call<List<TugasModel>> call, Throwable t) {
                Log.e("respon", "onFailure: ", t);
            }
        });

        bundle = new Bundle();
        bundle.putInt("groupId", groupId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflated = inflater.inflate(R.layout.fragment_tugas, container, false);
        recyclerView = (RecyclerView) inflated.findViewById(R.id.tugastRv);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.tugas);
        FloatingActionButton floatingActionButton = (FloatingActionButton) inflated.findViewById(R.id.addTugas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTugas.class);
                startActivity(intent);
            }
        });

        return inflated;
    }

}
