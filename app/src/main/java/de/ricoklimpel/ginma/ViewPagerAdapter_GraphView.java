package de.ricoklimpel.ginma;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ricoklimpel on 09.11.15.
 */
public class ViewPagerAdapter_GraphView extends FragmentPagerAdapter {


    String[] TabTitleGraphViewArray = {"Daten","Graph"};


    public ViewPagerAdapter_GraphView (FragmentManager manager){

        super(manager);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0: return new Fragment_GraphView_Data();
            case 1: return new Fragment_GraphView_Graph();
            case 2:return  new Fragment_GraphView_Graph();

        }


        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {

        return TabTitleGraphViewArray[position];
    }
}
