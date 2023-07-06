package net.hunterwatson.wifidatacollector.beacon.data;

import android.net.MacAddress;
import android.net.wifi.MloLink;
import android.net.wifi.ScanResult;
import android.net.wifi.ScanResult.InformationElement;
import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WifiBeacon extends BeaconBase {

    // Fields worth collecting for data analysis and mining (eg: not channelWidth)
    private int[] securityTypes = new int[0];
    private int standard = -1;
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

                // Get the AP MLD MAC address
                MacAddress macAddress= scanResult.getApMldMacAddress();
                String apMldMacAddress = macAddress.toString();
                if(apMldMacAddress != null && apMldMacAddress.length() > 0)
                    macAddressHRList.add(apMldMacAddress);

                // Get the MLO links
                List<MloLink> links = scanResult.getAffiliatedMloLinks();
                if(links != null && links.size() > 0) {
                    for (MloLink link : links) {
                        String mloLinkMacAddress = link.getApMacAddress().toString();
                        if(mloLinkMacAddress != null && mloLinkMacAddress.length() > 0)
                            macAddressHRList.add(mloLinkMacAddress);
                    }
                }
            }

            String SSID = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
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
            beacon.SSID = SSID;
            beacon.BSSID = scanResult.BSSID;
            beacon.frequency = scanResult.frequency;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                beacon.securityTypes = scanResult.getSecurityTypes();
                beacon.standard = scanResult.getWifiStandard();
            }

            return beacon;
        }
    }

    @Override
    public String toString() {
        return "WifiBeacon{" +
                "nameIdentifier='" + getNameIdentifier() + '\'' +
                ", SSID='" + SSID + '\'' +
                ", BSSID='" + BSSID + '\'' +
                ", macAddressHRList=" + getMacAddressHRList() +
                ", signalStrengthRSSI=" + getSignalStrengthRSSI() +
                ", securityTypes=" + Arrays.toString(securityTypes) +
                ", frequency=" + frequency +
                ", standard=" + standard +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WifiBeacon)) return false;
        WifiBeacon that = (WifiBeacon) o;
        return frequency == that.frequency &&
                standard == that.standard &&
                SSID.equals(that.SSID) &&
                BSSID.equals(that.BSSID);
    }
}
