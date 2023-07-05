package net.hunterwatson.wifidatacollector.beacon;

import net.hunterwatson.wifidatacollector.beacon.data.TaggedBeaconPair;

import org.jetbrains.annotations.NotNull;

import java.security.InvalidParameterException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import kotlin.NotImplementedError;

/**
 * A collection of TaggedBeaconPair objects
 * This class is used to store a list of TaggedBeaconPair objects
 * and provide methods to manipulate the list
 * @see TaggedBeaconPair
 * @see TaggedBeaconPair.Factory
 * @see TaggedBeaconPair.Factory.Build()
 */
 public class TaggedBeaconPairCollection {
    private final ArrayList<TaggedBeaconPair> pairList; // Init in constructor
    private Instant mostRecentInstantTagged = null; // Init to the lowest possible value

    /**
     * Get the head of the list (the oldest records)
     * @param limit The number of records to return
     * @return A list of TaggedBeaconPair objects
     */
    public List<TaggedBeaconPair> getTail(int limit) {
        if(limit < 0) {
            throw new InvalidParameterException("Limit must be >= 0, but received " + limit);
        }

        // TODO: Implement Method Body

        throw new NotImplementedError("getTail() is not implemented");
    }

    /**
     * Get the tail of the list (the newest records)
     * @param limit The number of records to return
     * @return A list of TaggedBeaconPair objects
     */
    public TaggedBeaconPairCollection() {
        pairList = new ArrayList<TaggedBeaconPair>(); // Initialize the list
    }

    /**
     * Push a TaggedBeaconPair object to the list, and sort the list if necessary
     * @param pair The TaggedBeaconPair object to push (Must not be null)
     */
    public void Push(@NotNull TaggedBeaconPair pair) {
        // Doing it wrong
        if(pair == null) {
            throw new NullPointerException("Cannot pass a null TaggedBeaconPair to " +
                    "TaggedBeaconPairCollector.Push");
        }

        // Add is to the end of the ArrayList
        pairList.add(pair);


        boolean isSorted = true; // Assume the list is sorted, unless proven otherwise
        if(mostRecentInstantTagged == null ||
                pair.getInstantTagged().isAfter(mostRecentInstantTagged)) {
            mostRecentInstantTagged = pair.getInstantTagged();
            isSorted = false; // The list is now in an unknown state
        }

        if(!isSorted && pairList.size() > 1) {
            // The new record is not >= the most recent record, so we must re-sort the list
            pairList.sort((o1, o2) -> o1.getInstantTagged().compareTo(o2.getInstantTagged()));

        }


    }

    /**
     * Get a list of TaggedBeaconPair objects that match a given filter (Eg. Instant (time);  Name;)
     * @param filter The filter to apply to the list, as a Predicate
     * @return A list of TaggedBeaconPair objects
     */
    public List<TaggedBeaconPair> getPairList(Predicate<TaggedBeaconPair> filter) {
        return pairList.stream().filter(filter).collect(Collectors.toList());
    }

    /**
     * Get the entire list of TaggedBeaconPair objects
     * @return A list of TaggedBeaconPair objects
     */
    public ArrayList<TaggedBeaconPair> getPairList() {
        return pairList;
    }

    /**
     * Remove all TaggedBeaconPair objects from the list that are older than a given time
     * @param instant The time to compare against
     */
    public void RemoveOlderThan(@NotNull Instant instant) {
        pairList.removeIf(pair -> pair.getInstantTagged().isBefore(instant));
    }

    // Pop X (remove and return) the oldest X records from the list
    /**
     * Remove and return the oldest X records from the list
     * @param limit The number of records to return
     * @return A list of TaggedBeaconPair objects
     */
    public List<TaggedBeaconPair> PopHead(int limit) {
        if(limit < 0) {
            throw new InvalidParameterException("Limit must be >= 0, but received " + limit);
        }

        List<TaggedBeaconPair> returnList = new ArrayList<TaggedBeaconPair>();

        // The list is already sorted ASC by time, so we can just return the first X records
        returnList.addAll(pairList.subList(0, limit));

        // Remove the first X records from the list
        pairList.subList(0, limit).clear();

        // Return the list of removed records
        return returnList;
    }

    /**
     * Remove all TaggedBeaconPair objects from the list
     */
    public void Clear() {
        pairList.clear();
    }
}
