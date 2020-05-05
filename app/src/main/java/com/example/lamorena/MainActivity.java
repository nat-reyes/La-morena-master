package com.example.lamorena;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.example.lamorena.Activities.CartActivity;
import com.example.lamorena.Activities.ProfileActivity;
import com.example.lamorena.Activities.Purchases;
import com.example.lamorena.Fragments.ProductFragment;
import com.example.lamorena.R;
import com.example.lamorena.Activities.Login;
import com.example.lamorena.Fragments.Categories;
import com.example.lamorena.Fragments.Offers;
import com.example.lamorena.Helpers.Utils;
import com.example.lamorena.SQLite.ConectionSQLiteHelper;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Categories.OnFragmentInteractionListener, ProductFragment.OnFragmentInteractionListener, Offers.OnFragmentInteractionListener {

    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView username, userLevel;
    private ImageView userPhoto;
    private ConectionSQLiteHelper conectionSQLiteHelper;
    private NavigationView navigationView;
    private DrawerLayout drawer;

    private FirebaseAuth mAuth;
    private GoogleSignInAccount acct;
    private GoogleSignInClient mGoogleSignInClient;

    private String userPhotoUrl;
    private String userName;
    private String userId;
    private String userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        appBarLayout = (AppBarLayout) findViewById(R.id.appBar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabTextColors(Color.parseColor("#fafafa"),Color.parseColor("#ffffff"));
//        tabLayout.setBackgroundColor(Color.parseColor("#484848"));
  //      appBarLayout.addView(tabLayout);

        viewPager = (ViewPager) findViewById(R.id.pager);
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

//        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
//        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
//        tabs.setupWithViewPager(pager);

        initializationVariables();

    }

    private void showProfilePhoto (final ImageView userPhoto , String link){

        Utils.LoadPhotoAsync loadPhotoAsync = new Utils.LoadPhotoAsync(){

            @Override
            protected void onPostExecute(Bitmap bmp) {
                super.onPostExecute(bmp);

                Bitmap bm = bmp;
                userPhoto.setImageBitmap(bm);
            }
        };

        loadPhotoAsync.execute(link);
    }

    private void initializationVariables() {
        mAuth = FirebaseAuth.getInstance();
        acct = GoogleSignIn.getLastSignedInAccount(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        conectionSQLiteHelper = new ConectionSQLiteHelper(this,"bd_user",null,2);
        View view = navigationView.getHeaderView(0);
        username = (TextView) view.findViewById(R.id.userName);
        userLevel = (TextView) view.findViewById(R.id.userLevel);
        userPhoto = (ImageView) view.findViewById(R.id.userPhoto);
        //setHeader(username,userLevel,userPhoto);
        setHeaderData(username,userLevel,userPhoto);

    }

    private void setHeader(TextView username,TextView userLevel,ImageView userPhoto) {
        SQLiteDatabase sqLiteDatabase = conectionSQLiteHelper.getReadableDatabase();
        String userId = getIntent().getStringExtra("userId");
        String [] parameters = {userId};
        String [] fields = {Utils.ATRIBUTE_USER_FIRSTNAME,Utils.ATRIBUTE_USER_PICTURE};
        Cursor cursor = sqLiteDatabase.query(Utils.TABLE_USER,fields,Utils.ATRIBUTE_USER_ID+"=?",parameters,null,null,null);
        cursor.moveToFirst();
        username.setText(getResources().getString(R.string.hi)+" "+cursor.getString(0));
        showProfilePhoto(userPhoto,cursor.getString(1));
        cursor.close();
    }

    private void setHeaderData (TextView username,TextView userLevel,ImageView userPhoto){
        userId = getIntent().getStringExtra("userId");
        userPhotoUrl = getIntent().getStringExtra("userPhoto");
        userName = getIntent().getStringExtra("userName");
        userEmail = getIntent().getStringExtra("userEmail");

        username.setText(getResources().getString(R.string.hi)+" "+userName);
        if(userPhotoUrl!=null || !userPhotoUrl.isEmpty()) showProfilePhoto(userPhoto,userPhotoUrl);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter{

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        String [] tituloTabs = {getResources().getString(R.string.categories),getResources().getString(R.string.offers)};

        @Override
        public Fragment getItem(int i) {

            switch (i){
                case 0: return new Categories();
                case 1: return new Offers();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        public CharSequence getPageTitle ( int position){
            return tituloTabs[position];
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_notification) {
//            return true;
//        }else if (id == R.id.action_search) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.logout){
            SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            LoginManager.getInstance().logOut();
            mAuth.signOut();
            if (mGoogleSignInClient!=null){
                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Utils.GoToNextActivityCleanStack(MainActivity.this, Login.class,true,null);
                    }
                });
            }else{
                Utils.GoToNextActivityCleanStack(MainActivity.this, Login.class,true,null);
            }

        }else if(id == R.id.account){
            ArrayList<Utils.Extra> extras = new ArrayList<>();
            extras.add(new Utils.Extra("userId",userId));
            extras.add(new Utils.Extra("userPhoto",userPhotoUrl));
            extras.add(new Utils.Extra("userName",userName));
            extras.add(new Utils.Extra("userEmail",userEmail));
            Utils.GoToNextActivityCleanStack(MainActivity.this, ProfileActivity.class,false,extras);
        }else if(id == R.id.ingreso){
            viewPager.setCurrentItem(0);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
