package de.ricoklimpel.ginma;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.rengwuxian.materialedittext.MaterialEditText;

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
    static String add_Date;
    static String add_Time;
    static String add_note;
    static Double add_value;

    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;

    Calendar calendar;

    Button btn_AD_addData;
    MaterialEditText eT_AD_addNote;
    MaterialEditText eT_AD_addValue;


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
                RVA_Graph_Data.notifyDataSetChanged();
                RV_Graph_Data.smoothScrollToPosition(items_notes.size());

            }
        });






    }


    private void add_Data() {

        calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                add_Time = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);

                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        add_Date = String.valueOf(dayOfMonth)+"."
                                +String.valueOf(monthOfYear)+"."
                                +String.valueOf(year);


                        add_ValueandNote();

                    }
                },calendar.get(Calendar.YEAR)
                ,calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();

            }
        }, calendar.get(Calendar.HOUR_OF_DAY)
                ,calendar.get(Calendar.MINUTE)
                ,DateFormat.is24HourFormat(getActivity()));

        timePickerDialog.show();
    }


    
    private void add_ValueandNote() {

        LayoutInflater AD_inflater = getActivity().getLayoutInflater();
        View AD_View = AD_inflater.inflate(R.layout.ad_adddata_graphview,null);

        final AlertDialog.Builder AD_addDataBuilder = new AlertDialog.Builder(getActivity());
        AD_addDataBuilder.setTitle("Neuen Datensatz hinzufügen");
        AD_addDataBuilder.setView(AD_View);

        btn_AD_addData = (Button)AD_View.findViewById(R.id.btn_AD_addData);
        eT_AD_addNote = (MaterialEditText)AD_View.findViewById(R.id.eT_Add_Note);
        eT_AD_addValue = (MaterialEditText)AD_View.findViewById(R.id.eT_AddData);

        final AlertDialog AD_addData = AD_addDataBuilder.create();

        btn_AD_addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((eT_AD_addNote.getText().toString() == "")
                ||(eT_AD_addNote.getText().toString().length()>20)
                ||(eT_AD_addValue.getText().toString() == "")
                ||(eT_AD_addValue.getText().toString().length()>20)){

                    //Error

                }else{

                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy HH:mm");
                    String Datehelper = add_Date.toString() +" "+ add_Time.toString();
                    try {
                        add_FullDate = format.parse(Datehelper);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    Toast.makeText(getActivity(), eT_AD_addNote.getText().toString(), Toast.LENGTH_SHORT).show();
                    
                    items_notes.add(eT_AD_addNote.getText().toString());
                    items_values.add(Double.valueOf(eT_AD_addValue.getText().toString()));
                    items_dates.add(add_FullDate);

                    AD_addData.dismiss();

                }

            }
        });

        AD_addData.show();
    }


    @Override
    public void onResume() {
        super.onResume();


        Init_RecyclerView();


    }


    private void Init_RecyclerView() {
        RV_Graph_Data = (RecyclerView)contentView_graphview_data.findViewById(R.id.RV_GraphView_Data);
        RVLM_Graph_Data = new LinearLayoutManager(this.getActivity());
        RV_Graph_Data.setLayoutManager(RVLM_Graph_Data);

        RVA_Graph_Data = new RVA_graphView_data();
        RV_Graph_Data.setAdapter(RVA_Graph_Data);
    }


    @Override
    public void onPause() {
        super.onPause();
    }
}
