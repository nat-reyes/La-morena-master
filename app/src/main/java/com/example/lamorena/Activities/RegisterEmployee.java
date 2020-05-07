package com.example.lamorena.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.lamorena.Entities.User;
import com.example.lamorena.Helpers.Url;
import com.example.lamorena.Helpers.Utils;
import com.example.lamorena.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterEmployee extends AppCompatActivity {


    private EditText cardId, name, email, rol, apellido, telefono;
    private Url url;
    private RequestQueue queue;
    private ProgressDialog progressDialog;
    private View view;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private User usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_employee);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        db = FirebaseFirestore.getInstance();
        cardId = (EditText) findViewById(R.id.input_cardId_employee);
        name = (EditText) findViewById(R.id.input_firstName_employee);
        email = (EditText) findViewById(R.id.input_email_employee);
        rol = (EditText) findViewById(R.id.input_cargo_employee);
        apellido = (EditText) findViewById(R.id.input_apellido_employee);
        telefono = (EditText) findViewById(R.id.input_cel_employee);
        mAuth = FirebaseAuth.getInstance();
        usuario = User.getInstance();
        url = new Url();
        //queue = Volley.newRequestQueue(RegistryUser.this);
        //Utils.veirifyConnection(this);
    }

    public void createAccountEmployee(View view) {
        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        String cardId = this.cardId.getText().toString();
        String email = this.email.getText().toString();
        String rol = this.rol.getText().toString();
        String name = this.name.getText().toString();
        String apellido = this.apellido.getText().toString();
        String telefono = this.telefono.getText().toString();
        FirebaseUser user = mAuth.getCurrentUser();
        if (Utils.veirifyConnection(this)) {
            System.out.println("ENCONTRO CONEXION");
            //showDialogWait(progressDialog);
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("Email",email);
            userMap.put("IdCard",cardId);
            userMap.put("Nombre",name);
            userMap.put("Apellido",apellido);
            userMap.put("Tel",telefono);
            userMap.put("Rol",rol);

            //serviceConnectLogin(email, password,name,cardId,view);
            Utils.saveUserFirebaseDatabase2(user,db,userMap);
            Utils.snackBarAndContinue(getResources().getString(R.string.userRegistrationOk),1000,this,Login.class,true,null);
        }

    }
    private void showDialogWait(ProgressDialog progressDialog) {
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getResources().getString(R.string.waitRegister));
        progressDialog.show();
    }

}

