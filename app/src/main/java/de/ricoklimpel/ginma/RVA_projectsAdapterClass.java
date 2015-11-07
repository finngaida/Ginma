package de.ricoklimpel.ginma;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * Created by ricoklimpel on 07.11.15.
 */

public class RVA_projectsAdapterClass
        extends RecyclerView.Adapter<RVA_projectsAdapterClass.ViewHolderClass> {


    public class ViewHolderClass extends RecyclerView.ViewHolder {


        CardView CV_projects;
        TextView tV_cardContent_projects;


        public ViewHolderClass(View itemView) {

            super(itemView);


            //Objecte aus dem Layout inizialisieren
            CV_projects = (CardView)itemView.findViewById(R.id.CV_projects);
            tV_cardContent_projects = (TextView)itemView
                    .findViewById(R.id.tV_CardContent_projects);

        }

    }

    @Override
    public ViewHolderClass onCreateViewHolder(ViewGroup viewGroup, int i) {


        //De, Recycler View das richtige Layout zuweisen
        View rv_item_projects = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_item_projects, null);


        return new ViewHolderClass(rv_item_projects);
    }

    @Override
    public void onBindViewHolder(ViewHolderClass viewHolderClass, final int i) {


        //Immer den richtigen Text aus dem Array anzeigen
        viewHolderClass.tV_cardContent_projects
                .setText(ChooseProjektActivity.ArrayProjectObjects.get(i));

        viewHolderClass.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {

        //größe des Arrays abrufen um zu wissen wie lang die Liste wird.
        return ChooseProjektActivity.ArrayProjectObjects.size();
    }
}
