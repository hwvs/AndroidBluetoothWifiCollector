package net.hunterwatson.wifidatacollector.beacon.data;

import android.net.MacAddress;
import android.net.wifi.ScanResult;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

public class WifiBeacon extends BeaconBase {

    // Fields worth collecting for data analysis and mining (eg: not channelWidth)
    private int[] getSecurityTypes;
    private String SSID;
    private String BSSID;
    private int frequency;

    /**
     * Hidden (private) constructor
     *
     * @param macAddressHRList
     * @param signalStrengthRSSI
     */
    private WifiBeacon(List<String> macAddressHRList, int signalStrengthRSSI) {
        super(macAddressHRList, signalStrengthRSSI);
    }


    public String getNameIdentifier() {
        return SSID;
    }

    public static class Factory {
        public static WifiBeacon fromScanResult(ScanResult scanResult) {

            List<String> macAddressHRList = new ArrayList<String>();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                MacAddress macAddress= scanResult.getApMldMacAddress();
                macAddressHRList.add(macAddress.toString());
            }

            String SSID = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                SSID = scanResult.getWifiSsid().toString();
            }

            if (SSID == null) { // Fall back
                SSID = scanResult.SSID;
            }
            if (SSID == null) { // Fall back
                SSID = scanResult.BSSID;
            }
            if (SSID == null) {
                throw new RuntimeException("SSID is null");
            }

            // Instantiate a new WifiBeacon object
            WifiBeacon beacon = new WifiBeacon(macAddressHRList, scanResult.level);

            // Set the fields
            beacon.BSSID = scanResult.BSSID;
            beacon.frequency = scanResult.frequency;

            return beacon;
        }
    }
}
