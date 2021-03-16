package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;

    String prefectureText = "";
    String cityText = "";
    String  ido;
    String  keido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView prefecture = findViewById(R.id.prefecture);
        TextView city = findViewById(R.id.city);
        TextView temperature = findViewById(R.id.temperature);



        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},1000);
        }else{
            locationStart();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,50, (LocationListener) this);
        }
    }

    private void locationStart(){
        Log.d("debug","LocationStart()");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if(locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Log.d("debug","not gpsEnable, startActivity");
        }else {
            Intent settingIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(settingIntent);
            Log.d("debug" , "not gpsEnable startActivity");
        }

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},1000);

            Log.d("debug", "checkSelfPermission false");
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, (LocationListener) this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1000){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("debug","checkSelfPermission true");

                locationStart();

            }else{
                Toast toast = Toast.makeText(this,
                        "これ以上何もできません",Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }


    public void onLocationChanged(Location location) {
        // 緯度の表示
        ido = "Latitude:"+location.getLatitude();

        System.out.println(ido);

        // 経度の表示
        keido = "Longtude:"+location.getLongitude();
        System.out.println(keido);
    }

    public void onProviderEnabled(String provider) {

    }

    public void onProviderDisabled(String provider) {

    }

}