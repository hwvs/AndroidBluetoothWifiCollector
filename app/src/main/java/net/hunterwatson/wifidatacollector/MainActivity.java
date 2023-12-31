package net.hunterwatson.wifidatacollector;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.arpa.example.wifidatacollector.R;

import net.hunterwatson.wifidatacollector.beacon.TaggedBeaconPairCollection;
import net.hunterwatson.wifidatacollector.beacon.data.TaggedBeaconPair;
import net.hunterwatson.wifidatacollector.beacon.data.WifiBeacon;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private final boolean CALLBACK_METHOD_ENABLED = false; // Set to false to disable callback method

    /*
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }*/


    // Main
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main); // Set the layout to content_main.xml


        if(permissionsCheck()) {

            // Task: Create a Wifi listener Android SDK
            // THERE ARE NO MORE COMMENTS AFTER THIs LINE

            Log.d("MainActivity", "Permission granted");
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

            // Check for android 30
            if (CALLBACK_METHOD_ENABLED && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                wifiManager.registerScanResultsCallback(ContextCompat.getMainExecutor(this),
                        new WifiManager.ScanResultsCallback() {
                            @Override
                            public void onScanResultsAvailable() {
                                callbackOnScanResultsAvailable(wifiManager);
                            }
                        }
                );
            } else {
                // Manual
                // Run this code every 20 seconds

                int period = 20000;

                // Lambda
                Runnable runnable = () -> {
                    while (true) {
                        try {
                            wifiManager.startScan();
                            callbackOnScanResultsAvailable(wifiManager);
                            Thread.sleep(period);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                Thread thread = new Thread(runnable); // Create a new thread
                thread.start(); // Start the thread

            }

            //wifiManager.startScan();
            Log.d("MainActivity", "Started scan");
        }
        else {
            Log.d("MainActivity", "Permission not granted");
        }


    }

    /**
     * Check if permissions are granted
     * @return True if permissions are granted, false otherwise
     */
    private boolean permissionsCheck() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            // Ask for permission callback
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            // Ask for permission using toast
            Log.d("MainActivity", "Permission not granted");



            String message = "Please grant location permission to use this app";
            int duration = Snackbar.LENGTH_INDEFINITE;
            //View view = findViewById(R.id.nav_host_fragment_content_main);
            View view = findViewById(R.id.textViewResults);
            Snackbar.make(view, message, duration).show();

            return false;
        }

        return true;
    }

    /**
     * Callback for when scan results are available
     * @param wifiManager The WifiManager object
     */
    public void callbackOnScanResultsAvailable(WifiManager wifiManager) {
        Log.d("MainActivity", "Callback callbackOnScanResultsAvailable called");

        @SuppressLint("MissingPermission") List<ScanResult> scanResults = wifiManager.getScanResults();
        Log.d("MainActivity", "Got scan results");
        // Combine array to , separated string
        Log.d("MainActivity", String.join(", ", scanResults.toString()));



        // Filter out duplicates
        ArrayList<WifiBeacon> uniqueBeacons = new ArrayList<WifiBeacon>();
        for (ScanResult scanResult : scanResults) {
            WifiBeacon beacon = WifiBeacon.Factory.fromScanResult(scanResult);

            // Check for duplicates .equals
            if (beacon != null) {
                if (!uniqueBeacons.contains(beacon)) {
                    uniqueBeacons.add(beacon);
                }
            }
        }

        // Create a TaggedBeaconPairCollection
        TaggedBeaconPairCollection taggedBeaconPairCollection = new TaggedBeaconPairCollection();

        // Add all the unique beacons to the collection
        for (WifiBeacon beacon : uniqueBeacons) {
            TaggedBeaconPair beaconPair = TaggedBeaconPair.Factory.build(beacon);
            taggedBeaconPairCollection.push(beaconPair);
        }

        // Now print out the results to the debugger attached to the app
        for (TaggedBeaconPair beaconPair : taggedBeaconPairCollection.getPairList()) {
            Log.d("MainActivity", beaconPair.toString());
        }


        // Now print out the results to the textViewResults
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textViewResults = findViewById(R.id.textViewResults);
                textViewResults.setText(""); // Clear text

                // Add to textViewResults text
                for (TaggedBeaconPair beaconPair : taggedBeaconPairCollection.getPairList()) {
                    textViewResults.setText(textViewResults.getText() + "\n" + beaconPair.toString());
                }

                // Scroll to bottom
                textViewResults.post(new Runnable() {
                    @Override
                    public void run() {
                        textViewResults.scrollTo(0, textViewResults.getBottom());
                    }
                });
            }
        });

    }
}