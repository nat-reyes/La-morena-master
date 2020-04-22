package com.example.lamorena.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lamorena.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShipmentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShipmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShipmentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ShipmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShipmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShipmentFragment newInstance(String param1, String param2) {
        ShipmentFragment fragment = new ShipmentFragment();
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
        View view =  inflater.inflate(R.layout.fragment_shipment, container, false);
        setActiveColor();
        setDisableColor();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setActiveColor();
        setDisableColor();
    }

    private void setActiveColor (){
        ImageView iconShipment = getActivity().findViewById(R.id.iconShipment);
        TextView textShipment = getActivity().findViewById(R.id.textShipment);
        iconShipment.setColorFilter(getResources().getColor(R.color.colorAccent));
        textShipment.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    private void setDisableColor (){
        ImageView iconOrder = getActivity().findViewById(R.id.iconOrder);
        //iconOrder.setImageDrawable(getResources().getDrawable(R.drawable.ic_tick));
        TextView textOrder = getActivity().findViewById(R.id.textOrder);
        iconOrder.setColorFilter(getResources().getColor(R.color.white));
        textOrder.setTextColor(getResources().getColor(R.color.white));

        ImageView iconPayment = getActivity().findViewById(R.id.iconPayment);
        TextView textPayment = getActivity().findViewById(R.id.textPayment);
        iconPayment.setColorFilter(getResources().getColor(R.color.white));
        textPayment.setTextColor(getResources().getColor(R.color.white));
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
