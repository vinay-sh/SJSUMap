package com.example.vinay.sjsu_map;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MapViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

//        Button btn = (Button)findViewById(R.id.location_button);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(this, CalculatingDistance.class);
//                startActivity(i);
//            }
//        });

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if (fragment == null){
            fragment = new MapViewFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();

            List<Building> buildingList = new ArrayList<Building>() {
                {
                    add(new Building("King Library", "kinglibrary", "Dr. Martin Luther King, Jr. Library, 150 East San Fernando Street, San Jose, CA 95112", "KING"));
                    add(new Building("Engineering Building", "engglibrary", "San Jose State University Charles W. Davidson College of Engineering, 1 Washington Square, San Jose, CA 95112", "EB"));
                    add(new Building("Yoshihiro Uchida Hall", "yuh", "Yoshihiro Uchida Hall, San Jose, CA 95112", "YUH"));
                    add(new Building("Student Union", "su", "Student Union Building, San Jose, CA 95112", "SU"));
                    add(new Building("BBC", "bbc", "Boccardo Business Complex, San Jose, CA 95112", "BBC"));
                    add(new Building("South Parking Garage", "spg", "San Jose State University South Garage, 330 South 7th Street, San Jose, CA 95112", "SPG"));
                }
            };

        }

    }
}
