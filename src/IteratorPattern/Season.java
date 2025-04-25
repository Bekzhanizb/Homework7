package IteratorPattern;

import IteratorPattern.iterators.EpisodeIterator;
import IteratorPattern.iterators.ReverseSeasonIterator;
import IteratorPattern.iterators.SeasonIterator;
import IteratorPattern.iterators.ShuffleSeasonIterator;

import java.util.Iterator;
import java.util.List;

public class Season implements Iterable<Episode> {
    private List<Episode> episodes;

    public void addEpisode(Episode episode) {
        episodes.add(episode);
    }
    public List<Episode> getEpisodes() {
        return episodes;
    }

    public EpisodeIterator getNormalIterator() {
        return new SeasonIterator(episodes);
    }
    public EpisodeIterator getReverseIterator() {
        return new ReverseSeasonIterator(episodes);
    }
    public EpisodeIterator getShuffleIterator() {
        return new ShuffleSeasonIterator(episodes);
    }

    @Override
    public Iterator<Episode> iterator() {
        return episodes.iterator();
    }
}
