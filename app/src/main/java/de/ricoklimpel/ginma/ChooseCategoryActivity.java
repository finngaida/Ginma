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
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Arrays;

public class ChooseCategoryActivity extends AppCompatActivity {

    static Activity CCA_ActivityContent;

    static RecyclerView RV_categorys;
    static RecyclerView.Adapter RVA_categorys;
    RecyclerView.LayoutManager RVLM_categorys;

    //Im Format "CID_"
    static String Category_ID;


    FloatingActionButton FAB_HelpCat;
    FloatingActionButton FAB_PlusCat;
    static FloatingActionsMenu FAM_categorys;


    static ArrayList<String> ArrayCategoryNames;


    //Dialogelemente neue Kategorie erstellen
    static Button btn_AD_addcategory;
    static MaterialEditText eT_AD_addcategory;


    static SharedPreferences prefs;
    static SharedPreferences.Editor prefseditor;

    static SharedPreferences prefs_values;
    static SharedPreferences.Editor prefseditor_values;

    Toolbar toolbar_choosecategory;
    ActionBar actionbar_choosecategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);
        CCA_ActivityContent = this;


        Init_Toolbar();

        Init_FabMenu();

    }

    private void Init_FabMenu() {
        FAM_categorys = (FloatingActionsMenu)findViewById(R.id.FAM_categorys);
        FAB_HelpCat = (FloatingActionButton)findViewById(R.id.FAB_helpCat);
        FAB_HelpCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ChooseCategoryActivity.this,
                        "Diese Funktion steht zur Zeit nicht zur Verfügung", Toast.LENGTH_SHORT).show();

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


    private void Init_Toolbar() {
        toolbar_choosecategory = (Toolbar)findViewById(R.id.Toolbar_ChooseCategory);
        setSupportActionBar(toolbar_choosecategory);

        toolbar_choosecategory.setSubtitle(
                ChooseProjektActivity.ArrayProjectNames.get(
                        Integer.valueOf(ChooseProjektActivity.Projekt_ID)));

        if(Build.VERSION.SDK_INT>=21){
            toolbar_choosecategory.setElevation(25);
        }
        actionbar_choosecategory = getSupportActionBar();
        actionbar_choosecategory.setDisplayHomeAsUpEnabled(true);
    }


    private void AddCategory_AlertDialog() {
        FAM_categorys.collapse();

        LayoutInflater AD_inflater = getLayoutInflater();
        View AD_View = AD_inflater.inflate(R.layout.ad_textinput_categorys,null);

        final AlertDialog.Builder AD_addProjectbuilder = new AlertDialog.Builder(ChooseCategoryActivity.this);
        AD_addProjectbuilder.setTitle("Neue Kategorie hinzufügen");
        AD_addProjectbuilder.setView(AD_View);

        btn_AD_addcategory = (Button)AD_View.findViewById(R.id.btn_AD_addcategorys);
        eT_AD_addcategory = (MaterialEditText)AD_View.findViewById(R.id.eT_AD_addcategorys);

        final AlertDialog AD_addProject = AD_addProjectbuilder.create();

        btn_AD_addcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (eT_AD_addcategory.getText().toString().isEmpty()) {

                    Toast.makeText(ChooseCategoryActivity.this, "Trage einen Kategorienamen ein!", Toast.LENGTH_SHORT).show();


                }else if (eT_AD_addcategory.getText().toString().length() > 20) {

                    Toast.makeText(ChooseCategoryActivity.this, "Maximal 20 Zeichen!", Toast.LENGTH_SHORT).show();

                }else {

                    addCategory();

                    RVA_categorys.notifyDataSetChanged();
                    RV_categorys.smoothScrollToPosition(ArrayCategoryNames.size());

                    AD_addProject.dismiss();
                }

            }
        });

        AD_addProject.show();
    }


    private void addCategory() {


        ArrayCategoryNames.add(eT_AD_addcategory.getText().toString());


        prefs = this.getSharedPreferences(String.valueOf("ID_" + ChooseProjektActivity.Projekt_ID), MODE_PRIVATE);
        prefseditor = prefs.edit();

        prefseditor.putString("category_names", convertToString(ArrayCategoryNames));
        prefseditor.putString("dates_CID_" + String.valueOf(ArrayCategoryNames.size() - 1), "");
        prefseditor.putString("values_CID_" + String.valueOf(ArrayCategoryNames.size() - 1), "");
        prefseditor.putString("notes_CID_" + String.valueOf(ArrayCategoryNames.size() - 1), "");
        prefseditor.commit();
    }


    @Override
    protected void onPause() {
        super.onPause();

        prefs = this.getSharedPreferences(String.valueOf("ID_" + ChooseProjektActivity.Projekt_ID), MODE_PRIVATE);
        prefseditor = prefs.edit();

        prefseditor.putString("category_names", convertToString(ArrayCategoryNames));
        prefseditor.putInt("category_quantity", ArrayCategoryNames.size());
        prefseditor.commit();

    }


    @Override
    protected void onResume() {
        super.onResume();

        loadCategorynameArray();

        Init_RecyclerView();
    }


    private void Init_RecyclerView() {
        RV_categorys = (RecyclerView)findViewById(R.id.RV_categorys);
        RVLM_categorys = new LinearLayoutManager(this);

        RV_categorys.setLayoutManager(RVLM_categorys);

        RVA_categorys = new RVA_categorysAdapterClass();
        RV_categorys.setAdapter(RVA_categorys);
    }


    private void loadCategorynameArray() {
        ArrayCategoryNames = new ArrayList<>();

        prefs = this.getSharedPreferences(String.valueOf("ID_" + ChooseProjektActivity.Projekt_ID), MODE_PRIVATE);
        String stringback =  prefs.getString("category_names","");

        if(stringback!=""){
            ArrayCategoryNames.addAll(Arrays.asList(stringback.split(","))) ;
        }
    }


    static public void DeleteDialog(final int i, Activity activity){
        new AlertDialog.Builder(activity)
                .setTitle("Kategorie löschen")
                .setMessage("Bist du dir sicher das '" +
                        ChooseCategoryActivity.ArrayCategoryNames.get(i) +
                        "' und sämtliche Daten in dieser Kategorie gelöscht werden sollen?")

                .setPositiveButton("löschen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        prefs = CCA_ActivityContent.getSharedPreferences(
                                String.valueOf("ID_" + ChooseProjektActivity.Projekt_ID), MODE_PRIVATE);
                        prefs.edit().remove("dates_CID_"+String.valueOf(i)).commit();
                        prefs.edit().remove("values_CID_"+String.valueOf(i)).commit();
                        prefs.edit().remove("notes_CID_"+String.valueOf(i)).commit();


                        ChooseCategoryActivity.ArrayCategoryNames.remove(i);


                        prefs = CCA_ActivityContent.getSharedPreferences(String.valueOf("ID_" + ChooseProjektActivity.Projekt_ID), MODE_PRIVATE);
                        prefseditor = prefs.edit();

                        prefseditor.putString("category_names", convertToString(ArrayCategoryNames));
                        prefseditor.putInt("category_quantity", ArrayCategoryNames.size());
                        prefseditor.commit();


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


    private static String convertToString(ArrayList<String> list) {

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
        getMenuInflater().inflate(R.menu.menu_choose_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_helpc) {

            Intent intent = new Intent(ChooseCategoryActivity.this, HelpActvity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.own_fade_in, R.anim.own_fade_out);

            return true;
        }

        if(id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Intent categoryintent = new Intent(ChooseCategoryActivity.CCA_ActivityContent, ChooseProjektActivity.class);
        ChooseCategoryActivity.CCA_ActivityContent.startActivity(categoryintent);
        ChooseCategoryActivity.CCA_ActivityContent.overridePendingTransition(R.anim.left_in,R.anim.right_out);
    }


    public static void open_Popup(View v,final int i, final Activity activity) {


        PopupMenu popup = new PopupMenu(ChooseCategoryActivity.CCA_ActivityContent,v);
        popup.getMenuInflater().inflate(R.menu.popup_rename_delete,popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuitem_rename: {

                        RenameCategory_AlertDialog(i);

                        break;
                    }
                    case R.id.menuitem_delete: {

                        DeleteDialog(i, activity);

                        break;
                    }
                }

                return false;
            }
        });

        popup.show();


    }


    public static void RenameCategory_AlertDialog(final int i) {

        LayoutInflater AD_inflater = CCA_ActivityContent.getLayoutInflater();
        View AD_View = AD_inflater.inflate(R.layout.ad_textinput_categorys, null);

        final AlertDialog.Builder AD_addProjectbuilder = new AlertDialog.Builder(CCA_ActivityContent);
        AD_addProjectbuilder.setTitle("Kategorie umbenennen");
        AD_addProjectbuilder.setView(AD_View);

        btn_AD_addcategory = (Button)AD_View.findViewById(R.id.btn_AD_addcategorys);
        btn_AD_addcategory.setText("speichern");
        eT_AD_addcategory = (MaterialEditText)AD_View.findViewById(R.id.eT_AD_addcategorys);
        eT_AD_addcategory.setHint("Neuen Kategorienamen eingeben");

        final AlertDialog AD_addProject = AD_addProjectbuilder.create();

        btn_AD_addcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (eT_AD_addcategory.getText().toString().isEmpty()) {

                    Toast.makeText(ChooseProjektActivity.CPA_activityContent,
                            "Bitte trage einen Kategorienamen ein!", Toast.LENGTH_SHORT).show();

                } else if (eT_AD_addcategory.getText().toString().length() > 20) {

                    Toast.makeText(ChooseProjektActivity.CPA_activityContent,
                            "Nicht mehr als 20 Zeichen!", Toast.LENGTH_SHORT).show();

                } else {

                    renameCategory(i, eT_AD_addcategory.getText().toString());


                    RVA_categorys.notifyDataSetChanged();
                    RV_categorys.smoothScrollToPosition(ArrayCategoryNames.size());

                    AD_addProject.dismiss();
                }

            }
        });

        AD_addProject.show();
    }

    public static  void renameCategory(int i, String name) {


        ArrayCategoryNames.remove(i);
        ArrayCategoryNames.add(i, name);


        prefs = CCA_ActivityContent.getSharedPreferences(String.valueOf("ID_" + ChooseProjektActivity.Projekt_ID), MODE_PRIVATE);
        prefseditor = prefs.edit();

        prefseditor.putString("category_names", convertToString(ArrayCategoryNames));
        prefseditor.putInt("category_quantity", ArrayCategoryNames.size());
        prefseditor.commit();


    }

}




//TODO Kommazahlen bei den Graphdaten unterstützen
//TODO den Graphen verschönern, das der nicht mehr aus dem Bildschirm rausgeht + evtl. Daten anzeigen
//TODO Graphdaten automatisch nach Daten sortieren (gleich beim einfügen!!)
//TODO Hilfe Bildschirm gestalten
//TODO Entwickler Infos gestalten: Links zu Twitter FB usw.
//TODO Beim Kategorieerstellen einheiten angeben für die Darinliegenden Daten
//TODO Statistiken / Graphen als Bild teilen
