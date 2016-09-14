package us.qywang.dotaboost;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CounterFragment.OnFragmentInteractionListener,
        FavoriteFragment.OnFragmentInteractionListener,
        MatchViewFragment.OnFragmentInteractionListener,
        PlayerViewFragment.OnFragmentInteractionListener,
        SearchFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener
{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("DEBUG", "HomeActivity onCreate called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        Log.d("DEBUG", "HomeActivity onBackPressed called");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("DEBUG", "HomeActivity onCreateOptionsMenu called");

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        Log.d("DEBUG", "HomeActivity onOptionsItemSelected called");

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Log.d("DEBUG", "HomeActivity onNavigationItemSelected called");

        final int id = item.getItemId();
        String indicator = "INDICATOR";


        Log.d("BEFORE", indicator);

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.show();
        new Thread()
        {
            public void run()
            {

                try
                {

                    if (id == R.id.nav_mystat) {
                        // Handle nav_search
                        try {



                            Player player = JsonConverter.get_player("364848976");
                            PlayerViewFragment playerViewFragment = PlayerViewFragment.newInstance(player, "364848976");
                            FragmentManager fragmentManager = getSupportFragmentManager();



                            fragmentManager.beginTransaction().replace(
                                    R.id.mainLayout,
                                    playerViewFragment,
                                    playerViewFragment.getTag()
                            ).commit();
                        } catch (Exception e) {
                            Log.d("DEBUG", "Can't load");
                        }

                    } else if (id == R.id.nav_search) {
                        // Handle nav_search
                        SearchFragment searchFragment = SearchFragment.newInstance("hi", "hi");
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(
                                R.id.mainLayout,
                                searchFragment,
                                searchFragment.getTag()
                        ).commit();

                    } else if (id == R.id.nav_favorite) {
                        // Handle nav_favorite
                        FavoriteFragment favoriteFragment = FavoriteFragment.newInstance("hi", "hi");
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(
                                R.id.mainLayout,
                                favoriteFragment,
                                favoriteFragment.getTag()
                        ).commit();

                    } else if (id == R.id.nav_counterpick) {
                        // Handle nav_counterpick
                        CounterFragment counterFragment = CounterFragment.newInstance("hi", "hi");
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(
                                R.id.mainLayout,
                                counterFragment,
                                counterFragment.getTag()
                        ).commit();

                    } else if (id == R.id.nav_settings) {
                        // Handle nav_settings
                        SettingsFragment settingsFragment = SettingsFragment.newInstance("hi", "hi");
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(
                                R.id.mainLayout,
                                settingsFragment,
                                settingsFragment.getTag()
                        ).commit();

                    }
                }
                catch (Exception e)
                {
                    Log.e("tag",e.getMessage());
                }
// dismiss the progressdialog
                progress.dismiss();
            }
        }.start();


        Log.d("AFTER", indicator);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(String title) {
        Log.d("DEBUG", "HomeActivity onFragmentInteraction called");
        getSupportActionBar().setTitle(title);
    }
}
