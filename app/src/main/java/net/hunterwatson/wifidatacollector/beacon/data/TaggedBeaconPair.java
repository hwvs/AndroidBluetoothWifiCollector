package net.hunterwatson.wifidatacollector.beacon.data;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;

import kotlin.NotImplementedError;

/**
 * A wrapper class for BeaconBase
 * This class is used to wrap a BeaconBase object and add a timestamp
 * @see BeaconBase
 * @see TaggedBeaconPair.Factory
 */
public class TaggedBeaconPair {


    /**
     * The time this object was created - Initialized when this object is constructed
     */
    @NotNull
    private Instant instantTagged;

    // The wrapped <>Beacon Object
    /**
     * The wrapped BeaconBase object
     * @see BeaconBase
     */
    @NotNull
    private BeaconBase beacon;


    /**
     * Hidden (private) constructor - Due to "TaggedBeaconPairFactory
     * as a factory class
     * @param beacon The BeaconBase object to wrap
     * @see TaggedBeaconPair.Factory
     */
    private TaggedBeaconPair(@NotNull BeaconBase beacon) {
        this.beacon = beacon;
    }

    /**
     * Get the time this object was created
     * @return The Instant (time) this object was created
     */
    @NotNull
    public Instant getInstantTagged() {
        return instantTagged;
    }

    /**
     * Get the wrapped BeaconBase object
     * @return The wrapped BeaconBase object
     */
    @NotNull
    public BeaconBase getBeacon() {
        return beacon;
    }

    /**
     * A factory class for TaggedBeaconPair
     * This class is used to create TaggedBeaconPair objects
     * @see TaggedBeaconPair
     */
    public static class Factory {

        /**
         * Build a TaggedBeaconPair object from a BeaconBase object
         * @param beacon The BeaconBase object to wrap
         * @return A TaggedBeaconPair object
         */
        @NotNull
        public static TaggedBeaconPair build(@NotNull BeaconBase beacon) {
            TaggedBeaconPair beaconPair = new TaggedBeaconPair(beacon);
            beaconPair.instantTagged = Instant.now(); // Init with the current Instant (time)

            return beaconPair; // Return the result
        }

        /**
         * Build a TaggedBeaconPair object from a JSON string
         * @param JSON The JSON string to parse
         * @return A TaggedBeaconPair object
         */
        @NotNull
        public static TaggedBeaconPair FromJSON(@NotNull String JSON) {
            // TODO: Implement this, and change the type of the parameter
            throw new NotImplementedError("BuildFromJSON is not implemented");
        }

    }

    @Override
    public String toString() {
        return "TaggedBeaconPair{" +
                "instantTagged=" + instantTagged +
                ", beacon=" + beacon +
                '}';
    }
}
