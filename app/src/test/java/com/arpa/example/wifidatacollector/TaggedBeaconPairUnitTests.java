package com.arpa.example.wifidatacollector;

import static org.junit.Assert.assertEquals;

import com.arpa.example.wifidatacollector.beacon.BeaconBase;
import com.arpa.example.wifidatacollector.beacon.NetworkEncryptionMode;
import com.arpa.example.wifidatacollector.beacon.TaggedBeaconPair;

import org.junit.Assert;
import org.junit.Test;

public class TaggedBeaconPairUnitTests {
    @Test
    public void TaggedBeaconPairSanity() {
        String testName = "12345";
        int testSignalStrength = -60;

        // Factory test - Setup and Bootstrap
        BeaconBase testBeaconBase = new BeaconBase(testName, "11:11:11:11:11:11", testSignalStrength,
                NetworkEncryptionMode.WPA2, "Audio");
        TaggedBeaconPair taggedBeaconPair = TaggedBeaconPair.Factory.Produce(testBeaconBase);

        // Assertions
        Assert.assertNotNull("FAILED ASSERT: TaggedBeaconPair produced a null beacon", taggedBeaconPair.getBeacon());
        Assert.assertTrue("FAILED ASSERT: Attributes were mangled by TaggedBeaconPair.Factory.Produce." +
                " Original BeaconBase is not equal to the internal of the TaggedBeaconPair",
                taggedBeaconPair.getBeacon().equals(testBeaconBase));
        Assert.assertTrue("FAILED ASSERT: Field '.getName()' was mangled by TaggedBeaconPair.Factory.Produce",
                taggedBeaconPair.getBeacon().getName().equals(testName));
        Assert.assertTrue("FAILED ASSERT: Field, '.getSignalStrength()' was mangled by TaggedBeaconPair.Factory.Produce",
                taggedBeaconPair.getBeacon().getSignalStrength() == testSignalStrength);
    }
}
