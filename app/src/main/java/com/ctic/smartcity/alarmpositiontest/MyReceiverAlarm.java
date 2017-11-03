package com.ctic.smartcity.alarmpositiontest;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class MyReceiverAlarm extends BroadcastReceiver{
    private LocationManager locationManager;
    LocationListener locationListener;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 10; // 1 minute

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("GIODEBUG_", "Alarm set. Aca mando ultima posicion");
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean // getting GPS status
                isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isSomething = locationManager
                .isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (isGPSEnabled) {///This is enough,
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location!=null)/// and if user is != vacio
                Log.d("GIODEBUG_", "GPS! "+location.getLatitude()+"_"+location.getLongitude());
        }
    }

}
