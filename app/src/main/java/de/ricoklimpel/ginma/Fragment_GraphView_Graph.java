package de.ricoklimpel.ginma;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
