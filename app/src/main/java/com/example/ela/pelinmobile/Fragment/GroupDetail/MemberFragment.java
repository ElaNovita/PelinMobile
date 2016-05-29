package com.example.ela.pelinmobile.Fragment.GroupDetail;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ela.pelinmobile.Adapter.ConfirmAdapter;
import com.example.ela.pelinmobile.Adapter.MemberAdapter;
import com.example.ela.pelinmobile.Fragment.InviteDialog;
import com.example.ela.pelinmobile.Helper.MySharedPreferences;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.MemberInterface;
import com.example.ela.pelinmobile.Interface.RequestInterface;
import com.example.ela.pelinmobile.Model.ApproveModel;
import com.example.ela.pelinmobile.Model.MemberModel;
import com.example.ela.pelinmobile.Model.RequestModel;
import com.example.ela.pelinmobile.Profile;
import com.example.ela.pelinmobile.OtherProfile;
import com.example.ela.pelinmobile.R;

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
    MemberAdapter adapter;
    int groupId;
    RecyclerView recyclerView, rv;
    Button confirmAll;
    FloatingActionButton fab;
    TextView fail;
    View inflated;
    LinearLayout linearLayout;
    boolean isTeacher;


    public MemberFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groupId = getArguments().getInt("groupId");

        MySharedPreferences mf = new MySharedPreferences(getActivity());
        isTeacher = mf.getStatus();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflated = inflater.inflate(R.layout.fragment_member, container, false);

        startAnim();

        kick = (Button) inflated.findViewById(R.id.kick);
        wrap = (LinearLayout) inflated.findViewById(R.id.wrap);
        fail = (TextView) inflated.findViewById(R.id.failed);


        confirmAll = (Button) inflated.findViewById(R.id.confirm_all_btn);
        fab = (FloatingActionButton) inflated.findViewById(R.id.invite);
        recyclerView = (RecyclerView) inflated.findViewById(R.id.memberRv);
        rv = (RecyclerView) inflated.findViewById(R.id.confirmRv);
        linearLayout = (LinearLayout) inflated.findViewById(R.id.req);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        reqJoin();
        reqMembers();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        fail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnim();
                reqJoin();
                reqMembers();
            }
        });

        return inflated;
    }

    private void kick(String nim) {
        RequestInterface service = new RetrofitBuilder(getActivity()).getRetrofit().create(RequestInterface.class);
        Call<ApproveModel> call = service.kick(groupId, nim);
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

    private void reqJoin() {
        RequestInterface requestInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(RequestInterface.class);

        Call<List<RequestModel>> call = requestInterface.getPendingUsers(groupId);
        call.enqueue(new Callback<List<RequestModel>>() {
            @Override
            public void onResponse(Call<List<RequestModel>> call, Response<List<RequestModel>> response) {
                List<RequestModel> requestModels = response.body();

                if (requestModels == null) {
                    confirmAll.setVisibility(View.GONE);
                }

                ConfirmAdapter adapters = new ConfirmAdapter(requestModels, getActivity(), groupId);
                rv.setAdapter(adapters);
                Log.d("respon", "onResponse: kode " + response.code());
                fail.setVisibility(View.GONE);
                stopAnim();
            }

            @Override
            public void onFailure(Call<List<RequestModel>> call, Throwable t) {
                fail.setVisibility(View.VISIBLE);
                stopAnim();
            }
        });

        confirmAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmAll();
            }
        });
    }

    private void confirmAll() {
        RequestInterface requestInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(RequestInterface.class);
        Call<ApproveModel> call = requestInterface.confirmAll(groupId);
        call.enqueue(new Callback<ApproveModel>() {
            @Override
            public void onResponse(Call<ApproveModel> call, Response<ApproveModel> response) {
                Log.d("respon", "onResponse: all " + response.code());
            }

            @Override
            public void onFailure(Call<ApproveModel> call, Throwable t) {

            }
        });
    }

    private void showDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        InviteDialog dialog = InviteDialog.newInstance("Enter Username");
        dialog.show(fragmentManager, "Enter Username");
    }

    private void reqMembers() {
        MemberInterface memberInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(MemberInterface.class);
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

                                if (isTeacher) {
                                    kick.setText("Delete " + memberModels.get(position).getName().toLowerCase() + "?");
                                    kick.setVisibility(View.VISIBLE);
                                    final String nim = memberModels.get(position).getStudent().getNim();
                                    kick.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            kick(nim);
                                            adapter.removeItem(position);
                                        }
                                    });
                                } else {
                                    Toast.makeText(getActivity(), memberModels.get(position).getName(), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                if (memberModels.get(position).isMe()) {
                                    Intent intent = new Intent(getActivity(), Profile.class);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(getActivity(), OtherProfile.class);
                                    intent.putExtra("userId", memberModels.get(position).getId());
                                    startActivity(intent);
                                }
                            }
                        }
                    });

                    fail.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);
                    stopAnim();

                } catch (Exception e) {
                    Log.e("respon", "onResponse: ", e);
                    fail.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<MemberModel>> call, Throwable t) {
                stopAnim();
            }
        });
    }

    public void startAnim() {
        inflated.findViewById(R.id.load).setVisibility(View.VISIBLE);
    }

    public void stopAnim() {
        inflated.findViewById(R.id.load).setVisibility(View.GONE);
    }
}
