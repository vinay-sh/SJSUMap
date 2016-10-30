package com.example.vinay.sjsu_map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

public class BuildingDetailFragment extends Fragment {

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

    private Button stview;

    public BuildingDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

//        stview = (Button)v.findViewById(R.id.button2);
//        stview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), StreetViewActivity.class);
//                startActivity(i);
//            }
//        });

        return v;
    }
}