package IteratorPattern.iterators;

import IteratorPattern.Episode;

public interface EpisodeIterator {
    boolean hasNext();
    Episode next();
}
