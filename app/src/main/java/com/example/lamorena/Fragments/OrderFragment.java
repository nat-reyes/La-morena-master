package com.example.lamorena.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lamorena.Adapters.ProductAdapter;
import com.example.lamorena.Entities.Product;
import com.example.lamorena.Helpers.Utils;
import com.example.lamorena.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment  implements ProductAdapter.OnProductClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerViewOffers;
    private ProductAdapter productAdapter;
    private Button checkout;

    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<Product> products;

    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        initializeVariables(view);
        onClickCheckout(checkout);
        setActiveColor();
        setDisableColor();
        ArrayList<Product> products = testProducts();
        showProducts(products,view);
        return view;
    }

    private void initializeVariables(View view) {
        checkout = (Button) view.findViewById(R.id.checkoutButton);
    }

    public void onClickCheckout (Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment();
            }
        });
    }

    private void changeFragment (){
        Fragment newFragment = new ShipmentFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FragmentCartFrame, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        setActiveColor();
        setDisableColor();
    }

    private void setActiveColor (){
        ImageView iconOrder = getActivity().findViewById(R.id.iconOrder);
        TextView textOrder = getActivity().findViewById(R.id.textOrder);
        iconOrder.setColorFilter(getResources().getColor(R.color.colorAccent));
        textOrder.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    private void setDisableColor (){
        ImageView iconShipment = getActivity().findViewById(R.id.iconShipment);
        TextView textShipment = getActivity().findViewById(R.id.textShipment);
        iconShipment.setColorFilter(getResources().getColor(R.color.white));
        textShipment.setTextColor(getResources().getColor(R.color.white));

        ImageView iconPayment = getActivity().findViewById(R.id.iconPayment);
        TextView textPayment = getActivity().findViewById(R.id.textPayment);
        iconPayment.setColorFilter(getResources().getColor(R.color.white));
        textPayment.setTextColor(getResources().getColor(R.color.white));
    }



    private ArrayList<Product> testProducts (){
        products =  new ArrayList<>();
//        Product  product= new Product("1","Antioqueño","Aguardiente","100 ml",1000,20,19,2,"100 ml");
//        products.add(product);
//        Product  product2 =new Product("1","Viejo de Caldas","Ron","250 ml",1000,20,19,5,"250 ml");
//        products.add(product2);
//        Product  product3= new Product("1","Andina","Cerveza","300 ml",1000,20,19,3,"300 ml");
//        products.add(product3);
//        Product  product4= new Product("1","Absolut","Cerveza","300 ml",1000,20,19,3,"300 ml");
//        products.add(product4);
//        Product  product5= new Product("1","Solera","Cerveza","300 ml",1000,20,19,3,"300 ml");
//        products.add(product5);
//        Product  product6= new Product("1","Polar","Cerveza","300 ml",1000,20,19,3,"300 ml");
//        products.add(product6);
//        Product  product7= new Product("1","Aguila","Cerveza","300 ml",1000,20,19,3,"300 ml");
//        products.add(product7);
//        Product  product8= new Product("1","Aguila","Cerveza","300 ml",1000,20,19,3,"300 ml");
//        products.add(product8);
//        Product  product9= new Product("1","Aguila","Cerveza","300 ml",1000,20,19,3,"300 ml");
//        products.add(product9);
//        Product  product10= new Product("1","Aguila","Cerveza","300 ml",1000,20,19,3,"300 ml");
//        products.add(product10);
//        Product  product11= new Product("1","Aguila","Cerveza","300 ml",1000,20,19,3,"300 ml");
//        products.add(product11);
//        Product  product12= new Product("1","Aguila","Cerveza","300 ml",1000,20,19,3,"300 ml");
//        products.add(product12);
//        Product  product13= new Product("1","Aguila","Cerveza","300 ml",1000,20,19,3,"300 ml");
//        products.add(product13);
//        Product  product14= new Product("1","Aguila","Cerveza","300 ml",1000,20,19,3,"300 ml");
//        products.add(product14);
        return products;
    }

    private void showProducts (ArrayList<Product> products, View view){
        recyclerViewOffers = (RecyclerView) view.findViewById(R.id.recyclerCart);
        //recyclerViewCategory.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerViewOffers.setLayoutManager(new LinearLayoutManager(getContext()));
        productAdapter = new ProductAdapter(products,getActivity(),getContext(),this, ProductAdapter.TYPE_PRODUCTCART);
        recyclerViewOffers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewOffers.setAdapter(productAdapter);

    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onProductClick(int pos, String typeClick) {
        switch (typeClick){
            case Utils.CLICKPLUSCART:
                System.out.println("POSITION ADAPTER : "+pos);
                break;
            case Utils.CLICKADDCART:

                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
