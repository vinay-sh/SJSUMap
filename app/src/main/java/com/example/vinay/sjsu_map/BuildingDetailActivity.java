package com.example.vinay.sjsu_map;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BuildingDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Inside BuildingDetailActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_detail);
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
