package IteratorPattern;

import IteratorPattern.iterators.BingeIterator;
import IteratorPattern.iterators.EpisodeIterator;

import java.util.ArrayList;
import java.util.List;

class Series {
    private List<Season> seasons = new ArrayList<>();
    public void addSeason(Season s) { seasons.add(s); }
    public List<Season> getSeasons() { return seasons; }

    public EpisodeIterator getBingeIterator() {
        return new BingeIterator(seasons);
    }
}