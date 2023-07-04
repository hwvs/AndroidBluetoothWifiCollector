package com.arpa.example.wifidatacollector.beacon;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;

public class TaggedBeaconPair {

    // Initialized when this object is constructed
    @NotNull
    private Instant instantTagged;

    // The wrapped <>Beacon Object
    @NotNull
    private BeaconBase beacon;


    // Hidden (private) constructor - Due to "TaggedBeaconPairFactory
    // as a factory class
    private TaggedBeaconPair(BeaconBase beacon) {
        this.beacon = beacon;
    }

    public Instant getInstantTagged() {
        return instantTagged;
    }

    public BeaconBase getBeacon() {
        return beacon;
    }

    // Static Factory Class
    public static class Factory {
        public static TaggedBeaconPair Produce(@NotNull BeaconBase beacon) {
            TaggedBeaconPair beaconPair = new TaggedBeaconPair(beacon);
            beaconPair.instantTagged = Instant.now(); // Init with the current Instant (time)

            return beaconPair; // Return the result
        }
    }
}
