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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private List<Building> buildingList;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View v;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private double ImageExtentLeft = -121.886032;
    private double ImageExtentRight = -121.879503;
    private double ImageExtentBottom = 37.334515;
    private double ImageExtentTop = 37.338823;
    private int image_width = 1070;
    private int image_height = 850;

    private OnFragmentInteractionListener mListener;

    public MapViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapViewFragment newInstance(String param1, String param2) {
        MapViewFragment fragment = new MapViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("I AM INSIDE CREATE MAP VIEW FRAGMENT");
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

        System.out.println("Value of X is "+calculateX(-121.8817131));
        System.out.println("Value of Y is "+calculateY(37.3367211));

        //CalculatingDistance cd = new CalculatingDistance();
       // cd.SendRequest(37.3367211, 37.3367211, 37.3367211, 37.3367211);
       // new AsyncTaskGoogleMapAPI().execute(37.3367211, -121.8817131,37.3367311, -121.8817431);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
                }

                return true;
            }

        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

        //return true;
    }

}
