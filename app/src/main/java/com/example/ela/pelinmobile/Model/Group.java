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
        datas.add(new Group("ANALISIS & DESAIN PERANGAKAT LUNAK", "Ahmat Adil, S.kom.,M.Sc.", 15));
        datas.add(new Group("JARINGAN KOMPUTER ", "Raisul Azhar,S.T., M.T.", 15));
        datas.add(new Group("PEMROGRAMAN II", "Dwi Ratnasari, MT", 15));
        datas.add(new Group("ATRIFICIAL INTELEGENCE ", "Khasnur Hidjah, S.Kom.,M.Cs", 15));
        datas.add(new Group("METODE NUMERIK ", "Uswatun H, M.Si", 15));
        datas.add(new Group("GRAFIKA KOMPUTER", "Sarifah fitriana M.Sn", 15));
        datas.add(new Group("ALJABAR LINIER", "Budiman Sani, M.Pd", 15));


        return datas;
    }

    public String getTitle() {
        return title;
    }


}
