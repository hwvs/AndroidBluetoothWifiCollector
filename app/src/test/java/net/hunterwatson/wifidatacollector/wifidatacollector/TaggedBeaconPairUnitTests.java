package net.hunterwatson.wifidatacollector.wifidatacollector;

import static org.junit.Assert.assertEquals;

import android.net.wifi.ScanResult;

import net.hunterwatson.wifidatacollector.beacon.data.BeaconBase;
import net.hunterwatson.wifidatacollector.beacon.data.WifiBeacon;
import net.hunterwatson.wifidatacollector.beacon.data.TaggedBeaconPair;

import org.junit.Assert;
import org.junit.Test;

public class TaggedBeaconPairUnitTests {
    @Test
    public void TaggedBeaconPair_sanityCheck() {
        String testName = "12345";
        int testSignalStrength = -60;

        ScanResult scanResult = new ScanResult();
        scanResult.BSSID = testName;
        scanResult.level = testSignalStrength;

        // Factory test - Setup and Bootstrap
        WifiBeacon testBeaconBase = WifiBeacon.Factory.fromScanResult(scanResult);
        TaggedBeaconPair taggedBeaconPair = TaggedBeaconPair.Factory.Build(testBeaconBase);

        // Assertions
        Assert.assertNotNull("FAILED ASSERT: TaggedBeaconPair produced a null beacon", taggedBeaconPair.getBeacon());
        Assert.assertTrue("FAILED ASSERT: Attributes were mangled by TaggedBeaconPair.Factory.Produce." +
                " Original BeaconBase is not equal to the internal of the TaggedBeaconPair",
                taggedBeaconPair.getBeacon().equals(testBeaconBase));
        Assert.assertTrue("FAILED ASSERT: Field '.getName()' was mangled by TaggedBeaconPair.Factory.Produce",
                taggedBeaconPair.getBeacon().getNameIdentifier().equals(testName));
        Assert.assertTrue("FAILED ASSERT: Field, '.getSignalStrength()' was mangled by TaggedBeaconPair.Factory.Produce",
                taggedBeaconPair.getBeacon().getSignalStrengthRSSI() == testSignalStrength);
    }
}
