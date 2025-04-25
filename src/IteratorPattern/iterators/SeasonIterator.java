package IteratorPattern.iterators;

import IteratorPattern.Episode;

import java.util.List;

public class SeasonIterator implements EpisodeIterator {
    private List<Episode> episodes;
    private int index = 0;

    public SeasonIterator(List<Episode> episodes) {
        this.episodes = episodes;
    }

    @Override
    public boolean hasNext() {
        return index < episodes.size();
    }

    @Override
    public Episode next() {
        return episodes.get(index++);
    }
}
