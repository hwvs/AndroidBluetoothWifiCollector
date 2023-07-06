package net.hunterwatson.wifidatacollector.beacon.data;

import android.Manifest;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import java.text.ParseException;
import java.util.List;

public class BluetoothBeacon extends BeaconBase {
    private String DeviceName;
    private BluetoothClass bluetoothClass;

    /**
     * Hidden (private) constructor
     *
     * @param macAddressHRList
     * @param signalStrengthRSSI
     */
    private BluetoothBeacon(List<String> macAddressHRList, int signalStrengthRSSI) {
        super(macAddressHRList, signalStrengthRSSI);
    }

    public String getNameIdentifier() {
        return DeviceName;
    }


    public static class Factory {
        /*
        Reference:  public static WifiBeacon fromScanResult(ScanResult scanResult) {

            List<String> macAddressHRList = new ArrayList<String>();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                MacAddress macAddress= scanResult.getApMldMacAddress();
                macAddressHRList.add(macAddress.toString());
            }

            String SSID = "";
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                SSID = scanResult.getWifiSsid().toString();
            }
            else {
                SSID = scanResult.SSID;
            }

            // Instantiate a new WifiBeacon object
            WifiBeacon beacon = new WifiBeacon(SSID, macAddressHRList, scanResult.level);

            // Set the fields
            beacon.BSSID = scanResult.BSSID;
            beacon.frequency = scanResult.frequency;

            return beacon;
        }
         */
        public static BluetoothBeacon fromScanResult(Context context, ScanResult scanResult) throws ParseException {

            List<String> macAddressHRList = new java.util.ArrayList<String>();

            BluetoothDevice device = scanResult.getDevice();
            if (device == null) {
                throw new ParseException("ScanResult does not contain a BluetoothDevice", 0);
            }

            String macAddress = device.getAddress();
            macAddressHRList.add(macAddress);

            // Instantiate a new WifiBeacon object
            BluetoothBeacon beacon = new BluetoothBeacon(macAddressHRList, scanResult.getRssi());

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                throw new ParseException("BLUETOOTH_CONNECT permission not granted", 0);
            }
            beacon.DeviceName = device.getName();
            beacon.bluetoothClass = device.getBluetoothClass();


            return beacon;

        }
    }


    @Override
    public String toString() {
        return "BluetoothBeacon{" +
                "nameIdentifier='" + getNameIdentifier() + '\'' +
                ", signalStrengthRSSI=" + getSignalStrengthRSSI() +
                ", macAddressHRList=" + getMacAddressHRList() +
                '}';
    }
}
