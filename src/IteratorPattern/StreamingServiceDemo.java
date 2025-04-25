package IteratorPattern;

import IteratorPattern.iterators.EpisodeIterator;

public class StreamingServiceDemo {
    public static void main(String[] args) {
        Episode ep1 = new Episode("Pilot", 1800);
        Episode ep2 = new Episode("The Journey Begins", 1900);
        Episode ep3 = new Episode("Cliffhanger", 1700);

        Season season1 = new Season();
        season1.addEpisode(ep1);
        season1.addEpisode(ep2);

        Season season2 = new Season();
        season2.addEpisode(ep3);

        Series series = new Series();
        series.addSeason(season1);
        series.addSeason(season2);

        System.out.println("Normal Order:");
        EpisodeIterator normal = season1.getNormalIterator();
        while (normal.hasNext()) System.out.println(normal.next().getTitle());

        System.out.println("\nReverse Order:");
        EpisodeIterator reverse = season1.getReverseIterator();
        while (reverse.hasNext()) System.out.println(reverse.next().getTitle());

        System.out.println("\nShuffle Order:");
        EpisodeIterator shuffle = season1.getShuffleIterator();
        while (shuffle.hasNext()) System.out.println(shuffle.next().getTitle());

        System.out.println("\nBinge Mode:");
        EpisodeIterator binge = series.getBingeIterator();
        while (binge.hasNext()) System.out.println(binge.next().getTitle());
    }
}