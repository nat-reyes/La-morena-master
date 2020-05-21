package com.example.lamorena.crud;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.lamorena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

public class registrarIngreso extends AppCompatActivity {


    FirebaseFirestore mfire;
    MaterialSpinner spinner,spinner2,spinner3;
    CollectionReference mquery;

    FirebaseFirestore db;
    private ArrayList<String> services;
    private ArrayList<String> empleados;
    private ArrayList<String> aseguradora;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_registrar_ingreso);
        spinner = (MaterialSpinner) findViewById(R.id.spinner_1);
        spinner2 = (MaterialSpinner) findViewById(R.id.spinner_2);
        spinner3 = (MaterialSpinner) findViewById(R.id.spinner_3);
        db = FirebaseFirestore.getInstance();

        getServices();
getEmpleados();
getAseguradoras();
        metodo();

    }




    public void getServices(){
        services = new ArrayList<>();

        mquery = db.collection("Servicios");
        mquery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        DocumentReference docRef =  db.collection("Servicios").document(document.getId());
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot document = task.getResult();
                                    if(document.exists()){
                                        for (String clave: document.getData().keySet()){
                                            if(clave.equalsIgnoreCase("Nombre")){
                                                String value = (String) document.getData().get(clave);
                                                services.add(value);
                                            }
                                        }
                                    }else{
                                        Log.d("tag", "Document snapshot"+document.getData());
                                    }
                                }else{
                                    Log.d("TAG", "No such document");
                                }
                            }
                        });
                    }
                } else{
                    Log.w("Cuentas:", "Error getting doc.", task.getException());
                }
            }
        });

       // mfire.collection("Services").addSnapshotListener()

    }





    public void getAseguradoras(){
        aseguradora= new ArrayList<>();

        mquery = db.collection("Aseguradoras");
        mquery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        DocumentReference docRef =  db.collection("Aseguradoras").document(document.getId());
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot document = task.getResult();
                                    if(document.exists()){
                                        for (String clave: document.getData().keySet()){
                                            if(clave.equalsIgnoreCase("Name")){
                                                String value = (String) document.getData().get(clave);
                                                aseguradora.add(value);
                                            }
                                        }
                                    }else{
                                        Log.d("tag", "Document snapshot"+document.getData());
                                    }
                                }else{
                                    Log.d("TAG", "No such document");
                                }
                            }
                        });
                    }
                } else{
                    Log.w("Cuentas:", "Error getting doc.", task.getException());
                }
            }
        });

        // mfire.collection("Services").addSnapshotListener()

    }




    public void getEmpleados(){
        empleados = new ArrayList<>();

        mquery = db.collection("empleados");
        mquery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        DocumentReference docRef =  db.collection("empleados").document(document.getId());
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot document = task.getResult();
                                    if(document.exists()){
                                        //rol
                                        int num = 0;
                                        //nombre
                                        String fullname = "";
                                        String name = "";
                                        for (String clave: document.getData().keySet()){
                                            if(clave.equalsIgnoreCase("Nombre")){
                                                String value = (String) document.getData().get(clave);
                                                name =value+" ";

                                            }

                                            if(clave.equalsIgnoreCase("Apellido")){
                                                String value = (String) document.getData().get(clave);
                                                //   System.out.println("z3"+clave+"-"+document.getData().get(clave));
                                                fullname =value+" ";
                                                name = name+" "+fullname;
                                                //  System.out.println("Guardo---"+"-"+names.get(num))
                                                empleados.add(name);
                                                break;
                                            }
                                        }

                                    }else{
                                        Log.d("tag", "Document snapshot"+document.getData());
                                    }
                                }else{
                                    Log.d("TAG", "No such document");
                                }
                            }
                        });
                    }
                } else{
                    Log.w("Cuentas:", "Error getting doc.", task.getException());
                }
            }
        });

    }








    @RequiresApi(api = Build.VERSION_CODES.M)
    public void metodo() {

        spinner.setItems(services);
        int arrowColor = Color.parseColor("#fbc02d");
        spinner.setArrowColor(arrowColor);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Selecciono " + item, Snackbar.LENGTH_LONG).show();
            }
        });


        spinner2.setItems(empleados);
        spinner2.setArrowColor(arrowColor);
        spinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Selecciono " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        spinner3.setItems(aseguradora);
        spinner3.setArrowColor(arrowColor);
        spinner3.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Selecciono " + item, Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
