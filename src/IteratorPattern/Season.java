package IteratorPattern;

import IteratorPattern.iterators.EpisodeIterator;
import IteratorPattern.iterators.ReverseSeasonIterator;
import IteratorPattern.iterators.SeasonIterator;
import IteratorPattern.iterators.ShuffleSeasonIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Season implements Iterable<Episode> {
    private List<Episode> episodes;

    public Season() {
        episodes = new ArrayList<>();
    }

    public void addEpisode(Episode episode) {
        episodes.add(episode);
    }
    public List<Episode> getEpisodes() {
        return episodes;
    }

    public EpisodeIterator getNormalIterator() {
        return new SeasonIterator(getEpisodes());
    }
    public EpisodeIterator getReverseIterator() {
        return new ReverseSeasonIterator(getEpisodes());
    }
    public EpisodeIterator getShuffleIterator() {
        return new ShuffleSeasonIterator(getEpisodes());
    }

    @Override
    public Iterator<Episode> iterator() {
        return episodes.iterator();
    }
}
