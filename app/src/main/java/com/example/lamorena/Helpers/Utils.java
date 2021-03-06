package com.example.lamorena.Helpers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.lamorena.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    public  static Dialog dialog;

    //String Database
    public static final String TABLE_USER="user";
    public static final String ATRIBUTE_USER_ID="id";
    public static final String ATRIBUTE_USER_IDCARD="idCard";
    public static final String ATRIBUTE_USER_FIRSTNAME="firstName";
    public static final String ATRIBUTE_USER_LASTNAME="lastname";
    public static final String ATRIBUTE_USER_EMAIL="email";
    public static final String ATRIBUTE_USER_PASSWORD="password";
    public static final String ATRIBUTE_USER_BIRTHDAY="birthDay";
    public static final String ATRIBUTE_USER_GENDER="gender";
    public static final String ATRIBUTE_USER_NUMBER="number";
    public static final String ATRIBUTE_USER_ADDRES="addres";
    public static final String ATRIBUTE_USER_TYPE="type";
    public static final String ATRIBUTE_USER_TOKEN="token";
    public static final String ATRIBUTE_USER_PICTURE="picture";

    public static final String CREATE_TABLE_USER = "CREATE TABLE "+TABLE_USER+"("+ATRIBUTE_USER_ID+" TEXT,"+ATRIBUTE_USER_IDCARD+" TEXT,"+ATRIBUTE_USER_FIRSTNAME+
                                                    " TEXT, "+ATRIBUTE_USER_LASTNAME+" TEXT, "+ATRIBUTE_USER_EMAIL+" TEXT, "+ATRIBUTE_USER_PASSWORD+" TEXT,"+ATRIBUTE_USER_BIRTHDAY+
                                                    " TEXT, "+ATRIBUTE_USER_GENDER+" TEXT, "+ATRIBUTE_USER_NUMBER+" TEXT,"+ATRIBUTE_USER_ADDRES+" TEXT, "+ATRIBUTE_USER_TYPE+" TEXT, "+ATRIBUTE_USER_TOKEN+" TEXT, "+ATRIBUTE_USER_PICTURE+" TEXT)";



    //Static String Modal
    public static final String MODAL_NAME="name";
    public static final String MODAL_EMAIL="email";
    public static final String MODAL_PHONE="phone";
    public static final String MODAL_ID="id";
    public static final String MODAL_ADDCART="cart";

    //Static String Card Product
    public static final String CLICKCARD="cardProduct";
    public static final String CLICKADDCART="addCartProduct";

    //Static String Card Product Cart
    public static final String CLICKCARDCART="cardProductCart";
    public static final String CLICKPLUSCART="plusProductCart";
    public static final String CLICKSUSTRACTCART="sustractProductCart";




    public Utils() {
    }



    public static void snackBarAndContinue (String msg, int tiempo , final Activity actual , final Class toGo, final boolean finaliza, final ArrayList<Extra> params ){
        try{
            View contextView = actual.getCurrentFocus();
            if(contextView != null){
                Snackbar.make(contextView, msg, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GoToNextActivityCleanStack(actual,toGo,finaliza,params);
            }
        }, tiempo);

    }

    public static void showSnackbar (View v, Context context, int idString){
        Snackbar.make(v, context.getResources().getString(idString), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public static void GoToNextActivityCleanStack(Activity activity, Class clase, boolean finaliza, ArrayList<Extra> params)
    {
        Intent intent = new Intent(activity, clase ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if(params!=null){
            for (Extra param: params) {
                intent.putExtra(param.getClave(),param.getValor());
            }
        }
        activity.startActivity(intent);

        if (finaliza){
            activity.finish();
        }
    }


    public static boolean veirifyConnection (Activity activity){
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            View contextView = activity.findViewById(R.id.context_view);
            Snackbar.make(contextView, activity.getResources().getString(R.string.errConnect), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }

    }

    public static class LoadPhotoAsync extends AsyncTask<String , Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static class Extra {
        private String clave;
        private String valor;

        public Extra(String clave, String valor) {
            this.clave = clave;
            this.valor = valor;
        }

        public String getClave() {
            return clave;
        }

        public void setClave(String clave) {
            this.clave = clave;
        }

        public String getValor() {
            return valor;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }
    }

    public static void saveUserFirebaseDatabase(FirebaseUser user, FirebaseFirestore db){
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("UserId",user.getUid());
        Log.e("user", user.getEmail());
        Log.e("user", user.getPhoneNumber());
        Log.e("id",  user.getUid());
        Log.e("db",  db.toString());
        userMap.put("Name",user.getDisplayName());
        userMap.put("Email",user.getEmail());
        userMap.put("Phone",user.getPhoneNumber());

        db.collection("Users").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("DBFirebase", "DocumentSnapshot added with ID: " + documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("DBFirebase", "Error adding document", e);

            }
        });
    }

    public static void saveUserGoogleFirebaseDatabase(GoogleSignInAccount acct, FirebaseFirestore db){
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("UserId",acct.getId());
        userMap.put("Name",acct.getDisplayName());
        userMap.put("Email",acct.getEmail());

        db.collection("Users").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("DBFirebase", "DocumentSnapshot added with ID: " + documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("DBFirebase", "Error adding document", e);

            }
        });
    }
}
