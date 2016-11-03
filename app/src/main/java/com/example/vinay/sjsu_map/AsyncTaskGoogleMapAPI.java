package com.example.vinay.sjsu_map;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by Vinay on 10/28/2016.
 */

public class AsyncTaskGoogleMapAPI extends AsyncTask<Double, Void, Void> {

    private static final String API_KEY = "AIzaSyC_ve8XnnHKgKh_bdVsK1foxA2MWMezmY8";
    private final WeakReference<TextView> timeref;
    private final WeakReference<TextView> distanceref;
    String distance, time, address;

    public AsyncTaskGoogleMapAPI(TextView a, TextView b, String add){
        timeref = new WeakReference<TextView>(a);
        distanceref = new WeakReference<TextView>(b);
        address = add;
    }

    public String encodeURL(String address){
        String a = "null";
        try {
            a = URLEncoder.encode(address, "utf-8");
        }catch(Exception e){
            e.printStackTrace();
        }
        return a;
    }

    @Override
    protected Void doInBackground(Double... params){
        try{
            address=encodeURL(address);
            String str = "https://maps.google.com/maps/api/distancematrix/json?origins=" +params[0]+ "," + params[1] + "&destinations=" + address  + "&sensor=false&units=metric&key=" + API_KEY;
            System.out.println("**********Sending URL "+str);
            URL myurl = new URL(str);

            HttpURLConnection connection = (HttpURLConnection) myurl.openConnection();
            connection.setDoOutput(true);
            connection.connect();
            connection.setRequestMethod("POST");
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.flush();
            wr.close();

            int responseCode = connection.getResponseCode();
            System.out.println("**********Sending 'POST' request to URL : " + myurl);
            System.out.println("**********Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            System.out.println("**********Response JSON data : " + response);

                //Parsing JSON response
                Gson gsonObj = new Gson();
                JsonElement jelement = new JsonParser().parse(response.toString());
                JsonObject distanceObj, timeObj;
                JsonObject jobject = jelement.getAsJsonObject();
                JsonArray jArray = jobject.getAsJsonArray("rows");
                jobject = jArray.get(0).getAsJsonObject();
                jArray = jobject.getAsJsonArray("elements");
                jobject = jArray.get(0).getAsJsonObject();
                if(jobject.getAsJsonObject("distance")!=null) {
                    distanceObj = jobject.getAsJsonObject("distance");
                    timeObj = jobject.getAsJsonObject("duration");

                    distance = distanceObj.get("text").toString();
                    time = timeObj.get("text").toString();
                    if (distance.length() >= 2 && distance.charAt(0) == '"' && distance.charAt(distance.length() - 1) == '"') {
                        distance =  distance.substring(1, distance.length() - 1);
                    }
                    if (time.length() >= 2 && time.charAt(0) == '"' && time.charAt(time.length() - 1) == '"') {
                        time =  time.substring(1, time.length() - 1);
                    }
                    System.out.println("**********Distance is  " + distance);
                    System.out.println("**********Time is " + time);
                }

            in.close();

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        super.onPostExecute(result);

        final TextView t = timeref.get();
        final TextView dist = distanceref.get();
        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);

        if(t!=null){
            t.setText(time);
            t.setTypeface(boldTypeface);
        }
        if(dist!=null){
            dist.setText(distance);
            dist.setTypeface(boldTypeface);
        }
        if(time==null && distance==null){
            t.setText("Fetch user's current location and try again.");
            dist.setText("Fetch user's current location and try again.");
        }

    }

}
