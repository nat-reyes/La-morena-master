package com.example.lamorena.crud;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lamorena.R;

import java.util.ArrayList;

public class listEmpleado extends AppCompatActivity {
    private ListView listview;
    private ArrayList<String> names;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_empleados);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Listado");

        listview = (ListView) findViewById(R.id.listEmpleados);
       llenarDatos();
      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
    listview.setAdapter(adapter);

    }

    public void llenarDatos(){

        names = new ArrayList<String>();
        names.add("Item 1");
        names.add("Item 2");
        names.add("Item 3");
        names.add("Item 4");
    }


}
