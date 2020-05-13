package com.example.lamorena.crud;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lamorena.Adapters.EmpleadoAdapter;
import com.example.lamorena.Fragments.listEmployeeFragment;
import com.example.lamorena.R;

import java.util.ArrayList;

public class listEmpleado extends AppCompatActivity {
    private ListView listview;
    private ArrayList<String> names;
    EmpleadoAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_fragment_em);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Listado");


        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");


        RecyclerView recyclerView = findViewById(R.id.recycleEmpleados);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EmpleadoAdapter(this, animalNames);
        recyclerView.setAdapter(adapter);

//
//        listEmployeeFragment listFragment = (listEmployeeFragment) getSupportFragmentManager().findFragmentById(R.id.listEmpleados);
//
//        if(listFragment==null){
//            listFragment = listEmployeeFragment.newInstance();
//            getSupportFragmentManager().beginTransaction().
//                    add(R.id.listEmpleados, listFragment).
//                    commit();
//
//        }




    }



}
