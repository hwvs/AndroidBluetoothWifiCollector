package com.arpa.example.wifidatacollector.beacon;

import java.time.Instant;

public class TaggedBeaconPair {

    // Initialized when this object is constructed
    private Instant instantTagged;

    // The wrapped <>Beacon Object
    private BeaconBase beacon;


    // Hidden (private) constructor - Due to "TaggedBeaconPairFactory
    // as a factory class
    private TaggedBeaconPair() {}

    public Instant getInstantTagged() {
        return instantTagged;
    }

    public BeaconBase getBeacon() {
        return beacon;
    }

    // Static Factory Class
    public static class Factory {
        public static TaggedBeaconPair Produce(BeaconBase beacon) {
            TaggedBeaconPair beaconPair = new TaggedBeaconPair();
            beaconPair.instantTagged = Instant.now(); // Init with the current Instant (time)

            return beaconPair; // Return the result
        }
    }
}
