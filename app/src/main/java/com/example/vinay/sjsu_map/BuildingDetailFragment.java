package com.example.vinay.sjsu_map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

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
    private String baddress;

    private AsyncTaskGoogleMapAPI asyncTask;

    private Button stview;

    public BuildingDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            System.out.println("Inside savedInstanceState not null");
            buildingName = savedInstanceState.getString("buildingname");
            baddress = savedInstanceState.getString("buildingaddress");
            buildingAddress = savedInstanceState.getString("buildingaddress");
            blatitude = savedInstanceState.getDouble("blatitude");
            blongitude = savedInstanceState.getDouble("blongitude");
            buildingImage = savedInstanceState.getString("image");
            ulatitude = savedInstanceState.getDouble("ulatitude");
            ulongitude = savedInstanceState.getDouble("ulongitude");

        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        System.out.println("inside onSaveInstanceState building details ");
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("buildingname", buildingName);
        savedInstanceState.putString("buildingaddress", buildingAddress);
        savedInstanceState.putDouble("blatitude", blatitude);
        savedInstanceState.putDouble("blongitude",blongitude);
        savedInstanceState.putDouble("ulatitude", ulatitude);
        savedInstanceState.putDouble("ulongitude",ulongitude);
        savedInstanceState.putString("image", buildingImage);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("Inside building details");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View v = inflater.inflate(R.layout.fragment_building_detail, container, false);
        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);

        //Latitude and longitude of the building
        baddress = (String) getActivity().getIntent().getSerializableExtra(ADDRESS);
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
        buildingAddressTextView.setTypeface(boldTypeface);

        buildingImage = (String) getActivity().getIntent().getSerializableExtra(IMAGE);
        buildingImageView = (ImageView) v.findViewById(R.id.buildingImage);
        int id = getResources().getIdentifier("com.example.vinay.sjsu_map:drawable/" + buildingImage, null, null);
        buildingImageView.setImageResource(id);

        timeNeededTextView = (TextView) v.findViewById(R.id.timeTaken);
        timeNeededTextView.setText(0+" mins");

        distanceTextView = (TextView) v.findViewById(R.id.distanceToBuilding);
        distanceTextView.setText(0 + " kms");

        asyncTask = new AsyncTaskGoogleMapAPI(timeNeededTextView, distanceTextView, baddress);
        asyncTask.execute(ulatitude, ulongitude);

        blatitude = (double) getActivity().getIntent().getSerializableExtra(B_LATITUDE);
        blongitude = (double) getActivity().getIntent().getSerializableExtra(B_LONGITUDE);

        stview = (Button)v.findViewById(R.id.streetview);
        stview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), StreetViewActivity.class);
                i.putExtra(StreetViewActivity.B_LATITUDE,blatitude);
                i.putExtra(StreetViewActivity.B_LONGITUDE,blongitude);
                startActivity(i);
            }
        });
        return v;
    }

    public void getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );
        } catch (Exception e) {
            e.printStackTrace();
        }

        blatitude = p1.latitude;
        blongitude = p1.longitude;
        System.out.println("**********VINAY Calculated Building's lat and lng "+blatitude+" & "+blongitude);

    }

}