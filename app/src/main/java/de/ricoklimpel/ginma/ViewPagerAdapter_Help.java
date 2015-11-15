package de.ricoklimpel.ginma;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ricoklimpel on 09.11.15.
 */
public class ViewPagerAdapter_Help extends FragmentPagerAdapter {


    String[] TabTitleGraphViewArray = {"Daten","Graph"};


    public ViewPagerAdapter_Help(FragmentManager manager){

        super(manager);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0: {
                return new Fragment_Help_examples();

            }
            case 1:{
                return new Fragment_Help_ProCat();

            }
            case 2:{
                return new Fragment_GraphView_Data();

            }
            case 3:{
                return new Fragment_Help_Graphs();

            }
            case 4:{
                return new Fragment_Help_developer();

            }


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
