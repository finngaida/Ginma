package de.ricoklimpel.ginma;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ricoklimpel on 10.11.15.
 */

public class RVA_graphView_data
        extends RecyclerView.Adapter<RVA_graphView_data.ViewHolderKlasse> {


    public class ViewHolderKlasse extends RecyclerView.ViewHolder {

        TextView tv_graphdata_date;
        TextView tv_graphdata_notes;
        TextView tv_graphdata_values;

        public ViewHolderKlasse(View itemView) {
            super(itemView);

            tv_graphdata_date = (TextView)itemView.findViewById(R.id.tv_graphdata_date);
            tv_graphdata_notes= (TextView)itemView.findViewById(R.id.tv_graphdata_notes);
            tv_graphdata_values = (TextView)itemView.findViewById(R.id.tv_graphdata_value);



        }
    }


    @Override
    public ViewHolderKlasse onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_graphdata,null);

        return new ViewHolderKlasse(itemView1);
    }

    @Override
    public void onBindViewHolder(ViewHolderKlasse holder, int position) {

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy  -  HH:mm");

        Date date = Fragment_GraphView_Data.items_dates.get(position);

        holder.tv_graphdata_date.setText(
                String.valueOf(format.format(date)));
        holder.tv_graphdata_notes.setText(
                Fragment_GraphView_Data.items_notes.get(position));
        holder.tv_graphdata_values.setText(
                String.valueOf(Fragment_GraphView_Data.items_values.get(position)));


    }

    @Override
    public int getItemCount() {
        return Fragment_GraphView_Data.items_values.size();
    }
}
