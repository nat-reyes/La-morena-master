package com.example.lamorena.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.lamorena.R;

import java.util.HashMap;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class listEmployeeFragment extends Fragment {

    private ListView empleadosView;
    public listEmployeeFragment() {
        // Required empty public constructor
    }

    public static listEmployeeFragment newInstance(/*parámetros*/) {
        listEmployeeFragment fragmentEmpleado = new listEmployeeFragment();
        return fragmentEmpleado;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Gets parámetros
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("oncreateview");
        View root = inflater.inflate(R.layout.list_fragment_em, container, false);

       // empleadosView = (ListView) root.findViewById(R.id.leads_list);
        String[] leadsNames = {
                "Alexander Pierrot",
                "Carlos Lopez",
                "Sara Bonz",
                "Liliana Clarence",
                "Benito Peralta",
                "Juan Jaramillo",
                "Christian Steps",
                "Alexa Giraldo",
                "Linda Murillo",
                "Lizeth Astrada"
        };

        ArrayAdapter <String> listAdapter;

        listAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                leadsNames);
        empleadosView.setAdapter(listAdapter);
        return root;
    }

    }



