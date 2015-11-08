package de.ricoklimpel.ginma;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by ricoklimpel on 07.11.15.
 */

public class RVA_categorysAdapterClass
        extends RecyclerView.Adapter<RVA_categorysAdapterClass.ViewHolderClass> {


    public class ViewHolderClass extends RecyclerView.ViewHolder {


        CardView CV_categorys;
        TextView tV_cardContent_catergorys;


        public ViewHolderClass(View itemView) {

            super(itemView);


            CV_categorys = (CardView)itemView.findViewById(R.id.CV_projects);
            tV_cardContent_catergorys = (TextView)itemView
                    .findViewById(R.id.tV_CardContent_projects);

        }

    }

    @Override
    public ViewHolderClass onCreateViewHolder(ViewGroup viewGroup, int i) {


        View rv_item_categorys = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_item_projects, null);


        return new ViewHolderClass(rv_item_categorys);
    }

    @Override
    public void onBindViewHolder(ViewHolderClass viewHolderClass, final int i) {

        viewHolderClass.tV_cardContent_catergorys
                .setText(ChooseCategoryActivity.ArrayCategoryObjects.get(i));


        viewHolderClass.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Weitergeben an Graph View

            }
        });

        viewHolderClass.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                ChooseCategoryActivity.DeleteDialog(i, ChooseCategoryActivity.CCA_ActivityContent);

                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return ChooseCategoryActivity.ArrayCategoryObjects.size();
    }
}
