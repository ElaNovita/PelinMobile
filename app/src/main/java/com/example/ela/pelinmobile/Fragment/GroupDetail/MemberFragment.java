package com.example.ela.pelinmobile.Fragment.GroupDetail;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ela.pelinmobile.Adapter.MemberAdapter;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.MemberInterface;
import com.example.ela.pelinmobile.Interface.RequestInterface;
import com.example.ela.pelinmobile.Model.ApproveModel;
import com.example.ela.pelinmobile.Model.MateriModel;
import com.example.ela.pelinmobile.Model.MemberModel;
import com.example.ela.pelinmobile.Profile;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberFragment extends Fragment {

    List<MemberModel> members;
    Button kick;
    LinearLayout wrap;
    RecyclerView recyclerView;
    MemberAdapter adapter;
    int groupId;
    String nim;


    public MemberFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groupId = getArguments().getInt("groupId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflated = inflater.inflate(R.layout.fragment_member, container, false);

        kick = (Button) inflated.findViewById(R.id.kick);
        wrap = (LinearLayout) inflated.findViewById(R.id.wrap);

        recyclerView = (RecyclerView) inflated.findViewById(R.id.memberRv);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        final MemberInterface memberInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(MemberInterface.class);

        Call<List<MemberModel>> call = memberInterface.getMembers(groupId);
        call.enqueue(new Callback<List<MemberModel>>() {
            @Override
            public void onResponse(Call<List<MemberModel>> call, Response<List<MemberModel>> response) {
                try {
                    final List<MemberModel> memberModels = response.body();

                    Log.d("respon", "onResponse: res " + response.body());



                    adapter = new MemberAdapter(getActivity(), memberModels);

                    adapter.setOnItemClickListener(new MemberAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, final int position, boolean isLongClick) {
                            if (isLongClick) {
                                kick.setText("Delete " + memberModels.get(position).getName().toLowerCase() + "?");
                                kick.setVisibility(View.VISIBLE);
                                nim = "1210520070";
                                kick.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        kick();
                                        adapter.removeItem(position);
                                    }
                                });
                            } else {
                                Intent intent = new Intent(getActivity(), Profile.class);
                                startActivity(intent);
                            }
                        }
                    });

                    recyclerView.setAdapter(adapter);

                } catch (Exception e) {
                    Log.e("respon", "onResponse: ", e);
                }
            }

            @Override
            public void onFailure(Call<List<MemberModel>> call, Throwable t) {

            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.member);

        return inflated;
    }

    private void kick() {
        RequestInterface service = new RetrofitBuilder(getActivity()).getRetrofit().create(RequestInterface.class);
        Call<ApproveModel> call = service.kick(4, nim);
        call.enqueue(new Callback<ApproveModel>() {
            @Override
            public void onResponse(Call<ApproveModel> call, Response<ApproveModel> response) {
                Log.d("respon", "onResponse: kick " + response.code());
            }

            @Override
            public void onFailure(Call<ApproveModel> call, Throwable t) {

            }
        });
    }

}
