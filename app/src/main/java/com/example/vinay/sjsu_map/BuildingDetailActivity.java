package com.example.vinay.sjsu_map;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class BuildingDetailActivity extends AppCompatActivity {

    private static final String TAG = "BuildingDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Inside BuildingDetailActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_detail);
        Log.i(TAG, "BuildindDetailActivity's onCreate");
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.buildingDetailsFragmentContainer);

        if (fragment == null) {
            fragment = new BuildingDetailFragment();
            fm.beginTransaction()
                    .add(R.id.buildingDetailsFragmentContainer, fragment)
                    .commit();

        }
    }
}
