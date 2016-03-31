package com.example.ela.pelinmobile.Model;

import android.graphics.Color;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ela on 18/03/16.
 */
public class Group {
    public String title, name;
    public int count;

    Group(String title, String name, int count) {
        this.title = title;
        this.name = name;
        this.count = count;

    }

    public static List<Group> initData() {

        List<Group> datas;
        datas = new ArrayList<>();
        datas.add(new Group("Nama Group 1", "Nama Dosen 1", 15));
        datas.add(new Group("Sama Group 1", "Nama Dosen 1", 15));
        datas.add(new Group("Dama Group 1", "Nama Dosen 1", 15));
        datas.add(new Group("Yama Group 1", "Nama Dosen 1", 15));
        datas.add(new Group("Kama Group 1", "Nama Dosen 1", 15));
        datas.add(new Group("Lama Group 1", "Nama Dosen 1", 15));
        datas.add(new Group("Jama Group 1", "Nama Dosen 1", 15));


        return datas;
    }


}
