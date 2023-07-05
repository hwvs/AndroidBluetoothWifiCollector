package net.hunterwatson.wifidatacollector.beacon;

import net.hunterwatson.wifidatacollector.beacon.data.TaggedBeaconPair;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TaggedBeaconPairCollector {
    private ArrayList<TaggedBeaconPair> pairList;

    public TaggedBeaconPairCollector() {
        pairList = new ArrayList<TaggedBeaconPair>(); // Initialize the list
    }

    public void Push(@NotNull TaggedBeaconPair pair) {
        // Doing it wrong
        if(pair == null) {
            throw new NullPointerException("Cannot pass a null TaggedBeaconPair to TaggedBeaconPairCollector.Push");
        }

        // TODO: Keep track of the largest Instant added to the list, and if the new record is not >=
        // then we MUST re-sort the list in order to guarantee that it is fully sorted ASC in time

        // Add is to the end of the ArrayList
        pairList.add(pair);
    }

    // Pass a Predicate to filter results (Eg;  Instant (time);  Name;  etc.)
    public List<TaggedBeaconPair> getPairList(Predicate<TaggedBeaconPair> filter) {
        return pairList.stream().filter(filter).collect(Collectors.toList());
    }

    public ArrayList<TaggedBeaconPair> getPairList() {
        return pairList;
    }
}
