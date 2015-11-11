package de.ricoklimpel.ginma;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Date;


public class GraphViewActivity extends AppCompatActivity {

    static Toolbar toolbar_graphview;
    ViewPager viewpager_graphview;
    TabLayout tablyout_graphview;
    ActionBar actionbar_graphview;

    static Activity CGV_activityContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);
        CGV_activityContent = this;


        toolbar_graphview = (Toolbar) findViewById(R.id.Toolbar_GraphView);
        setSupportActionBar(toolbar_graphview);
        toolbar_graphview.setSubtitle(ChooseProjektActivity.ArrayProjectNames.get(
                Integer.valueOf(ChooseProjektActivity.Projekt_ID))
                + " => " + ChooseCategoryActivity.ArrayCategoryNames.get(
                Integer.valueOf(ChooseCategoryActivity.Category_ID)));

        actionbar_graphview = getSupportActionBar();
        actionbar_graphview.setDisplayHomeAsUpEnabled(true);



        viewpager_graphview = (ViewPager) findViewById(R.id.ViewPager_GraphView);

        ViewPagerAdapter_GraphView viewPagerAdapter_graphView
                = new ViewPagerAdapter_GraphView(getSupportFragmentManager());
        viewpager_graphview.setAdapter(viewPagerAdapter_graphView);


        tablyout_graphview = (TabLayout) findViewById(R.id.TabLayout_GraphView);
        tablyout_graphview.setTabGravity(TabLayout.GRAVITY_FILL);
        tablyout_graphview.setupWithViewPager(viewpager_graphview);
        tablyout_graphview.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewpager_graphview.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_graph_view, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


