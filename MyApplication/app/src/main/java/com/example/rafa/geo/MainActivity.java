package com.example.rafa.geo;

import android.Manifest;
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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompacActivity {

    final double LATITUD = 41.639;
    final double LONGITUD = -0.906;

    TextView tv1;
    TextView tv2;

/*
    Double doublex;
    Double doubley;
*/
    @RequiresApi(api = Build.VERSION_CODES.N)

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        Button button = (Button) findViewById(R.id.llamar);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


        button.setOnClickListener(new View.OnClickListener()

        {

            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:669979616"));
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                    return;
                startActivity(intent);
            }
        });
    /*    //Reducimos los doubles a dos decimales

        DecimalFormat df = new DecimalFormat("#.000");
        df.format(doublex);
        df.format(doubley);

        if ((doublex == LATITUD) && (doubley == LONGITUD)) {
            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:669979616"));
            startActivity(i);
        }
*/

    }


    public class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            tv1.setText("Lat :" + location.getLatitude());
            tv2.setText("Long :" + location.getLongitude());

           /* doublex = location.getLatitude();
            doubley = location.getLongitude();*/
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "GPS enabled", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "GPS disable", Toast.LENGTH_LONG).show();
        }
    }


}

