package net.hunterwatson.wifidatacollector.beacon.data;

import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.Objects;

/**
 * A wrapper class for BeaconBase
 * This class is used to wrap a BeaconBase object and add a timestamp
 * @see BeaconBase
 * @see TaggedBeaconPair.Factory
 */
public class BeaconBase {

    private int signalStrengthRSSI;

    private List<String> macAddressHRList; // (HR) Human Readable MAC Address

    /**
     * Hidden (private) constructor
     */
    BeaconBase(List<String> macAddressHRList, int signalStrengthRSSI) {
        this.macAddressHRList = macAddressHRList;
        this.signalStrengthRSSI = signalStrengthRSSI;

    }

    /**
     * Get the identifier (name) of the beacon. SSID for Wifi, Device Name for Bluetooth
     * @return The identifier (name) of the beacon
     */
    public String getNameIdentifier() {
        return "<Unknown>";
    }

    public List<String> getMacAddressHRList() {
        return macAddressHRList;
    }

    public int getSignalStrengthRSSI() {
        return signalStrengthRSSI;
    }

    public void setSignalStrengthRSSI(int signalStrengthRSSI) {
        this.signalStrengthRSSI = signalStrengthRSSI;
    }


}