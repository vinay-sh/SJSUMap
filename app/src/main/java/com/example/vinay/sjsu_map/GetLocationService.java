package com.example.vinay.sjsu_map;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Vinay on 10/31/2016.
 */

public class GetLocationService extends IntentService implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String BROADCAST_ACTION = "com.example.vinay.sjsu_map.BROADCAST";
    public static final String LATITUDE = "com.example.vinay.sjsu_map.LATITUDE";
    public static final String LONGITUDE = "com.example.vinay.sjsu_map.LONGITUDE";
    private static final String TAG = GetLocationService.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    private double latitude;
    private double longitude;

    public GetLocationService(){
        super("GetLocationService");
    }

    @Override
    protected void onHandleIntent(Intent workIntent){

        System.out.println("**********VINAY Inside onHandleIntent ");
        buildGoogleApiClient();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {
        // Once connected with google api, get the location
        displayLocation();
        System.out.println("**********VINAY GetMyLocation displayLocation() Response "+latitude+" , "+longitude);

        Intent localIntent = new Intent();
        localIntent.setAction(ResponseReceiver.PROCESS_RESPONSE);
        localIntent.addCategory(Intent.CATEGORY_DEFAULT);
        localIntent.putExtra(LATITUDE, latitude);
        localIntent.putExtra(LONGITUDE, longitude);

        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    private void displayLocation() {
        try {
            mLastLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);
        }catch(SecurityException e){
            e.printStackTrace();
        }
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            System.out.println("*****GetMyLocation displayLocation() Response "+latitude+" , "+longitude);
        } else {
            latitude = 0;
            longitude = 0;
        }
    }
}
