package com.example.ela.pelinmobile.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ela.pelinmobile.Adapter.AssigntListAdapter;
import com.example.ela.pelinmobile.Adapter.NotifListAdapter;
import com.example.ela.pelinmobile.Fragment.GroupDetail.ConfirmMember;
import com.example.ela.pelinmobile.GroupDetail;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.NotifInterface;
import com.example.ela.pelinmobile.Model.MarkReadModel;
import com.example.ela.pelinmobile.Model.NotifModel;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotifFragment extends Fragment {
    RecyclerView recyclerView;


    public NotifFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflated = inflater.inflate(R.layout.fragment_notif, container, false);
        recyclerView = (RecyclerView) inflated.findViewById(R.id.notifRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        reqJson();

        return inflated;
    }

    private void reqJson() {
        NotifInterface service = new RetrofitBuilder(getActivity()).getRetrofit().create(NotifInterface.class);
        Call<List<NotifModel>> call = service.listNotif();
        call.enqueue(new Callback<List<NotifModel>>() {
            @Override
            public void onResponse(Call<List<NotifModel>> call, Response<List<NotifModel>> response) {
                final List<NotifModel> notifModels = response.body();

                NotifListAdapter adapter = new NotifListAdapter(notifModels, getActivity(), new NotifListAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(NotifModel notif, int position) {
                        Intent intent = new Intent(getActivity(), GroupDetail.class);
                        intent.putExtra("groupId", notifModels.get(position).getTarget().getId());
                        intent.putExtra("groupTitle", notifModels.get(position).getTarget().getTitle());
                        startActivity(intent);
                    }
                });

                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<NotifModel>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        NotifInterface service = new RetrofitBuilder(getActivity()).getRetrofit().create(NotifInterface.class);
        Call<MarkReadModel> call = service.markRead();
        call.enqueue(new Callback<MarkReadModel>() {
            @Override
            public void onResponse(Call<MarkReadModel> call, Response<MarkReadModel> response) {
                reqJson();
                Log.d("respon", "onResponse: kode " + response.code());
            }

            @Override
            public void onFailure(Call<MarkReadModel> call, Throwable t) {
                Log.e("respon", "onFailure: ", t);
            }
        });
    }
}
