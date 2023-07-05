package net.hunterwatson.wifidatacollector.beacon.data;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;

import kotlin.NotImplementedError;

public class TaggedBeaconPair {


    // Initialized when this object is constructed
    @NotNull
    private Instant instantTagged;

    // The wrapped <>Beacon Object
    @NotNull
    private BeaconBase beacon;


    // Hidden (private) constructor - Due to "TaggedBeaconPairFactory
    // as a factory class
    private TaggedBeaconPair(@NotNull BeaconBase beacon) {
        this.beacon = beacon;
    }

    @NotNull
    public Instant getInstantTagged() {
        return instantTagged;
    }

    @NotNull
    public BeaconBase getBeacon() {
        return beacon;
    }

    // Static Factory Class
    public static class Factory {
        @NotNull
        public static TaggedBeaconPair Build(@NotNull BeaconBase beacon) {
            TaggedBeaconPair beaconPair = new TaggedBeaconPair(beacon);
            beaconPair.instantTagged = Instant.now(); // Init with the current Instant (time)

            return beaconPair; // Return the result
        }

        @NotNull
        public static TaggedBeaconPair FromJSON(@NotNull String JSON) {
            // TODO: Implement this, and change the type of the parameter
            throw new NotImplementedError("BuildFromJSON is not implemented");
        }

    }
}
