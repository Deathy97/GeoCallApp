package com.example.rafa.geo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView tv1;
    TextView tv2;
    Location location;
    LocationManager locationManager;
    LocationListener locationListener;
    private final double latitud = 41.639475;
    private final double longitud = -0.906729;
    double latitudActual, longitudActual;

    @RequiresApi(api = Build.VERSION_CODES.N)

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Controlando las versiones, a partir de Android M el request de los permisos se hace al ejecutar no al instalar
        //Obteniendo la ultima posicion conocida para partir sobre ella como base

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return;
            }
            else{
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        else{
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        if ((Math.round(latitudActual * 10000d) / 10000d) == (Math.round(latitud * 10000d) / 10000d) && (Math.round(longitudActual * 10000d) / 10000d) == (Math.round(longitud * 10000d) / 10000d)){
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:976550758"));
        }


    }

    public void setActualLocation() {


        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                latitudActual = location.getLatitude();
                longitudActual = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return;
            }
            else{

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            }
        }
        else{

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }


    }

}

