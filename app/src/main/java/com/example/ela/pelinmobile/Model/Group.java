package com.example.ela.pelinmobile.Model;

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
        datas.add(new Group("Nama Group 2", "Nama Dosen 2", 39));
        datas.add(new Group("Nama Group 3", "Nama Dosen 3", 45));
        datas.add(new Group("Nama Group 1", "Nama Dosen 1", 15));
        datas.add(new Group("Nama Group 2", "Nama Dosen 2", 39));
        datas.add(new Group("Nama Group 3", "Nama Dosen 3", 45));
        datas.add(new Group("Nama Group 1", "Nama Dosen 1", 15));
        datas.add(new Group("Nama Group 2", "Nama Dosen 2", 39));
        datas.add(new Group("Nama Group 3", "Nama Dosen 3", 45));
        datas.add(new Group("Nama Group 1", "Nama Dosen 1", 15));
        datas.add(new Group("Nama Group 2", "Nama Dosen 2", 39));
        datas.add(new Group("Nama Group 3", "Nama Dosen 3", 45));

        return datas;
    }


}
