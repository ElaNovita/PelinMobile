package com.example.ela.pelinmobile.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ela.pelinmobile.Adapter.AssigntListAdapter;
import com.example.ela.pelinmobile.Adapter.GroupListAdapter;
import com.example.ela.pelinmobile.AssigntDetail;
import com.example.ela.pelinmobile.Fragment.GroupDetail.ListTugas;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.FragmentComunicator;
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
public class AssigntFragment extends Fragment {

    private List<TugasModel> tugasModels;
    String TAG = "respon";
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    View inflated;
    TextView failed;
    private FragmentComunicator comunicator;


    public AssigntFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        context = getActivity();
        comunicator = (FragmentComunicator) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflated = inflater.inflate(R.layout.fragment_assignt, container, false);
        recyclerView = (RecyclerView) inflated.findViewById(R.id.assigntRv);
        refreshLayout = (SwipeRefreshLayout) inflated.findViewById(R.id.swipeRefresh);
        failed = (TextView) inflated.findViewById(R.id.failed);

        startAnim();

        reqJson();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reqJson();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        return inflated;
    }

    public void startAnim() {
        inflated.findViewById(R.id.load).setVisibility(View.VISIBLE);
    }

    public void stopAnim() {
        inflated.findViewById(R.id.load).setVisibility(View.GONE);
    }

    public void reqJson() {
        TugasInterface tugasInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(TugasInterface.class);
        Call<List<TugasModel>> call = tugasInterface.getAllTugas();
        call.enqueue(new Callback<List<TugasModel>>() {
            @Override
            public void onResponse(Call<List<TugasModel>> call, Response<List<TugasModel>> response) {
                List<TugasModel> tugasModels = response.body();

                Log.d(TAG, "onResponse: size " + tugasModels.size());

                ArrayList<Integer> counter = new ArrayList<Integer>();



                for (TugasModel tugas : tugasModels) {
                    if (!tugas.isPassed()) {
                        counter.add(tugas.getId());
                    }
                }

                comunicator.sendDataToActivity(counter.size());

                AssigntListAdapter adapter = new AssigntListAdapter(tugasModels, getActivity(), new AssigntListAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(TugasModel tugasModel) {
                        Intent intent = new Intent(getActivity(), ListTugas.class);
                        startActivity(intent);
                    }
                });

                recyclerView.setAdapter(adapter);

                refreshLayout.setRefreshing(false);
                failed.setVisibility(View.GONE);
                stopAnim();

            }

            @Override
            public void onFailure(Call<List<TugasModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: ", t);

                refreshLayout.setRefreshing(false);
                failed.setVisibility(View.VISIBLE);
                stopAnim();
            }
        });
    }

}
