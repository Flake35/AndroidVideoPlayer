package fr.enssat.hemerylievin.androidvideoplayer.models;

public class WebUrl {
    private String url;
    private int startTime;
    private int stopTime;

    public WebUrl(String url, int start, int stop){
        this.url = url;
        this.startTime = start;
        this.stopTime = stop;
    }

    public boolean isCurrentUrl(int position){
        if (position >= this.startTime && position < stopTime) {
            return true;
        }
        return false;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getStopTime() {
        return stopTime;
    }

    public void setStopTime(int stopTime) {
        this.stopTime = stopTime;
    }
}
