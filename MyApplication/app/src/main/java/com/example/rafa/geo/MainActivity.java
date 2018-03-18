package com.example.rafa.geo;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends Activity {

    TextView tv1, tv2, tv3, tv4;
    Geocoder geocoder;
    List<Address> addresses;
    LocationManager locationManager;
    List<Geofence> mGeofenceList =  new ArrayList<>();
    PendingIntent mGeofencePendingIntent;

    @RequiresApi(api = Build.VERSION_CODES.N)

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();

        geocoder = new Geocoder(this, Locale.getDefault());

        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE}, 1234);

        //Controlando las versiones, a partir de Android Mn el request de los permisos se hace al ejecutar no al instalar
        //Obteniendo la ultima posicion conocida para partir sobre ella como base

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return;
            }
            else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
        else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }


        mGeofenceList.add(new Geofence.Builder()
                .setRequestId("Geofence1")
                .setCircularRegion(41.632730 ,-0.890711,100)
                .setExpirationDuration(5000)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());


        GeofencingClient mGeofencingClient = null;
        mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }


    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, MainActivity.class);
        mGeofencePendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return mGeofencePendingIntent;
    }



    public class MyLocationListener implements LocationListener {


            @Override
            public void onLocationChanged(Location location) {
                tv1.setText("Lat :" + location.getLatitude());
                tv2.setText("Long :" + location.getLongitude());
                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
              //  tv3.setText(""+addresses); prueba con la lista entera
                tv4.setText(""+addresses.get(0).getAddressLine(0));

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
                Toast.makeText(getApplicationContext(), "GPS enabled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(getApplicationContext(), "GPS disable", Toast.LENGTH_LONG).show();
            }
        };
}

