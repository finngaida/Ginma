package de.ricoklimpel.ginma;

import android.graphics.Color;
import android.graphics.PathEffect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.util.AxisAutoValues;
import lecho.lib.hellocharts.util.ChartUtils;
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
        line.setFilled(true);
        line.setCubic(true);
        line.setHasLabels(true);
        line.setHasLines(true);
        line.setHasPoints(true);

        lines.add(line);
        data = new LineChartData(lines);
        data.setBaseValue(0);

        chart1 = (LineChartView)contentView_graphview_graph.findViewById(R.id.chart);

        chart1.setLineChartData(data);
        chart1.setBottom(-10);


    }
}
