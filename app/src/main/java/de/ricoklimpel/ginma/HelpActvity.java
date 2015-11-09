package de.ricoklimpel.ginma;

import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class HelpActvity extends AppCompatActivity {


    DrawerLayout DrawerLayout_Help;
    ActionBarDrawerToggle DrawerToggle;
    Toolbar Toolbar_Help;
    ActionBar Actionbar_Help;
    NavigationView help_navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_actvity);


        Toolbar_Help = (Toolbar)findViewById(R.id.Toolbar_Help);
        setSupportActionBar(Toolbar_Help);
        Actionbar_Help = getSupportActionBar();
        Actionbar_Help.setDisplayHomeAsUpEnabled(true);

        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        //DrawerToggle.syncState();

        DrawerLayout_Help = (DrawerLayout)findViewById(R.id.DrawerLayout_Help);
        DrawerToggle = new ActionBarDrawerToggle(HelpActvity.this,DrawerLayout_Help,R.string.app_name,R.string.app_name);
        DrawerLayout_Help.setDrawerListener(DrawerToggle);

        help_navigationView = (NavigationView)findViewById(R.id.help_navigationView);
        help_navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch(menuItem.getItemId()){

                    case R.id.menuitem_procat:{

                        break;
                    }
                    case R.id.menuitem_graphs:{

                        break;
                    }
                    case  R.id.menuitem_developer:{

                        break;
                    }
                }

                DrawerLayout_Help.closeDrawers();
                menuItem.setCheckable(true);

                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_choose_projekt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        if(DrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        DrawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        DrawerToggle.onConfigurationChanged(new Configuration());
    }
}
