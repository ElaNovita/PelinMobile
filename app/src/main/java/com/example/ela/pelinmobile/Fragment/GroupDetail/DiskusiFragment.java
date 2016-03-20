package com.example.ela.pelinmobile.Fragment.GroupDetail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ela.pelinmobile.Adapter.DiskusiAdapter;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiskusiFragment extends Fragment {

    private List<Diskusi> diskusis;

    public DiskusiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflated = inflater.inflate(R.layout.fragment_diskusi, container, false);
        RecyclerView recyclerView = (RecyclerView) inflated.findViewById(R.id.dRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DiskusiAdapter adapter = new DiskusiAdapter(diskusis);
        recyclerView.setAdapter(adapter);

        return inflated;
    }

    public class Diskusi {
        public String sender, dSendAt, dContent, dLike, dCount;
        public int dImgSender;

        public Diskusi(String dSender, String dSendAt, String dContent, String dLike, String dCount, int dImgSender) {
            this.sender = dSender;
            this.dSendAt = dSendAt;
            this.dContent = dContent;
            this.dLike = dLike;
            this.dCount = dCount;
            this.dImgSender = dImgSender;
        }
    }

    private void initData() {
        diskusis = new ArrayList<>();
        diskusis.add(new Diskusi("Akashi Seijuro", "10.23 AM", "Know Your Place", "25", "12", R.drawable.sei));
        diskusis.add(new Diskusi("Levi Ackerman", "Sun 05.02 PM", "Clean the room brat!", "7", "27", R.drawable.levi));
        diskusis.add(new Diskusi("Akashi Seijuro", "Fri 07.12 AM", "Iam absolute", "10", "23", R.drawable.sei));
        diskusis.add(new Diskusi("Haruka Nanase", "Thur 06.45 AM", "What about mackarel?", "17", "16", R.drawable.haruka));
        diskusis.add(new Diskusi("Haruka Nanase", "Thur 06.45 AM", "What about mackarel?", "17", "16", R.drawable.haruka));
        diskusis.add(new Diskusi("Haruka Nanase", "Thur 06.45 AM", "What about mackarel?", "17", "16", R.drawable.haruka));
        diskusis.add(new Diskusi("Haruka Nanase", "Thur 06.45 AM", "What about mackarel?", "17", "16", R.drawable.haruka));
        diskusis.add(new Diskusi("Haruka Nanase", "Thur 06.45 AM", "What about mackarel?", "17", "16", R.drawable.haruka));
        diskusis.add(new Diskusi("Haruka Nanase", "Thur 06.45 AM", "What about mackarel?", "17", "16", R.drawable.haruka));
        diskusis.add(new Diskusi("Haruka Nanase", "Thur 06.45 AM", "What about mackarel?", "17", "16", R.drawable.haruka));
    }

}
