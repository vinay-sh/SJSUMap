package com.example.vinay.sjsu_map;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Vinay on 10/29/2016.
 */

public class StreetViewActivity extends AppCompatActivity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_streetview);

        StreetViewPanoramaFragment streetViewPanoramaFragment =
                (StreetViewPanoramaFragment) getFragmentManager()
                        .findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(new OnStreetViewPanoramaReadyCallback() {

            @Override
            public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
               if(savedInstanceState == null)
                    panorama.setPosition(new LatLng(37.3353492,-121.8841295));
            }

        });
    }


}
