package com.example.my_application16;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class WeatherDataService {

    public static final String QUERY_FOR_CITY_ID  = "https://www.metaweather.com/api/location/search/?query=";

    Context context;

    String cityID;

    public WeatherDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(String cityID);
    }

    public String getCityID(String cityName, VolleyResponseListener volleyResponseListener){

        String url = QUERY_FOR_CITY_ID + cityName ;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                cityID = "";
                try {
                    JSONObject cityInfo = response.getJSONObject(0);
                    cityID = cityInfo.getString("woeid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                volleyResponseListener.onResponse(cityID);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError(cityID);
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
        return cityID;
    }

//    public List<weatherReportModel> getCityForecastByID(String cityID){
//
//    }
//
//    public List<weatherReportModel> getCityForecastByName(String cityName){
//
//    }
}
