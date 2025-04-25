package IteratorPattern;

import java.util.List;

public class Season {
    private List<Episode> episodes;

    public void addEpisode(Episode episode) {
        episodes.add(episode);
    }
    public List<Episode> getEpisodes() {
        return episodes;
    }
}
