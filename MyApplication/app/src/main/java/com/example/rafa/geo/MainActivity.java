package com.example.rafa.geo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {

    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    Geocoder geocoder;
    List<Address> addresses;
    Location location;
    LocationManager locationManager;
    LocationListener locationListener;
    double latitudActual, longitudActual;
    String numero = "";

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

        if ((Math.round(latitudActual * 10000d) / 10000d) == (Math.round(latitud * 10000d) / 10000d) && (Math.round(longitudActual * 10000d) / 10000d) == (Math.round(longitud * 10000d) / 10000d)){
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+numero));
        }

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

