package IteratorPattern;

public class Episode {
    private String title;
    private int runtime;

    public Episode(String title, int runtime) {
        this.title = title;
        this.runtime = runtime;
    }

    public String getTitle() {
        return title;
    }

    public int getRuntime() {
        return runtime;
    }
}
