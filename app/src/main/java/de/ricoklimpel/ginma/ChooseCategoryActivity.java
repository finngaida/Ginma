package de.ricoklimpel.ginma;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gc.materialdesign.views.ButtonFlat;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Arrays;

public class ChooseCategoryActivity extends AppCompatActivity {

    static Activity CCA_ActivityContent;

    RecyclerView RV_categorys;
    static RecyclerView.Adapter RVA_categorys;
    RecyclerView.LayoutManager RVLM_categorys;


    FloatingActionButton FAB_HelpCat;
    FloatingActionButton FAB_PlusCat;
    static FloatingActionsMenu FAM_categorys;

    static ArrayList<String> ArrayCategoryObjects;


    ButtonFlat btn_AD_addcategory;
    MaterialEditText eT_AD_addcategory;


    SharedPreferences prefs;
    SharedPreferences.Editor prefseditor;


    Toolbar toolbar_choosecategory;
    ActionBar actionbar_choosecategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);
        CCA_ActivityContent = this;


        toolbar_choosecategory = (Toolbar)findViewById(R.id.Toolbar_ChooseCategory);
        setSupportActionBar(toolbar_choosecategory);
        toolbar_choosecategory.setSubtitle(ChooseProjektActivity.USERS_PATH.toUpperCase());
        if(Build.VERSION.SDK_INT>=21){
            toolbar_choosecategory.setElevation(25);
        }
        actionbar_choosecategory = getSupportActionBar();
        actionbar_choosecategory.setDisplayHomeAsUpEnabled(true);


        FAM_categorys = (FloatingActionsMenu)findViewById(R.id.FAM_categorys);
        FAB_HelpCat = (FloatingActionButton)findViewById(R.id.FAB_helpCat);
        FAB_HelpCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ChooseCategoryActivity.this, HelpActvity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });

        FAB_PlusCat = (FloatingActionButton)findViewById(R.id.FAB_plusCat);
        FAB_PlusCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddCategory_AlertDialog();

            }
        });

    }


    private void AddCategory_AlertDialog() {
        FAM_categorys.collapse();

        LayoutInflater AD_inflater = getLayoutInflater();
        View AD_View = AD_inflater.inflate(R.layout.ad_textinput_projects,null);

        final AlertDialog.Builder AD_addProjectbuilder = new AlertDialog.Builder(ChooseCategoryActivity.this);
        AD_addProjectbuilder.setTitle("Neue Kategorie hinzufügen");
        AD_addProjectbuilder.setView(AD_View);

        btn_AD_addcategory = (ButtonFlat)AD_View.findViewById(R.id.btn_AD_addprojects);
        eT_AD_addcategory = (MaterialEditText)AD_View.findViewById(R.id.eT_AD_addprojects);

        final AlertDialog AD_addProject = AD_addProjectbuilder.create();

        btn_AD_addcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (eT_AD_addcategory.getText().toString() != "") {
                    if (eT_AD_addcategory.length() > 20) {

                        //Flash Button red or something?

                    } else {

                        ArrayCategoryObjects.add(eT_AD_addcategory.getText().toString());
                        RVA_categorys.notifyDataSetChanged();
                        RV_categorys.smoothScrollToPosition(ArrayCategoryObjects.size());

                        AD_addProject.dismiss();
                    }

                }

            }
        });

        AD_addProject.show();
    }



    @Override
    protected void onPause() {
        super.onPause();

        prefs = this.getSharedPreferences("ginma", MODE_PRIVATE);
        prefseditor = prefs.edit();

        prefseditor.putString(ChooseProjektActivity.USERS_PATH, convertToString(ArrayCategoryObjects));
        prefseditor.commit();

    }


    @Override
    protected void onResume() {
        super.onResume();

        ArrayCategoryObjects = new ArrayList<>();


        prefs = this.getSharedPreferences("ginma", MODE_PRIVATE);
        String stringback =  prefs.getString(ChooseProjektActivity.USERS_PATH,"");

        if(stringback!=""){
            ArrayCategoryObjects.addAll(Arrays.asList(stringback.split(","))) ;
        }


        RV_categorys = (RecyclerView)findViewById(R.id.RV_categorys);
        RVLM_categorys = new LinearLayoutManager(this);

        RV_categorys.setLayoutManager(RVLM_categorys);

        RVA_categorys = new RVA_categorysAdapterClass();
        RV_categorys.setAdapter(RVA_categorys);
    }


    static public void DeleteDialog(final int i, Activity activity){
        new AlertDialog.Builder(activity)
                .setTitle("Kategorie löschen")
                .setMessage("Bist du dir sicher das '" +
                        ChooseCategoryActivity.ArrayCategoryObjects.get(i) +
                        "' und sämtliche Daten in dieser Kategorie gelöscht werden sollen?")

                .setPositiveButton("löschen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        ChooseCategoryActivity.ArrayCategoryObjects.remove(i);
                        RVA_categorys.notifyDataSetChanged();

                    }
                })
                .setNegativeButton("abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(R.drawable.ic_warning_black_36dp)
                .show();
    }



    private String convertToString(ArrayList<String> list) {

        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (String s : list)
        {
            sb.append(delim);
            sb.append(s);;
            delim = ",";
        }
        return sb.toString();
    }


    private ArrayList<String> convertToArray(String string) {

        ArrayList<String> list = new ArrayList<String>(Arrays.asList(string.split(",")));
        return list;
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
        if(id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
