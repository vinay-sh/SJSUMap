package com.example.vinay.sjsu_map;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.json.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.xml.transform.Result;

/**
 * Created by Vinay on 10/28/2016.
 */

public class AsyncTaskGoogleMapAPI extends AsyncTask<Double, Void, Void> {
//    protected void onPreExecute(){
//        super.onPreExecute();
//    }
//double originLat, double originLon, double destinatinLat, double destinationLon

    private static final String API_KEY = "AIzaSyC_ve8XnnHKgKh_bdVsK1foxA2MWMezmY8";

    @Override
    protected Void doInBackground(Double... params){
        try{
            String str = "https://maps.google.com/maps/api/distancematrix/json?origins=" +params[0]+ "," + params[1] + "&destinations=" + params[2] + "," + params[3] + "&sensor=false&units=metric&key=" + API_KEY;
            //String str2 = "http://maps.google.com/maps/api/distancematrix/json?origins=" + URLEncoder.encode(str, "UTF-8");
            System.out.println("**********Sending URL "+str);
            URL myurl = new URL(str);

            HttpURLConnection connection = (HttpURLConnection) myurl.openConnection();
            connection.setDoOutput(true);
            connection.connect();
            connection.setRequestMethod("POST");
            //InputStream input = connection.getInputStream();

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
//            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = connection.getResponseCode();
            System.out.println("**********Sending 'POST' request to URL : " + myurl);
            // System.out.println("Post parameters : " + urlParameters);
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

            String distance, time;
            JsonElement jelement = new JsonParser().parse(response.toString());
            JsonObject distanceObj, timeObj;
            JsonObject jobject = jelement.getAsJsonObject();
            JsonArray jArray = jobject.getAsJsonArray("rows");
            jobject = jArray.get(0).getAsJsonObject();
            jArray = jobject.getAsJsonArray("elements");
            jobject = jArray.get(0).getAsJsonObject();

            distanceObj = jobject.getAsJsonObject("distance");
            timeObj = jobject.getAsJsonObject("duration");

            distance = distanceObj.get("text").toString();
            time = timeObj.get("text").toString();

            System.out.println("**********Distance is  " + distance);
            System.out.println("**********Time is " + time);

            in.close();

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}