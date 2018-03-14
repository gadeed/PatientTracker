package com.demo.user1.patient;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    String userId;
    TabLayout tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        InitializeWidgets();
        InitToolbar();

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        tabs.setupWithViewPager(pager);

        fillNavigationHeader();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.profile:
                        Intent userIdIntent = new Intent(MainActivity.this, ContactActivity.class);
                        startActivity(userIdIntent);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.refresh:
                        drawerLayout.closeDrawers();
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
    }


    private void fillNavigationHeader() {
        View header = navigationView.inflateHeaderView(R.layout.sidemenu_header1);

        TextView tvUsername = (TextView) header.findViewById(R.id.name);
        CircleImageView image = (CircleImageView) header.findViewById(R.id.userimage);
    }


    public void InitializeWidgets () {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        tabs = (TabLayout) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        navigationView = (NavigationView) findViewById(R.id.left_drawer);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void InitToolbar () {
        ImageView drawerImageView = (ImageView) findViewById(R.id.drawer_imageview);
        drawerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (drawerLayout.isDrawerOpen(navigationView)) {
                    drawerLayout.closeDrawer(navigationView);
                } else {
                    drawerLayout.openDrawer(navigationView);
                }
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                finish();
                return true;
            default:
                break;
        }
        return false;
    }



    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"Doctors", "Patients","Medication","History"};
        //,"Grids","Feeds","Side Menu","Find Friends","Settings","About","OnBoarding","Empty Views","Splash Screens","Chat Screens"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch (TITLES[position]) {
                case "Doctors":
                    fragment = new DoctorsFragment();
                    break;
                case "Patients":
                    fragment = new PatientFragment();
                    break;
                case "Medication":
                    fragment = new MedicationFragment();
                    break;
                case "History":
                    fragment = new MedicalHistoryFragment();
                    break;
            }
            return fragment;
        }

    }
}
