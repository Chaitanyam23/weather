package com.example.weather;

import static com.google.android.material.color.utilities.MaterialDynamicColors.error;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity<cityName, searchIV> extends AppCompatActivity {

    private RelativeLayout homeRL;
    private ProgressBar loadingPB;
    private TextView cityNameTV, temperatureTV, conditionTV;
    private RecyclerView weatherRV;
    private TextInputEditText cityEdt;
    private ImageView backIV, iconIV, searchIV;
    public searchIV;
    private WeatherRVAdapter weatherRVAdapter;
    private ArrayList<WeatherRVModel> weatherRVModelArrayList;
    private LocationManager locationManager;
    private int PERMISSION_CODE = 1;
    private String cityName;

    public MainActivity(String cityName) {
        this.cityName = cityName;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);

        homeRL = findViewById(R.id.idRLHome);
        loadingPB = findViewById(R.id.idPBLoading);
        cityNameTV = findViewById(R.id.idTVCity);
        temperatureTV = findViewById(R.id.idTVTemperature);
        conditionTV = findViewById(R.id.idTVCondition);
        weatherRV = findViewById(R.id.idRVWeather);
        cityEdt = findViewById(R.id.idEdtCity);
        backIV = findViewById(R.id.idIVBack);
        iconIV = findViewById(R.id.idIVIcon);
        searchIV = findViewById(R.id.idTVSearch);
        weatherRVAdapter = new WeatherRVAdapter(this, weatherRVModelArrayList);
        weatherRVModelArrayList = new ArrayList<>();
        weatherRV.setAdapter(weatherRVAdapter);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE);

    }

    @SuppressLint("MissingPermission")
    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

    cityName=

    getCityName(location.getLongitude(),location.

    getLatitude());

    getWeatherInfo(cityName);


searchIV.setOnClickListener(new View.OnClickListener()

    private final Object v;

    {
        @Override
        public void onClick(View v){
        String city = cityEdt.getText().toString();
        if (city.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter city name", Toast.LENGTH_SHORT).show();
        } else {
            cityNameTV.setText(cityName);
            getWeatherInfo(city);
        }
    }
    });



}


    private String getCityName() {
        return getCityName(, );
    }

    private String getCityName(double longitude, double latitude)  {
    String cityName = "Not found";
    Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
    try {
        List<Address> addresses = gcd.getFromLocation(latitude, longitude, 10);
        for (Address adr : addresses) {
            if (adr != null) {
                String city = adr.getLocality();
                if (city != null && !city.equals("")) {
                    cityName = city;
                } else {
                    Log.d("TAG","CITY NOT FOUND");
                    Toast.makeText(this,"User City Not Found..",Toast.LENGTH_SHORT).show();
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return cityName;
}

    private <jsonObjectRequest> void getWeatherInfo(String cityName){
        String url = "http://api.weatherapi.com/v1/forecast.json?key=588c56bf945742ebb68132854232210&q="+cityName+"&days=1&aqi=yes&alerts=yes";
        cityNameTV.setText(cityName);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingPB.setVisibility(View.GONE);
                homeRL.setVisibility(View.VISIBLE);
                weatherRVModelArrayList.clear();
                try {
                    try {
                        String temperature = response.getJSONObject("current").getString("temp_c");
                        temperatureTV.setText(temperature+"c");
                        int isDay = response.getJSONObject("current").getInt("is_day");
                        String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                        String conditionIcon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                        Picasso.get().load("http:".concat(conditionIcon)).into(iconIV);
                        conditionTV.setText(condition);
                        if (isDay==1){
                            Picasso.get().load("https://www.google.com/url?sa=i&url=https%3A%2F%2Fsteemit.com%2Faiman%2F%40aiman%2Fgood-morning-beautiful-weather-today-2018-02-27-03-21-52&psig=AOvVaw3tAxk1DdR54ElYbjH0mY3T&ust=1698495171824000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCJjY7tuZloIDFQAAAAAdAAAAABAE").into(backIV);
                        }else{
                            //night
                            Picasso.get().load("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.peakpx.com%2Fen%2Fsearch%3Fq%3Dclear%2Bweather&psig=AOvVaw3bgkbdCTc4XvEeG_JJbDxv&ust=1698495107021000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCOC5-ryZloIDFQAAAAAdAAAAABAR").into(backIV);
                        }
                        JSONObject foreastObj = response.getJSONObject("forecast");
                        JSONObject forecastO=foreastObj.getJSONArray("forecastday").getJSONObject(0);
                        JSONArray hourArray = forecastO.getJSONArray("hour");
                        for(int i=0; i<hourArray.length();i++){
                            JSONObject hourObj=hourArray.getJSONObject(i);
                            String time = hourObj.getString("time");
                            String temper = hourObj.getString("temp_c");
                            String img = hourObj.getJSONObject("condition").getString("icon");
                            String wind = hourObj.getString("wind_kph");
                            weatherRVModelArrayList.add(new WeatherRVModel(time,temperature,img,wind));

                        }
                        weatherRVAdapter.notifyDataSetChanged();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }
        }

            private void is(boolean b) {
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Please enter valid city name..",Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

    });
    }
}