package com.arpa.example.wifidatacollector;
import java.util.Objects;

public class BeaconBase {
    private String name;
    private String macAddress;
    private int signalStrength;
    
    private NetworkEncryptionMode encryptionMode;
    private String deviceType;

    public BeaconBase(String name, String macAddress, int signalStrength, NetworkEncryptionMode encryptionMode, String deviceType) {
        this.name = name;
        this.macAddress = macAddress;
        this.signalStrength = signalStrength;
        this.encryptionMode = encryptionMode;
        this.deviceType = deviceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public int getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(int signalStrength) {
        this.signalStrength = signalStrength;
    }

    public NetworkEncryptionMode getEncryptionMode() {
        return encryptionMode;
    }

    public void setEncryptionMode(NetworkEncryptionMode encryptionMode) {
        this.encryptionMode = encryptionMode;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeaconBase that = (BeaconBase) o;
        return signalStrength == that.signalStrength &&
                Objects.equals(name, that.name) &&
                Objects.equals(macAddress, that.macAddress) &&
                Objects.equals(encryptionMode, that.encryptionMode) &&
                Objects.equals(deviceType, that.deviceType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, macAddress, signalStrength, encryptionMode, deviceType);
    }

    @Override
    public String toString() {
        return "BeaconBase{" +
                "name='" + name + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", signalStrength=" + signalStrength +
                ", encryptionMode='" + encryptionMode.toString() + '\'' +
                ", deviceType='" + deviceType + '\'' +
                '}';
    }
}