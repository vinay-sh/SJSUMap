package com.example.vinay.sjsu_map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MapViewFragment extends Fragment {

    private List<Building> buildingList;
    private double ImageExtentLeft = -121.886032;
    private double ImageExtentRight = -121.879503;
    private double ImageExtentBottom = 37.334515;
    private double ImageExtentTop = 37.338823;
    private int image_width = 1070;
    private int image_height = 850;
    private View v;

   // private OnFragmentInteractionListener mListener;

    public MapViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("I AM INSIDE CREATE MAP VIEW FRAGMENT");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        buildingList = new ArrayList<Building>() {
            {
                add(new Building("King Library", "kinglibrary", "Dr. Martin Luther King, Jr. Library, 150 East San Fernando Street, San Jose, CA 95112", "KING", -959));
                add(new Building("Engineering Building", "engg", "San Jose State University Charles W. Davidson College of Engineering, 1 Washington Square, San Jose, CA 95112", "EB",-49133));
                add(new Building("Yoshihiro Uchida Hall", "yuh", "Yoshihiro Uchida Hall, San Jose, CA 95112", "YUH", -4641443));
                add(new Building("Student Union", "su", "Student Union Building, San Jose, CA 95112", "SU", -6739523));
                add(new Building("BBC", "bbc", "Boccardo Business Complex, San Jose, CA 95112", "BBC", -11722061));
                add(new Building("South Parking Garage", "spg", "San Jose State University South Garage, 330 South 7th Street, San Jose, CA 95112", "SPG", -38400));
            }
        };

        //CalculatingDistance cd = new CalculatingDistance();
        // cd.SendRequest(37.3367211, 37.3367211, 37.3367211, 37.3367211);

        // new AsyncTaskGoogleMapAPI().execute(37.3367211, -121.8817131,37.3367311, -121.8817431);

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_map_view, container, false);
        v.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){

                final int xpos = (int) event.getX();
                final int ypos = (int) event.getY();

                int colorInt = getHotspotColor(xpos,ypos);
                System.out.println("The value is " +colorInt);
                System.out.println("The value is "+xpos+","+ypos);

                ListIterator<Building> iterator= buildingList.listIterator();


                while(iterator.hasNext()){
                    Building currBuilding = (Building)iterator.next();
                    if(colorInt == currBuilding.getColor()){
                        Intent detailActivityIntent = new Intent(getActivity(), BuildingDetailActivity.class);
                        detailActivityIntent.putExtra(BuildingDetailFragment.NAME, currBuilding.getName());
                        detailActivityIntent.putExtra(BuildingDetailFragment.ADDRESS, currBuilding.getAddress());
                        detailActivityIntent.putExtra(BuildingDetailFragment.DISTANCE, 10);
                        detailActivityIntent.putExtra(BuildingDetailFragment.TIMENEEDED, 2);
                        detailActivityIntent.putExtra(BuildingDetailFragment.IMAGE, currBuilding.getImage());
                        startActivity(detailActivityIntent);
                        break;
                    }
//
//                int pointClicked = getHotspotColor(xpos, ypos);
//                System.out.println("******** The value of the hotspot is " +pointClicked);
//                System.out.println("The value is "+xpos+","+ypos);
//
//                //**Vinay.. Executing the AysncTask
////                System.out.println("Value of X is "+calculateX(-121.8817131));
////                System.out.println("Value of Y is "+calculateY(37.3367211));
//
//                //new AsyncTaskGoogleMapAPI().execute(37.3367211, -121.8817131,37.3367311, -121.8817431);
//
//                String abc = "null";
//                int flag=0;
//
//                switch(pointClicked){
//                    case -959:
//                        abc="King Library";
//                        flag=1;
//                        break;
//                    case -49133:
//                        abc="Engineering Building";
//                        flag=1;
//                        break;
//                    case -4641443:
//                        abc="Yoshihiro Uchida Hall";
//                        flag=1;
//                        break;
//                    case -6739523:
//                        abc="Student Union";
//                        flag=1;
//                        break;
//                    case -11722061:
//                        abc="BBC";
//                        flag=1;
//                        break;
//                    case -38400:
//                        abc="South Parking Garage";
//                        flag=1;
//                        break;
//
//                }
//
//                if(flag==1) {
//                    Intent intent = new Intent(getActivity(), BuildingDetailActivity.class);
//                    startActivity(intent);
//>>>>>>> Stashed changes
                }

                return true;
            }

        });

        return v;
    }


    public int getHotspotColor ( int x, int y) {
        ImageView img = (ImageView) v.findViewById(R.id.image_areas);
        img.setDrawingCacheEnabled(true);
        Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
        img.setDrawingCacheEnabled(false);
        return hotspots.getPixel(x, y);
    }

    public double calculateX (double lon){
        double res = image_width * ( lon - ImageExtentLeft ) / (ImageExtentRight - ImageExtentLeft);
        System.out.println("Value of CalculateX is "+res);
        return res;
    }

    public double calculateY(double lat){
        double res = image_height * ( 1 - ( lat - ImageExtentBottom) / (ImageExtentTop - ImageExtentBottom));
        System.out.println("Value of CalculateY is "+res);
        return res+575;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu,inflater);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
                                              @Override
                                              public boolean onQueryTextChange(String newText){
                                                  System.out.println("inside text change"+newText);
                                                  return true;
                                              }

                                              @Override
                                              public boolean onQueryTextSubmit(String query){
                                                  System.out.println("inside on text submit"+query);
                                                  System.out.println("buildingList size"+buildingList.size());

                                                  ListIterator<Building> iterator= buildingList.listIterator();


                                                  while(iterator.hasNext()){
                                                      Building currBuilding = (Building)iterator.next();
                                                      if( query.equalsIgnoreCase(currBuilding.getName())){
                                                          System.out.println("The search matches"+currBuilding.getName());
                                                      }
                                                  }


                                                  return true;


                                              }
                                          }

        );
    }
}
