package IteratorPattern.iterators;

import IteratorPattern.Episode;

import java.util.*;

class ShuffleSeasonIterator implements EpisodeIterator {
    private final List<Episode> shuffled;
    private int index = 0;
    public ShuffleSeasonIterator(List<Episode> episodes) {
        this.shuffled = new ArrayList<>(episodes);
        Collections.shuffle(shuffled, new Random(42)); // fixed seed for repeatability
    }
    public boolean hasNext() { return index < shuffled.size(); }
    public Episode next() { return shuffled.get(index++); }
}