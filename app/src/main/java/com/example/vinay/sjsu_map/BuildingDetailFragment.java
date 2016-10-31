package com.example.vinay.sjsu_map;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import com.google.android.gms.location.LocationResult;

public class BuildingDetailFragment extends Fragment {

    public static final String NAME = "com.example.vinay.sjsu_map.name";
    public static final String IMAGE = "com.example.vinay.sjsu_map.image";
    public static final String ADDRESS = "com.example.vinay.sjsu_map.address";
    public static final String B_LATITUDE = "com.example.vinay.sjsu_map.blatitude";
    public static final String B_LONGITUDE = "com.example.vinay.sjsu_map.blongitude";
    public static final String U_LATITUDE = "com.example.vinay.sjsu_map.ulatitude";
    public static final String U_LONGITUDE = "com.example.vinay.sjsu_map.ulongitude";
    private String buildingName;
    private String buildingAddress;
    private String buildingImage;
    private double blatitude;
    private double blongitude;
    private double ulatitude;
    private double ulongitude;
    private ImageView buildingImageView;
    private TextView buildingAddressTextView;
    private TextView timeNeededTextView;
    private TextView distanceTextView;

    private AsyncTaskGoogleMapAPI asyncTask;

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

        //current Latitude and longitude of the user
        //getCurrentUserLocation();

//        GetMyLocation.LocationResult lr = new GetMyLocation.LocationResult() {
//            @Override
//            public void gotLocation(Location location) {
//                System.out.println("******Coordinates"+location.getLatitude()+" , "+location.getLongitude());
//            }
//        };
//
//        GetMyLocation myloc = new GetMyLocation();
//        myloc.getLocation(getContext(), lr);


        //Latitude and longitude of the building
        blatitude = (double) getActivity().getIntent().getSerializableExtra(B_LATITUDE);
        blongitude = (double) getActivity().getIntent().getSerializableExtra(B_LONGITUDE);

        ulatitude = (double) getActivity().getIntent().getSerializableExtra(U_LATITUDE);
        ulongitude = (double) getActivity().getIntent().getSerializableExtra(U_LONGITUDE);

        System.out.println("**********VINAY BuildingDetailFragment latitude and longitude is "+ulatitude+" & "+ulongitude);



        buildingName = (String) getActivity().getIntent().getSerializableExtra(NAME);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(buildingName);

        buildingAddress = (String) getActivity().getIntent().getSerializableExtra(ADDRESS);
        buildingAddressTextView = (TextView) v.findViewById(R.id.buildingAddress);
        buildingAddressTextView.setText(buildingAddress);

        buildingImage = (String) getActivity().getIntent().getSerializableExtra(IMAGE);
        buildingImageView = (ImageView) v.findViewById(R.id.buildingImage);
        int id = getResources().getIdentifier("com.example.vinay.sjsu_map:drawable/" + buildingImage, null, null);
        buildingImageView.setImageResource(id);

//        timeNeeded = (int) getActivity().getIntent().getSerializableExtra(TIMENEEDED);
        timeNeededTextView = (TextView) v.findViewById(R.id.timeTaken);
        timeNeededTextView.setText(0+" mins");

//        distance = (int) getActivity().getIntent().getSerializableExtra(DISTANCE);
        distanceTextView = (TextView) v.findViewById(R.id.distanceToBuilding);
        distanceTextView.setText(0 + " kms");

        asyncTask = new AsyncTaskGoogleMapAPI(timeNeededTextView, distanceTextView);
        asyncTask.execute(ulatitude, ulongitude, blatitude, blongitude);

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