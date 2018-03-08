package com.example.bangadi.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    private EditText txvCity;
    private TextView desc1;
    private Button b1;
    private ImageView weatherImageView;

    private String location;
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        txvCity = (EditText) findViewById(R.id.txvCity);
        desc1 = (TextView) findViewById(R.id.desc1);
        b1= (Button) findViewById(R.id.b1);
        weatherImageView = (ImageView) findViewById(R.id.weatherImageView);
        //  weatherImageView.setImageResource(R.drawable.icon_clearsky);

        mQueue = Volley.newRequestQueue(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // location = txvCity.getText().toString().trim();
                //desc1.setText(location);
                getWeatherInfo();
            }
        });

    }

    private void getWeatherInfo(){
        location = txvCity.getText().toString();
        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" +  location  + "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        // String url="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22tyler%2C%20TX%22)&format=json";
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //JSONArray jsonArray = response.getJSONArray("name");
                          /*  String city = response.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("location").getString("city");


                            String jsonResponse = "";
                            jsonResponse += "City: " + city + "\n\n";*/

                            String description = response.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getString("description");
                            String date = response.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition").getString("date");
                            String temp = response.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition").getString("temp");
                            String text = response.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition").getString("text");
                            String chill = response.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("wind").getString("chill");
                            String code = response.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition").getString("code");
                            int codes = Integer.parseInt(code);
                            String jsonResponse = "";
                            jsonResponse += description + "\n\n";
                            jsonResponse += date + "\n\n";
                            jsonResponse += temp + " °F "+"\n\n";
                            jsonResponse += text +"\n\n";
                            jsonResponse += "Chill "+chill +"\n\n";


                            desc1.setText(jsonResponse);

                            //int iconResourceId = getResources().getIdentifier("icon_" + text+".png", "drawable", getPackageName());
                            //weatherImageView.setImageResource(iconResourceId);
                            //  weatherImageView.setImageResource(R.drawable.icon_clear);



                            switch(codes){
                                case 0: weatherImageView.setImageResource(R.drawable.tornado); break;
                                case 1: weatherImageView.setImageResource(R.drawable.tropicalstorm); break;
                                case 2: weatherImageView.setImageResource(R.drawable.hurricane); break;
                                case 3: weatherImageView.setImageResource(R.drawable.severthunderstorm); break;
                                case 4: weatherImageView.setImageResource(R.drawable.thunderstorm); break;
                                case 5: weatherImageView.setImageResource(R.drawable.rainsnow); break;
                                case 6: weatherImageView.setImageResource(R.drawable.rainsleet); break;
                                case 7: weatherImageView.setImageResource(R.drawable.snowsleet); break;
                                case 8: weatherImageView.setImageResource(R.drawable.feelingdrizzle); break;
                                case 9: weatherImageView.setImageResource(R.drawable.drizzle); break;
                                case 10: weatherImageView.setImageResource(R.drawable.freezingrain); break;
                                case 11: weatherImageView.setImageResource(R.drawable.showers); break;
                                case 12: weatherImageView.setImageResource(R.drawable.showers); break;
                                case 13: weatherImageView.setImageResource(R.drawable.snowflurries); break;
                                case 14: weatherImageView.setImageResource(R.drawable.snowshowers); break;
                                case 15: weatherImageView.setImageResource(R.drawable.bowlingsnow); break;
                                case 16: weatherImageView.setImageResource(R.drawable.snow); break;
                                case 17: weatherImageView.setImageResource(R.drawable.hail); break;
                                case 18: weatherImageView.setImageResource(R.drawable.sleet); break;
                                case 19: weatherImageView.setImageResource(R.drawable.dust); break;
                                case 20: weatherImageView.setImageResource(R.drawable.fog); break;
                                case 21: weatherImageView.setImageResource(R.drawable.haze); break;
                                case 22: weatherImageView.setImageResource(R.drawable.fog); break;
                                case 23: weatherImageView.setImageResource(R.drawable.fog); break;
                                case 24: weatherImageView.setImageResource(R.drawable.wind); break;
                                case 25: weatherImageView.setImageResource(R.drawable.cloud); break;
                                case 26: weatherImageView.setImageResource(R.drawable.cloud); break;
                                case 27: weatherImageView.setImageResource(R.drawable.mostlycloudy); break;
                                case 28: weatherImageView.setImageResource(R.drawable.mostlycloudyday); break;
                                case 29: weatherImageView.setImageResource(R.drawable.partlycloudynight); break;
                                case 30: weatherImageView.setImageResource(R.drawable.partlycloudy); break;
                                case 31: weatherImageView.setImageResource(R.drawable.clearnight); break;
                                case 32: weatherImageView.setImageResource(R.drawable.sunny); break;
                                case 33: weatherImageView.setImageResource(R.drawable.night); break;
                                case 34: weatherImageView.setImageResource(R.drawable.day); break;
                                case 35: weatherImageView.setImageResource(R.drawable.rainsleet); break;
                                case 36: weatherImageView.setImageResource(R.drawable.sunny); break;
                                case 37: weatherImageView.setImageResource(R.drawable.thunderstorm); break;
                                case 38: weatherImageView.setImageResource(R.drawable.thunderstorm); break;
                                case 39: weatherImageView.setImageResource(R.drawable.severthunderstorm); break;
                                case 40: weatherImageView.setImageResource(R.drawable.snowsleet); break;
                                case 41: weatherImageView.setImageResource(R.drawable.snow); break;
                                case 42: weatherImageView.setImageResource(R.drawable.snowshowers); break;
                                case 43: weatherImageView.setImageResource(R.drawable.snow); break;
                                case 44: weatherImageView.setImageResource(R.drawable.cloud); break;
                                case 45: weatherImageView.setImageResource(R.drawable.thunderstorm); break;
                                case 46: weatherImageView.setImageResource(R.drawable.snowshowers); break;
                                case 47: weatherImageView.setImageResource(R.drawable.thunderstorm); break;
                                case 3200: break;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }


}
