package net.hunterwatson.wifidatacollector;

import android.Manifest;
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

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.arpa.example.wifidatacollector.R;

import net.hunterwatson.wifidatacollector.beacon.TaggedBeaconPairCollection;
import net.hunterwatson.wifidatacollector.beacon.data.TaggedBeaconPair;
import net.hunterwatson.wifidatacollector.beacon.data.WifiBeacon;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


    // Main
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            View view = findViewById(R.id.nav_host_fragment_content_main);
            Snackbar.make(view, message, duration).show();

            return;
        }

        // Task: Create a Wifi listener Android SDK
        // THERE ARE NO MORE COMMENTS AFTER THIs LINE

        Log.d("MainActivity", "Permission granted");
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        wifiManager.startScan();
        Log.d("MainActivity", "Started scan");

        List<ScanResult> scanResults = wifiManager.getScanResults();
        Log.d("MainActivity", "Got scan results");
        // Combine array to , separated string
        Log.d("MainActivity", String.join(", ", scanResults.toString()));

        TaggedBeaconPairCollection taggedBeaconPairCollection = new TaggedBeaconPairCollection();

        for (ScanResult scanResult : scanResults) {
            WifiBeacon beacon = WifiBeacon.Factory.fromScanResult(scanResult);
            if (beacon != null) {
                TaggedBeaconPair beaconPair = TaggedBeaconPair.Factory.build(beacon);
                taggedBeaconPairCollection.push(beaconPair);
            }
        }

        // Now print out the results to the debugger attached to the app
        for (TaggedBeaconPair beaconPair : taggedBeaconPairCollection.getPairList()) {
            Log.d("MainActivity", beaconPair.toString());
        }

    }
}