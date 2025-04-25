package IteratorPattern.iterators;

import IteratorPattern.Episode;

import java.util.List;

class ReverseSeasonIterator implements EpisodeIterator {
    private List<Episode> episodes;
    private int index;
    public ReverseSeasonIterator(List<Episode> episodes) {
        this.episodes = episodes;
        this.index = episodes.size() - 1;
    }
    public boolean hasNext() { return index >= 0; }
    public Episode next() { return episodes.get(index--); }
}