package com.example.polar_watch;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.util.Log;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.polar_watch.databinding.ActivityMainBinding;
import com.google.gson.Gson;


import java.util.HashMap;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends Activity implements LocationListener {

    private TextView mTextView;
    private ActivityMainBinding binding;

    private LocationManager locationManager;
    private Location startLocation;
    private Location endLocation;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 100;
    private Button buttonStart;
    private Button buttonStop;

    TextView data;
    private TextView mTextViewLocation;
    private TextView distanceTextView;
    private boolean isActivityRunning = true;



    @Override
    protected void onStart() {
        super.onStart();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    public void startRecording() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            mTextViewLocation.setText("Latitude: " + startLocation.getLatitude() + ", Longitude: " + startLocation.getLongitude());

        } else {
            // Request the permission from the user
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                startRecording();
            } else {
                // Permission denied
                // Handle the situation accordingly
            }
        }
    }


    public void stopRecording() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            endLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (endLocation != null && startLocation != null) {
                float distance = startLocation.distanceTo(endLocation);
                // do something with the distance
                // Display distance to the user

                distanceTextView.setText("Distance: " + distance + " meters");
                 mTextViewLocation.setText("Completed");
                 isActivityRunning = false;


            } else {
                // handle the case where either startLocation or stopLocation is null
            }

        } else {
            // Request the permission from the user
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        if (isActivityRunning)
        {
            mTextViewLocation.setText("Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
        }
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Not needed for this example
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Not needed for this example
    }

    @Override
    public void onProviderDisabled(String provider) {
        // Not needed for this example
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        mTextView = binding.text;
//
//
//        Button button = findViewById(R.id.button7);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, StartTrackingActivity.class);
//                startActivity(intent);
//            }
//        });


        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);
        mTextViewLocation = findViewById(R.id.textViewLocation);
        distanceTextView = findViewById(R.id.distanceTextView);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
            }
        });

        ///task computer
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //This thread will calculate distance
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Your code here
                        stopRecording();
                    }
                }).start();
                //This thread  will upload the location , distance data to server
                new Thread(new  Runnable() {
                    @Override
                    public void run() {
                        // Your code here
                        uploadToServer();
                    }
                }).start();

                //This thread will get and display hear rate
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Your code here
                        getHeartRate();
                    }
                }).start();
            }
        });


    }
    }
