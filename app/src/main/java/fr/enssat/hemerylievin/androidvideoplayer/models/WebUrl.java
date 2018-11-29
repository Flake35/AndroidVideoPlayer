package fr.enssat.hemerylievin.androidvideoplayer.models;

public class WebUrl {
    public String url;
    public int startTime;
    public int stopTime;

    public WebUrl(String url, int start, int stop){
        this.url = url;
        this.startTime = start;
        this.stopTime = stop;
    }

    public Boolean isCurrentUrl(int position){
        if (position >= this.startTime && position < stopTime) {
            return true;
        }
        return false;
    }
}
