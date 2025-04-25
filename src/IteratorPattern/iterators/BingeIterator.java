package IteratorPattern.iterators;

import IteratorPattern.Episode;
import IteratorPattern.Season;

import java.util.List;
import java.util.NoSuchElementException;

public class BingeIterator implements EpisodeIterator {
    private final List<Season> seasons;
    private int seasonIndex = 0;
    private EpisodeIterator currentIterator;

    public BingeIterator(List<Season> seasons) {
        this.seasons = seasons;
        if (!seasons.isEmpty()) {
            currentIterator = seasons.get(0).getNormalIterator();
        }
    }

    public boolean hasNext() {
        while (currentIterator != null && !currentIterator.hasNext()) {
            seasonIndex++;
            if (seasonIndex >= seasons.size()) return false;
            currentIterator = seasons.get(seasonIndex).getNormalIterator();
        }
        return currentIterator != null && currentIterator.hasNext();
    }

    public Episode next() {
        if (!hasNext()) throw new NoSuchElementException();
        return currentIterator.next();
    }
}
