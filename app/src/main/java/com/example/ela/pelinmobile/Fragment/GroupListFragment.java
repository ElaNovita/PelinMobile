package com.example.ela.pelinmobile.Fragment;



import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.ela.pelinmobile.Adapter.GroupListAdapter;
import com.example.ela.pelinmobile.AllGroups;
import com.example.ela.pelinmobile.EditGroup;
import com.example.ela.pelinmobile.GroupDetail;
import com.example.ela.pelinmobile.Helper.MySharedPreferences;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.HomeDosen;
import com.example.ela.pelinmobile.Interface.GroupInterface;
import com.example.ela.pelinmobile.Interface.MyGroups;
import com.example.ela.pelinmobile.Model.Group;
import com.example.ela.pelinmobile.Model.GroupModel;
import com.example.ela.pelinmobile.OnItemClickListener;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupListFragment extends Fragment {
    RecyclerView groupRv;
    private FragmentActivity myContenxt;
    @Bind(R.id.tablayout)
    TabLayout tabLayout;
    RecyclerView recyclerView;
    ArrayList<Integer> groupId;
    String TAG = "respon";
    View inflated;
    TextView failed, nogroup, cancel;
    SwipeRefreshLayout refreshLayout;
    LinearLayout kick;
    GroupListAdapter adapter;
    ImageView delete, edit;
    boolean isTeacher;

    private List<GroupModel> groups;

    public GroupListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySharedPreferences mf = new MySharedPreferences(getActivity());
        isTeacher = mf.getStatus();
        Log.d(TAG, "onCreate: status " + isTeacher);

//        stopAnim();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflated = inflater.inflate(R.layout.fragment_group_list, container, false);
        recyclerView = (RecyclerView) inflated.findViewById(R.id.groupRv);
        failed = (TextView) inflated.findViewById(R.id.failed);
        refreshLayout = (SwipeRefreshLayout) inflated.findViewById(R.id.swipeRefresh);
        kick = (LinearLayout) inflated.findViewById(R.id.kick);
        nogroup = (TextView) inflated.findViewById(R.id.nogroup);
        delete = (ImageView) inflated.findViewById(R.id.delete);
        edit = (ImageView) inflated.findViewById(R.id.edit);
        cancel = (TextView) inflated.findViewById(R.id.cancel);


        reqJson();


        FloatingActionButton fab = (FloatingActionButton) inflated.findViewById(R.id.addGroup);

        if (isTeacher) {
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.GONE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reqJson();
            }
        });

        recyclerView.setLayoutManager(linearLayoutManager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();

            }
        });

        failed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                failed.setVisibility(View.GONE);
                reqJson();
            }
        });

        return inflated;
    }

    public void showDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        CreateGroupDialog createGroupDialog = CreateGroupDialog.newInstance("Create New Group");
        createGroupDialog.show(fragmentManager, "title");
    }

    public void startAnim() {
        inflated.findViewById(R.id.load).setVisibility(View.VISIBLE);
    }

    public void stopAnim() {
        inflated.findViewById(R.id.load).setVisibility(View.GONE);
    }

    public void reqJson() {
        MyGroups groupInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(MyGroups.class);
        Call<List<GroupModel>> call = groupInterface.getMyGroups();

        startAnim();

        call.enqueue(new Callback<List<GroupModel>>() {

            @Override
            public void onResponse(Call<List<GroupModel>> call, Response<List<GroupModel>> response) {
                try {
                    final List<GroupModel> groups = response.body();

//                    Log.d(TAG, "onResponse: " + groups.get(2).getId());
                    if (groups.size() == 0) {
                        nogroup.setVisibility(View.VISIBLE);
                    } else {
                        nogroup.setVisibility(View.GONE);
                    }

                    groupId = new ArrayList<Integer>();


                    for (GroupModel group : groups) {

                        groupId.add(group.getId());

                    }



                    Log.d(TAG, "onResponse: " + groupId);


                    adapter = new GroupListAdapter(groups, new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, final int position, boolean isLongClick) {
                            final int groupId = groups.get(position).getId();
                            if (isLongClick) {
                                if (isTeacher) {
                                    kick.setVisibility(View.VISIBLE);
                                    delete.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DeleteGroup(groupId);
                                            adapter.removeItem(position);
                                            kick.setVisibility(View.GONE);
                                        }
                                    });
                                    edit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(getActivity(), EditGroup.class);
                                            intent.putExtra("groupId", groupId);
                                            intent.putExtra("groupTitle", groups.get(position).getTitle());
                                            intent.putExtra("semester", groups.get(position).getSemester());
                                            intent.putExtra("jurusan", groups.get(position).getMajor());
                                            startActivity(intent);
                                            kick.setVisibility(View.GONE);
                                        }
                                    });
                                    cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            kick.setVisibility(View.GONE);
                                        }
                                    });
                                } else {
                                    Toast.makeText(getActivity(), groups.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Intent intent = new Intent(getActivity(), GroupDetail.class);
                                intent.putExtra("groupId", groupId);
                                intent.putExtra("groupTitle", groups.get(position).getTitle());
                                intent.putExtra("semester", groups.get(position).getSemester());
                                intent.putExtra("jurusan", groups.get(position).getMajor());
                                intent.putExtra("owner", groups.get(position).isOwner());
                                startActivity(intent);
                                Log.d(TAG, "onItemClick: id " + groupId);
                            }


                        }
                    });

                    recyclerView.setAdapter(adapter);

                    refreshLayout.setRefreshing(false);
                    stopAnim();
                    failed.setVisibility(View.GONE);

                } catch (Exception e) {
                    Log.e("respon", "onResponse: error", e);
                }
            }

            @Override
            public void onFailure(Call<List<GroupModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                refreshLayout.setRefreshing(false);
                stopAnim();
                failed.setVisibility(View.VISIBLE);

            }
        });
    }

    private void DeleteGroup(int groupId) {
        GroupInterface service = new RetrofitBuilder(getActivity()).getRetrofit().create(GroupInterface.class);
        Call<ResponseBody> call = service.deleteGroup(groupId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                reqJson();
                Toast.makeText(getActivity(), "Group Berhasil di Hapus", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}
