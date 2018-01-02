package com.zht.album;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zht.album.fragment.HomeAttendanceFragment;
import com.zht.album.fragment.HomeClassesFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private FloatingActionButton fab;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private RelativeLayout relative_main;
    private ImageView img_page_start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        initview();

        evalu();

        mTabLayout = findViewById(R.id.tab_layout_main);
        mViewPager = findViewById(R.id.view_pager_main);

        List<String> titles = new ArrayList<>();
        titles.add("美图");
        titles.add("分类");
        //titles.add("收藏夹");
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        //mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeClassesFragment());
        fragments.add(new HomeAttendanceFragment());
        //fragments.add(new HomeExamFragment());

        evalu();

        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), (ArrayList<String>) titles,fragments);

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);

    }

    public void initview() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Designed by zht,slq,xf", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:zht1246@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "选择邮箱");
                //intent.putExtra(Intent.EXTRA_TEXT, "Hi,");
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "未找到邮箱", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initViewPager() {




    }

    public void evalu(){
        for (int i=0;i<3;i++){
            i++;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            Toast.makeText(getApplicationContext(),"Designed by zht,slq,xf", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent intent=new Intent(getApplicationContext(),com.github.skykai.ui.PhotoPickerActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "哈哈哈哈哈哈哈哈哈哈哈，6666666666666666");
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "分享方式"));

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

        private Context context;
        private List<Fragment> fragmentList=new ArrayList<>();
        private List views=null;
        private ArrayList<String> titleList=new ArrayList<>();

        public SimpleFragmentPagerAdapter(FragmentManager fm, ArrayList<String> titleList, List<Fragment> fragmentList) {
            super(fm);
            this.titleList=titleList;
            this.fragmentList=fragmentList;


            /*this.titleList=titleList;*/


        }

        @Override
        public int getCount() {
            return fragmentList.size();

            //return views.size();
        }

        //
        @Override
        public Fragment getItem(int position) {

            return fragmentList.get(position);

        }




        @Override
        public CharSequence getPageTitle(int position) {

            return titleList.get(position);
        }
    }
}
