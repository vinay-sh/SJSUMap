package com.example.vinay.sjsu_map;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MapViewFragment extends Fragment implements LocationListener{

    private List<Building> buildingList;
    private double ImageExtentLeft = -121.887608;
    private double ImageExtentRight = -121.876020;
    private double ImageExtentBottom = 37.3344444;
    private double ImageExtentTop = 37.337427;
    private int image_width = 1430;
    private int image_height = 2100;
    private View v;
    private static double ulatitude=0;
    private static double ulongitude=0;
    private ResponseReceiver receiver;
    private Button fetchuserloc;
    private static final String[] INITIAL_PERMS={
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.READ_CONTACTS
    };
    private static final String[] LOCATION_PERMS={
            android.Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int INITIAL_REQUEST=1337;
    private static final int LOCATION_REQUEST=INITIAL_REQUEST+3;

    // private OnFragmentInteractionListener mListener;

    public MapViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("I AM INSIDE CREATE MAP VIEW FRAGMENT");
        if (!canAccessLocation()) {
            requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
        }
    }

    private boolean canAccessLocation() {
        return(hasPermission(android.Manifest.permission.ACCESS_FINE_LOCATION));
    }
    private boolean hasPermission(String perm) {
        return(PackageManager.PERMISSION_GRANTED== ContextCompat.checkSelfPermission(getActivity(),perm));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        buildingList = new ArrayList<Building>() {
            {
                add(new Building("King Library", "kinglibrary", "Dr. Martin Luther King, Jr. Library, 150 East San Fernando Street, San Jose, CA 95112", "KING", -959, 37.335338, -121.885043,182,611));
                add(new Building("Engineering Building", "engg", "San Jose State University Charles W. Davidson College of Engineering, 1 Washington Square, San Jose, CA 95112", "EB",-49133, 37.337129, -121.881717,863,683));
                add(new Building("Yoshihiro Uchida Hall", "yuh", "Yoshihiro Uchida Hall, San Jose, CA 95112", "YUH", -4641443, 37.333553, -121.883757,165,1324));
                add(new Building("Student Union", "su", "SJSU Student Union, San Jose, CA 95112", "SU", -6739523, 37.336430, -121.881202,823,955));
                add(new Building("BBC", "bbc", "Boccardo Business Complex, San Jose, CA 95112", "BBC", -11722061, 37.336649, -121.878493,1209,1145));
                add(new Building("South Parking Garage", "spg", "San Jose State University South Garage, 330 South 7th Street, San Jose, CA 95112", "SPG", -38400, 37.3332248,-121.8797073,540,1805));
            }
        };

//        IntentFilter filter = new IntentFilter(ResponseReceiver.PROCESS_RESPONSE);
//        filter.addCategory(Intent.CATEGORY_DEFAULT);
//        receiver = new ResponseReceiver();
//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver,filter);


        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_map_view, container, false);
        //getCurrentUserLocation();
       RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.mapView);
       relativeLayout.addView(new UserLocation(getActivity(),calculateX(ulongitude),calculateY(ulatitude)));

//        private final LocationListener mlocationListener = new LocationListener() {

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                System.out.println("**********VINAY OnLocationChanged  "+location.getLatitude()+" , "+location.getLongitude());
                ulatitude = location.getLatitude();
                ulongitude = location.getLongitude();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        try {
            // Register the listener with the Location Manager to receive location updates
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        } catch(SecurityException e){
            e.printStackTrace();
        }


        fetchuserloc = (Button)v.findViewById(R.id.fetchuser);
        fetchuserloc.setOnClickListener(new View.OnClickListener() {

             @Override
            public void onClick(View v) {
                final LocationManager manager = (LocationManager) getActivity().getSystemService( Context.LOCATION_SERVICE );

                if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    Toast.makeText(getActivity().getApplicationContext(), "Switch on the GPS to fetch user's current location", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Fetching user's current location", Toast.LENGTH_LONG).show();
//                    LocationListener locationListener = new MyLocationListener();
                    //getCurrentUserLocation();
                }
            }
        });

        Intent intent = getActivity().getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            System.out.println("The query us"+query);
            //doMySearch(query);
        }


        v.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){

                final int xpos = (int) event.getX();
                final int ypos = (int) event.getY();

                int colorInt = getHotspotColor(xpos,ypos);
                System.out.println("The value is " +colorInt);
                System.out.println("The value is "+xpos+","+ypos);

                //getCurrentUserLocation();
                //System.out.println("*****Current user latitude and longitude is "+ulatitude+" & "+ulongitude);

                ListIterator<Building> iterator= buildingList.listIterator();

                System.out.println("**********VINAY OnCLick Location Button(  "+ulatitude+" , "+ulongitude);

                while(iterator.hasNext()){
                    Building currBuilding = (Building)iterator.next();
                    if(colorInt == currBuilding.getColor()){
                        Intent detailActivityIntent = new Intent(getActivity(), BuildingDetailActivity.class);
                        detailActivityIntent.putExtra(BuildingDetailFragment.NAME, currBuilding.getName());
                        detailActivityIntent.putExtra(BuildingDetailFragment.ADDRESS, currBuilding.getAddress());
                        detailActivityIntent.putExtra(BuildingDetailFragment.B_LATITUDE, currBuilding.getLatitude());
                        detailActivityIntent.putExtra(BuildingDetailFragment.B_LONGITUDE, currBuilding.getLongitude());
                        detailActivityIntent.putExtra(BuildingDetailFragment.U_LATITUDE, ulatitude);
                        detailActivityIntent.putExtra(BuildingDetailFragment.U_LONGITUDE, ulongitude);
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

    @Override
    public void onLocationChanged(final Location loc) {
        System.out.println("*****VINAY LocationManager Fetched User coordinates of the user: Latitude "+loc.getLatitude()+" Longitude "+loc.getLongitude());
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

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
        return res;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu,inflater);

        final MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
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
                    if( query.equalsIgnoreCase(currBuilding.getName()) || query.equalsIgnoreCase(currBuilding.getAbbreviation())){
                        System.out.println("The search matches "+currBuilding.getName());
                        RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.mapView);
                        relativeLayout.addView(new BuildingMarker(getActivity(),currBuilding.getX(),currBuilding.getY()));/* calculateX(currBuilding.getLongitude()), calculateY(currBuilding.getLatitude())));*/
                    }
                }
                return true;
            }
        }
        );

        ImageView closeButton = (ImageView)searchView.findViewById(R.id.search_close_btn);


        // Set on click listener
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println("Inside the on clear method method");

                searchView.setQuery("", false);
                //Collapse the action view
                searchView.onActionViewCollapsed();
                //Collapse the search widget
                searchItem.collapseActionView();

                RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.mapView);
                relativeLayout.addView(new ClearMarker(getActivity()));
            }
        });

    }

        public void getCurrentUserLocation() {

            Intent mServiceIntent = new Intent(getActivity(), GetLocationService.class);
            getActivity().startService(mServiceIntent);

        }

    @Override
    public void onDestroy(){
        super.onDestroy();
        try {
//            getContext().unregisterReceiver(receiver);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void setLatLng(double lat, double lng){
        ulatitude = lat;
        ulongitude = lng;
        System.out.println("**********VINAY Updated user latitude and longitude is "+ulatitude+" & "+ulongitude);
    }
}


class ResponseReceiver extends BroadcastReceiver{

    public static final String PROCESS_RESPONSE = "com.example.vinay.sjsu_map.PROCESS_RESPONSE";

    @Override
    public void onReceive(Context context, Intent intent){

        double latitude = intent.getDoubleExtra(GetLocationService.LATITUDE, 0/*37.334968*/);
        double longitude = intent.getDoubleExtra(GetLocationService.LONGITUDE, 0/*-121.879637*/);
        System.out.println("**********VINAY Intent response()  "+latitude+" , "+longitude);

        MapViewFragment mvf = new MapViewFragment();
        mvf.setLatLng(latitude, longitude);

    }

}

class UserLocation extends View{
    Paint paint = new Paint();
    float x;
    float y;

    public UserLocation(Context context, double posX, double posY) {
        super(context);
        x=(float)posX;
        y=(float)posY;
    }
    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.RED);
        canvas.drawCircle(x, y, 20, paint);
    }
}

class BuildingMarker extends View{
    Paint paint = new Paint();
    float x;
    float y;
    Bitmap marker;

    public BuildingMarker(Context context, double posX, double posY) {
        super(context);
        x=(float)posX;
        y=(float)posY;
    }
    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.RED);
        marker = BitmapFactory.decodeResource(getResources(),
                R.drawable.addressmarker);
        canvas.drawBitmap( Bitmap.createScaledBitmap(marker,80,80,true)
                , x, y, paint);

    }
}

class ClearMarker extends View{
    Paint paint = new Paint();
    Bitmap marker;

    public ClearMarker(Context context) {
        super(context);
    }
    @Override
    public void onDraw(Canvas canvas) {
        Bitmap mapView = BitmapFactory.decodeResource(getResources(),
                R.drawable.campusmap);
        canvas.drawBitmap( Bitmap.createScaledBitmap(mapView,1430,2100,true)
                , 0, 0, paint);

    }
}