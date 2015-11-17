package de.ricoklimpel.ginma;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;


/**
 * Created by ricoklimpel on 09.11.15.
 */
public class Fragment_GraphView_Graph extends Fragment {

    static View contentView_graphview_graph;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView_graphview_graph = inflater.inflate(R.layout.fragment_graphview_graph,null);

        return contentView_graphview_graph;
    }





    static LineChartView chart1;
    static LineChartData data;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);







    }

    @Override
    public  void onResume() {
        super.onResume();


        ChartReload();


    }

    @Override
    public void onPause() {
        super.onPause();
    }


    public static void ChartReload() {

        List<PointValue> values = new ArrayList<PointValue>();
        List<Line> lines = new ArrayList<Line>();

        Float f;

        for(int i=0; i<Fragment_GraphView_Data.items_values.size(); i++)

        {
            int Coord = i;
            values.add(new PointValue(i, Integer.valueOf(Fragment_GraphView_Data.items_values.get(i))));

        }



        Line line = new Line(values);
        line.setColor(Color.BLUE);
        line.setPointColor(Color.DKGRAY);
        line.setFilled(true);
        line.setCubic(false);

        line.setHasLines(true);
        line.setHasLabelsOnlyForSelected(true);

        line.setHasPoints(true);


        lines.add(line);
        data = new LineChartData(lines);

        chart1 = (LineChartView)contentView_graphview_graph.findViewById(R.id.chart);
        chart1.setFadingEdgeLength(8);
        chart1.setZoomEnabled(true);
        chart1.setZoomType(ZoomType.HORIZONTAL);
        chart1.setMaxZoom(4);
        chart1.setLineChartData(data);




        chart1.setOnValueTouchListener(new LineChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {

            }

            @Override
            public void onValueDeselected() {

            }
        });


    }


    private static int maxValue(char[] chars) {
        int max = chars[0];
        for (int ktr = 0; ktr < chars.length; ktr++) {
            if (chars[ktr] > max) {
                max = chars[ktr];
            }
        }
        return max;
    }
}
