package com.example.tan_pc.navigationdraweractivity;

import android.content.Context;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import SettingsSQLite.SqliteHelper;
import adapter.AdapterGrid;

import static SettingsSQLite.SqliteHelper.KEY_ID;
import static SettingsSQLite.SqliteHelper.KEY_IMAGES;
import static SettingsSQLite.SqliteHelper.KEY_IP;
import static SettingsSQLite.SqliteHelper.KEY_LANGUAGES;
import static SettingsSQLite.SqliteHelper.KEY_PORT;
import static SettingsSQLite.SqliteHelper.KEY_ROWS;
import static SettingsSQLite.SqliteHelper.KEY_THEMES;
import static SettingsSQLite.SqliteHelper.KEY_THREHOLD;
import static SettingsSQLite.SqliteHelper.KEY_VALVES;
import static SettingsSQLite.SqliteHelper.TABLE_SETTINGS;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static SqliteHelper PROJECTDATABASE;
    final static String DATABASENAME = "db_Waterfall";
    public RelativeLayout rHome;
    public RelativeLayout rDisplayText;
    public RelativeLayout rClock;
    public RelativeLayout rImport;
    public RelativeLayout rPaint;
    public RelativeLayout rSettings;
    //    public RelativeLayout rHomeB;
//    public RelativeLayout rDisplayTextB;
//    public RelativeLayout rClockB;
//    public RelativeLayout rImportB;
//    public RelativeLayout rPaintB;
//    public RelativeLayout rSettingsB;
    int countBackButtonPress = 0;
    DrawerLayout backupmainApp;
    Button btSlideShow;
    int sttfragment = 0;//trang thai dang o fragment nao 0=home; 1=Displaytext;2=Clock; 3=Import....
    DisplayTextFragment displayTextFragment = new DisplayTextFragment();
    AlbumDisplay albumDisplay = new AlbumDisplay();
    PaintFragment paintFragment = new PaintFragment();
    SettingsFragment settingsFragment = new SettingsFragment();
    ClockFragment clockFragment = new ClockFragment();
    HomeFragment homeFragment = new HomeFragment();
    FragmentManager manager = getSupportFragmentManager();

    //Hide Keyboard if click app recent button if keyboard in showing
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitializeComponent();
        FragmentShow(4);
        FragmentShow(3);
        FragmentShow(2);
        FragmentShow(1);
        FragmentShow(5);
        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.e("Show Hom: ", "Fragment 5");
            }
        });
        FragmentShow(0);
        Log.e("Show Hom: ", "Fragment 0");
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d("Focus debug", "Focus changed !");
        if (!hasFocus) {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                countBackButtonPress = 0;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            countBackButtonPress++;
            Log.d(this.getClass().getName(), "back button pressed");
            if (countBackButtonPress == 1) {
                ToastShow("Press Back Again To Run In BackGround");
            } else if (countBackButtonPress == 2) {
                countBackButtonPress++;
                moveTaskToBack(true);
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    //Hint the slide
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //Khong Tat Ung Dung Khi nhan nut Back;
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {

            FragmentShow(0);
        } else if (id == R.id.nav_keytext) {

            FragmentShow(1);
        } else if (id == R.id.nav_clock) {

            FragmentShow(2);
        } else if (id == R.id.nav_import) {

            FragmentShow(3);
        } else if (id == R.id.nav_paint) {

            FragmentShow(4);
        } else if (id == R.id.nav_settings) {

            FragmentShow(5);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void InitializeComponent() {
        try {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            setContentView(R.layout.activity_main);

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            rHome = (RelativeLayout) findViewById(R.id.rHome);
            rClock = (RelativeLayout) findViewById(R.id.rClock);
            rDisplayText = (RelativeLayout) findViewById(R.id.rDisplayText);
            rImport = (RelativeLayout) findViewById(R.id.rImport);
            rPaint = (RelativeLayout) findViewById(R.id.rPaint);
            rSettings = (RelativeLayout) findViewById(R.id.rSettings);
            backupmainApp = (DrawerLayout) findViewById(R.id.drawer_layout);
            //btSlideShow = (Button) findViewById(R.id.idslideshow);
            ThreelinesLeftShow();
            PROJECTDATABASE = new SqliteHelper(getApplicationContext(), DATABASENAME, null, 1);
        } catch (Exception e) {
            ToastShow(e.getMessage().toString());

        }
    }

    //settings...
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            getSupportActionBar().setTitle("Settings");
            manager.beginTransaction().replace(R.id.rSettings, settingsFragment).commit();
            hideAllFragment();
            rSettings.setVisibility(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }

    private void ThreelinesLeftShow() {
        try{
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer,
                    toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close) {
                /**
                 * Called when a drawer has settled in a completely closed state.
                 */
                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                }

                /**
                 * Called when a drawer has settled in a completely open state.
                 */
                // @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }
            };
            drawer.addDrawerListener(toggle);
            toggle.syncState();
        }catch (Exception e){
            ToastShow(e.getMessage().toString());
        }

    }

    private void FragmentShow(int frag) {
        try{

            switch (frag) {
                case 0:
                    sttfragment = 0;
                    hideAllFragment();
                    rHome.setVisibility(View.VISIBLE);
                    setTitle("Graphicial Waterfall ");
                    manager.beginTransaction().replace(R.id.rHome, homeFragment).commit();

                    break;
                case 1:
                    sttfragment = 1;
                    hideAllFragment();
                    rDisplayText.setVisibility(View.VISIBLE);
                    setTitle("Display Text");
                    manager.beginTransaction().replace(R.id.rDisplayText, displayTextFragment).commit();

                    break;
                case 2:
                    sttfragment = 2;
                    hideAllFragment();
                    rClock.setVisibility(View.VISIBLE);
                    setTitle("Display Clock");
                    manager.beginTransaction().replace(R.id.rClock, clockFragment).commit();

                    break;
                case 3:
                    sttfragment = 3;
                    hideAllFragment();
                    rImport.setVisibility(View.VISIBLE);
                    setTitle("Gallery");
                    manager.beginTransaction().replace(R.id.rImport, albumDisplay).commit();

                    break;
                case 4:
                    sttfragment = 4;
                    hideAllFragment();
                    rPaint.setVisibility(View.VISIBLE);
                    setTitle("Display Paint");
                    manager.beginTransaction().replace(R.id.rPaint, paintFragment).commit();

                    break;
                case 5: //sttfragment=5;
                    hideAllFragment();
                    rSettings.setVisibility(View.VISIBLE);
                    setTitle("Settings ");
                    manager.beginTransaction().replace(R.id.rSettings, settingsFragment).commit();

                    break;
                default:
                    sttfragment = 0;
                    hideAllFragment();
                    rHome.setVisibility(View.VISIBLE);
                    setTitle("Graphicial Waterfall ");
                    manager.beginTransaction().replace(R.id.rHome, homeFragment).commit();

                    break;
            }

        }catch (Exception e){
            ToastShow(e.getMessage().toString());
        }
    }

    public void setTitle(String title) {
        try{
            getSupportActionBar().setTitle(title);
        }catch (Exception e){
            ToastShow(e.getMessage().toString());
        }

    }

    public void ToastShow(String frag) {
        try{
            Toast.makeText(this, frag, Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }

    }

    public void hideAllFragment() {
        try{
            rHome.setVisibility(View.GONE);
            rClock.setVisibility(View.GONE);
            rPaint.setVisibility(View.GONE);
            rImport.setVisibility(View.GONE);
            rSettings.setVisibility(View.GONE);
            rDisplayText.setVisibility(View.GONE);
        }catch (Exception e)
        {
            ToastShow(e.getMessage().toString());
        }

    }

    /* (non-Javadoc)
* @see android.app.Activity#dispatchTouchEvent(android.view.MotionEvent)
*/
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View v = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);
        if (this instanceof MainActivity) {
            return false;
        }
        if (v instanceof EditText) {
            View w = getCurrentFocus();
            int scrCoords[] = new int[2];
            w.getLocationOnScreen(scrCoords);
            float x = event.getRawX() + w.getLeft() - scrCoords[0];
            float y = event.getRawY() + w.getTop() - scrCoords[1];
            if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom())) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }


//    //Backup beforechanged
//    void backupFragment()
//    {
//        rHomeB=rHome;
//        rDisplayTextB=rDisplayText;
//        rClockB=rClock;
//        rImportB=rImport;
//        rPaintB=rPaint;
//        rSettingsB=rSettings;
//    }
//    //Backup beforechanged
//    void recoveryComponent()
//    {
//        rHome=rHomeB;
//        rDisplayText=rDisplayTextB;
//        rClock=rClockB;
//        rImport=rImportB;
//        rPaint=rPaintB;
//        rSettings=rSettingsB;
//    }
    //khi xoay man hinh thi khong bi giu nguyen layout
//    @Override
//        public void onConfigurationChanged(Configuration newConfig) {
//            super.onConfigurationChanged(newConfig);
//            backupFragment();
//            // Checks the orientation of the screen
//            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
//                setContentView(R.layout.activity_main);
//                InitializeComponent();
//                recoveryComponent();
//            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//                Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
//                setContentView(R.layout.activity_main);
//                InitializeComponent();
//                recoveryComponent();
//            }
//    }

}

