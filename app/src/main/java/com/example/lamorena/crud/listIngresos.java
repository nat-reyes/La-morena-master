package com.example.lamorena.crud;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lamorena.Activities.IngresoItem;
import com.example.lamorena.Adapters.EmpleadoAdapter;
import com.example.lamorena.Adapters.IngresoAdapter;
import com.example.lamorena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class listIngresos extends AppCompatActivity {

    private ListView listview;
    List<IngresoItem> mData;
    private IngresoAdapter adapter;
    private CollectionReference mquery;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mData = new ArrayList<>();
        setContentView(R.layout.list_frag_ingreso);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Listado");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        getData();


    }


    public void getData() {

        mquery = db.collection("Ingreso");
        mquery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DocumentReference docRef = db.collection("Ingreso").document(document.getId());
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        String placa = "";
                                        Object estado = true;

                                        for (String clave : document.getData().keySet()) {

                                            if (clave.equalsIgnoreCase("Placa")) {
                                                placa = (String) document.getData().get(clave);
                                            }
                                            if (clave.equalsIgnoreCase("Estado")) {
                                                estado = document.getData().get(clave);

                                            }

                                        }
                                        System.out.println(placa+estado.toString());
                                        IngresoItem item = new IngresoItem(R.drawable.ic_revision, placa, estado.toString());
                                        mData.add(item);
                                        cargarRecycle(mData);

                                    } else {
                                        Log.d("tag", "Document snapshot" + document.getData());
                                    }
                                } else {
                                    Log.d("TAG", "No such document");
                                }
                            }
                        });
                    }
                } else {
                    Log.w("Cuentas:", "Error getting doc.", task.getException());
                }
            }
        });

    }

    public void cargarRecycle(List<IngresoItem> mData) {
        RecyclerView recyclerView = findViewById(R.id.recycleIngresos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new IngresoAdapter(this, mData);
        recyclerView.setAdapter(adapter);

    }
}


