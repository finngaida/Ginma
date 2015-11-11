package de.ricoklimpel.ginma;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.style.TtsSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by ricoklimpel on 09.11.15.
 */


public class Fragment_GraphView_Data extends Fragment {

    View contentView_graphview_data;
    static Context thiscontext;
    RecyclerView RV_Graph_Data;
    RecyclerView.Adapter RVA_Graph_Data;
    RecyclerView.LayoutManager RVLM_Graph_Data;

    static ArrayList<String> items_notes;
    static ArrayList<Date> items_dates;
    static ArrayList<Double> items_values;


    FloatingActionButton FAB_addData;

    static Date add_FullDate;
    static Date add_Date;
    static Time add_Time;
    static String add_note;
    static Double add_value;

    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView_graphview_data = inflater.inflate(R.layout.fragment_graphview_data, null);

        thiscontext = getActivity().getApplicationContext();

        return contentView_graphview_data;
    }


    static Date date;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        items_dates = new ArrayList<>();
        items_notes = new ArrayList<>();
        items_values = new ArrayList<>();


        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy HH:mm");

        try {
            date = format.parse("11.03.14 09:33");
        } catch (ParseException e) {
            e.printStackTrace();
        }



        items_dates.add(date);
        items_notes.add("Klausur");
        items_values.add(13.0);

        items_dates.add(date);
        items_notes.add("Mündliche Note");
        items_values.add(9.0);


        FAB_addData = (FloatingActionButton)contentView_graphview_data.findViewById(R.id.FAB_addData);

        FAB_addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_Data();

            }
        });



        RV_Graph_Data = (RecyclerView)contentView_graphview_data.findViewById(R.id.RV_GraphView_Data);
        RVLM_Graph_Data = new LinearLayoutManager(this.getActivity());
        RV_Graph_Data.setLayoutManager(RVLM_Graph_Data);

        RVA_Graph_Data = new RVA_graphView_data();
        RV_Graph_Data.setAdapter(RVA_Graph_Data);


    }


    private void add_Data() {
        FAB_addData.getColorPressed();
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
    }
}
