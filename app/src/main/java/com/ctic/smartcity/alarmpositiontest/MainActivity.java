package com.ctic.smartcity.alarmpositiontest;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // This value is defined and consumed by app code, so any value will work.
    // There's no significance to this sample using 0.
    public static final int REQUEST_CODE = 0;

    int PERMISSION_ALL = 3;
    private static final String TAG = "GIODEBUG_Login";
    private final int code_request=1234;
    private static final String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WAKE_LOCK};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!hasPermissions(this, PERMISSIONS))
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case code_request:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "COARSE LOCATION permitido", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "COARSE LOCATION no permitido", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void StartAlarm(View view){
        // BEGIN_INCLUDE (intent_fired_by_alarm)
        // First create an intent for the alarm to activate.
        // This code simply starts an Activity, or brings it to the front if it has already
        // been created.
        Intent intent = new Intent(this, MyReceiverAlarm.class);
        //intent.setAction(Intent.ACTION_MAIN);
        //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        // END_INCLUDE (intent_fired_by_alarm)

        // BEGIN_INCLUDE (pending_intent_for_alarm)
        // Because the intent must be fired by a system service from outside the application,
        // it's necessary to wrap it in a PendingIntent.  Providing a different process with
        // a PendingIntent gives that other process permission to fire the intent that this
        // application has created.
        // Also, this code creates a PendingIntent to start an Activity.  To create a
        // BroadcastIntent instead, simply call getBroadcast instead of getIntent.
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, REQUEST_CODE,
                intent, 0);

        // END_INCLUDE (pending_intent_for_alarm)

        // BEGIN_INCLUDE (configure_alarm_manager)
        // There are two clock types for alarms, ELAPSED_REALTIME and RTC.
        // ELAPSED_REALTIME uses time since system boot as a reference, and RTC uses UTC (wall
        // clock) time.  This means ELAPSED_REALTIME is suited to setting an alarm according to
        // passage of time (every 15 seconds, 15 minutes, etc), since it isn't affected by
        // timezone/locale.  RTC is better suited for alarms that should be dependant on current
        // locale.

        // Both types have a WAKEUP version, which says to wake up the device if the screen is
        // off.  This is useful for situations such as alarm clocks.  Abuse of this flag is an
        // efficient way to skyrocket the uninstall rate of an application, so use with care.
        // For most situations, ELAPSED_REALTIME will suffice.
        int alarmType = AlarmManager.ELAPSED_REALTIME;
        final int FIFTEEN_SEC_MILLIS = 15000;

        // The AlarmManager, like most system services, isn't created by application code, but
        // requested from the system.
        AlarmManager alarmManager = (AlarmManager)
                this.getSystemService(this.ALARM_SERVICE);

        // setRepeating takes a start delay and period between alarms as arguments.
        // The below code fires after 15 seconds, and repeats every 15 seconds.  This is very
        // useful for demonstration purposes, but horrendous for production.  Don't be that dev.
        alarmManager.setRepeating(alarmType, SystemClock.elapsedRealtime() + FIFTEEN_SEC_MILLIS,
                FIFTEEN_SEC_MILLIS, pendingIntent);
        // END_INCLUDE (configure_alarm_manager);
        Log.i("GIODEBUG_", "Alarm set. de acá");
    }
}
