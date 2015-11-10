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
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by ricoklimpel on 09.11.15.
 */
public class Fragment_GraphView_Graph extends Fragment {

    View contentView_graphview_graph;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView_graphview_graph = inflater.inflate(R.layout.fragment_graphview_graph,null);

        return contentView_graphview_graph;
    }





    LineChartView chart1;
    LineChartData data;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<PointValue> values = new ArrayList<PointValue>();
        List<Line> lines = new ArrayList<Line>();
        values.add(new PointValue(0, 2));
        values.add(new PointValue(1, 4));
        values.add(new PointValue(2, 3));
        values.add(new PointValue(3, 4));
        values.add(new PointValue(4, 10));
        values.add(new PointValue(5, 3));
        values.add(new PointValue(6, 17));
        values.add(new PointValue(7, 9));

        Line line = new Line(values);
        line.setColor(Color.BLUE);
        line.setFilled(true);
        line.setCubic(true);
        line.setHasLabelsOnlyForSelected(true);
        line.setHasLines(true);
        line.setHasPoints(true);

        lines.add(line);
        data = new LineChartData(lines);

        chart1 = (LineChartView)contentView_graphview_graph.findViewById(R.id.chart);

        chart1.setLineChartData(data);
        chart1.isInteractive();
        chart1.setScrollContainer(true);
        chart1.setZoomEnabled(true);
        chart1.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);





    }
}
