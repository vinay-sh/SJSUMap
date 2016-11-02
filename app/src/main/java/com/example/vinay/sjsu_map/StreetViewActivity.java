package com.example.vinay.sjsu_map;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Vinay on 10/29/2016.
 */

public class StreetViewActivity extends AppCompatActivity {

    public final static String B_LATITUDE = "com.example.vinay.sjsu_map.B_LATITUDE";
    public final static String B_LONGITUDE = "com.example.vinay.sjsu_map.B_LONGITUDE";
    private double lat, lng;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_streetview);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setHasOptionsMenu(true);

        lat = getIntent().getDoubleExtra(B_LATITUDE, 37.3339968);
        lng = getIntent().getDoubleExtra(B_LONGITUDE, -121.9038523);

        StreetViewPanoramaFragment streetViewPanoramaFragment =
                (StreetViewPanoramaFragment) getFragmentManager()
                        .findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(new OnStreetViewPanoramaReadyCallback() {

            @Override
            public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
               if(savedInstanceState == null)
                    panorama.setPosition(new LatLng(lat,lng));
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem m){
        switch(m.getItemId()){
            case android.R.id.home:
                if (NavUtils.getParentActivityName(this) != null) {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(m);
        }
    }


}
