package com.example.vinay.sjsu_map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BuildingDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BuildingDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuildingDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String NAME="com.example.vinay.sjsu_map.name";
    public static final String IMAGE="com.example.vinay.sjsu_map.image";
    public static final String ADDRESS="com.example.vinay.sjsu_map.address";
    public static final String DISTANCE="com.example.vinay.sjsu_map.distance";
    public static final String TIMENEEDED="com.example.vinay.sjsu_map.timeneeded";
    private String buildingName;
    private String buildingAddress;
    private String buildingImage;
    private int timeNeeded;
    private int distance;
    private ImageView buildingImageView;
    private TextView buildingAddressTextView;
    private TextView distanceTextView;
    private TextView timeNeededTextView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BuildingDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuildingDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuildingDetailFragment newInstance(String param1, String param2) {
        BuildingDetailFragment fragment = new BuildingDetailFragment();
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
        System.out.println("Inside building details");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View v = inflater.inflate(R.layout.fragment_building_detail, container, false);
        buildingName = (String) getActivity().getIntent().getSerializableExtra(NAME);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(buildingName);

        buildingAddress = (String) getActivity().getIntent().getSerializableExtra(ADDRESS);
        buildingAddressTextView = (TextView) v.findViewById(R.id.buildingAddress);
        buildingAddressTextView.setText(buildingAddress);

        buildingImage = (String) getActivity().getIntent().getSerializableExtra(IMAGE);
        buildingImageView = (ImageView) v.findViewById(R.id.buildingImage);
        int id = getResources().getIdentifier("com.example.vinay.sjsu_map:drawable/" + buildingImage, null, null);
        buildingImageView.setImageResource(id);

        timeNeeded = (int) getActivity().getIntent().getSerializableExtra(TIMENEEDED);
        timeNeededTextView = (TextView) v.findViewById(R.id.timeTaken);
        timeNeededTextView.setText(timeNeeded+" Hours");

        distance = (int) getActivity().getIntent().getSerializableExtra(DISTANCE);
        distanceTextView = (TextView) v.findViewById(R.id.distanceToBuilding);
        distanceTextView.setText(distance + " miles");


        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
