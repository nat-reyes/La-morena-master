package com.example.lamorena.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.lamorena.Fragments.BottomSheetDialogFragment;
import com.example.lamorena.Helpers.Utils;
import com.example.lamorena.SQLite.ConectionSQLiteHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lamorena.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.lamorena.Helpers.Utils.MODAL_EMAIL;
import static com.example.lamorena.Helpers.Utils.MODAL_ID;
import static com.example.lamorena.Helpers.Utils.MODAL_NAME;
import static com.example.lamorena.Helpers.Utils.MODAL_PHONE;

public class ProfileActivity extends AppCompatActivity {

    private ImageView userPhoto;
    private TextView name, email, phoneNumber, identificationCard;
    private ConectionSQLiteHelper conectionSQLiteHelper;
    private Toolbar toolbar;
    private BottomSheetDialogFragment bottomSheetFragment;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
        initializationVariables();
        addToolbar();
        addFAB();
        setHeaderData(userPhoto);
        setDataUser();
    }

    private void setDataUser() {
         user = FirebaseAuth.getInstance().getCurrentUser();

    }

    private void  initializationVariables (){
        conectionSQLiteHelper = new ConectionSQLiteHelper(this,"bd_user",null,2);
        userPhoto = (ImageView) findViewById(R.id.profile_image);
        name = (TextView) findViewById(R.id.userName);
        email = (TextView) findViewById(R.id.userEmail);
        phoneNumber = (TextView) findViewById(R.id.userPhoneNumber);
        identificationCard = (TextView) findViewById(R.id.userID);

    }

    private void addFAB (){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void addToolbar (){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        int colorNavigationIcon = getResources().getColor(R.color.secondaryTextColor);
        Drawable iconNavigation = getResources().getDrawable(R.drawable.ic_left_arrow);
        iconNavigation.setColorFilter(colorNavigationIcon, PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(iconNavigation);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.secondaryTextColor));
        setSupportActionBar(toolbar);
        onClickNavigation();
    }

    private void onClickNavigation (){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void setHeader(ImageView userPhoto) {
        SQLiteDatabase sqLiteDatabase = conectionSQLiteHelper.getReadableDatabase();
        SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String userId = preferences.getString("userId","01");
        String [] parameters = {userId};
        String [] fields = {Utils.ATRIBUTE_USER_FIRSTNAME,Utils.ATRIBUTE_USER_PICTURE};
        Cursor cursor = sqLiteDatabase.query(Utils.TABLE_USER,fields,Utils.ATRIBUTE_USER_ID+"=?",parameters,null,null,null);
        cursor.moveToFirst();
       // toolbar.setTitle(cursor.getString(0));
        showProfilePhoto(userPhoto,cursor.getString(1));
        cursor.close();
    }

    private void setHeaderData ( ImageView userPhoto){
        String userId = getIntent().getStringExtra("userId");
        String userPhotoUrl = getIntent().getStringExtra("userPhoto");
        String userName = getIntent().getStringExtra("userName");
        String userEmail = getIntent().getStringExtra("userEmail");

        if(userPhotoUrl!=null || !userPhotoUrl.isEmpty()) showProfilePhoto(userPhoto,userPhotoUrl);
    }

    public void showFragmentBottomSheetName(View view){
        System.out.println(getIntent().getStringExtra("userName"));
        bottomSheetFragment = new BottomSheetDialogFragment(MODAL_NAME,getIntent().getStringExtra("userName") );
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    public void showFragmentBottomSheetEmail(View view){
        bottomSheetFragment = new BottomSheetDialogFragment(MODAL_EMAIL, getIntent().getStringExtra("userEmail"));
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    public void showFragmentBottomSheetPhone(View view){
        // No tengo numero de telefono en google
        bottomSheetFragment = new BottomSheetDialogFragment(MODAL_PHONE, getIntent().getStringExtra(""));
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    public void showFragmentBottomSheetID(View view){
        // No tengo id card para este usuario
        bottomSheetFragment = new BottomSheetDialogFragment(MODAL_ID, getIntent().getStringExtra("") );
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    public void dismissFragmentBottomSheet (View view){
        bottomSheetFragment.dismiss();
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
}
