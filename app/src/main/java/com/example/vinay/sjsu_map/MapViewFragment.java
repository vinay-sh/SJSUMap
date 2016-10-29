package com.example.vinay.sjsu_map;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


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

        System.out.println("Value of X is "+calculateX(-121.8817131));
        System.out.println("Value of Y is "+calculateY(37.3367211));

        //CalculatingDistance cd = new CalculatingDistance();
       // cd.SendRequest(37.3367211, 37.3367211, 37.3367211, 37.3367211);
        new AsyncTaskGoogleMapAPI().execute(37.3367211, -121.8817131,37.3367311, -121.8817431);

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

                //System.out.println("The value is " +getHotspotColor(xpos,ypos));
                System.out.println("The value is "+xpos+","+ypos);

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
}
