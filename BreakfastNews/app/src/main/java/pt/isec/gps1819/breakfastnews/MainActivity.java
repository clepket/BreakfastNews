package pt.isec.gps1819.breakfastnews;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , TimePickerDialog.OnTimeSetListener {

    String hora = " ";
    String minuto = " ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public String readFile(String file) {
        String text = null;

        try {
            FileInputStream fis = openFileInput(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            text = new String(buffer);
        } catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error reading from file!", Toast.LENGTH_SHORT).show();
        }

        return text;
    }

    public void saveTextAsFile(String fileName, String keywords, String jornalistas) {
        String enter = "\n";

        //create file
        File file = new File(fileName);


        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            fos.write(keywords.getBytes());
            fos.write(enter.getBytes());
            fos.write(jornalistas.getBytes());
            CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
            if (checkBox.isChecked()) {
                fos.write(enter.getBytes());
                fos.write(hora.getBytes());
                fos.write(enter.getBytes());
                fos.write(minuto.getBytes());
            }
            fos.close();
            Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "File not found!!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error saving!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (item.getItemId()){
            case R.id.nav_feed:
                //setTitle(getResources().getString(R.string.app_name) + " - " + getResources().getString(R.string.frag_title_feed));
                fragmentManager.beginTransaction().replace(R.id.fragment, new FeedFragment()).commit();
                break;
            case R.id.nav_favorites:
                //setTitle(getResources().getString(R.string.app_name) + " - " + getResources().getString(R.string.frag_title_favorites));
                fragmentManager.beginTransaction().replace(R.id.fragment, new FavoriteFragment()).commit();
                break;
            case R.id.nav_config:
                //setTitle(getResources().getString(R.string.app_name) + " - " + getResources().getString(R.string.frag_title_config));
                fragmentManager.beginTransaction().replace(R.id.fragment, new ConfigFragment()).commit();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hora = Integer.toString(hourOfDay);
        minuto = Integer.toString(minute);
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);

        checkBox.setText("Todos os dias às " + hourOfDay + "h" + minute);
        checkBox.setChecked(true);
    }
}
