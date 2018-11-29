package fr.enssat.hemerylievin.androidvideoplayer.custom;

import android.content.Context;
import android.widget.Button;

public class CustomButton {
    private Button button;
    private int startTime;
    private int stopTime;

    public CustomButton(String title, int tag, int startTIme, int stopTime, Context appContext) {
        this.button = new Button(appContext);
        this.button.setText(title);
        this.button.setTag(tag);
        this.startTime = startTIme;
        this.stopTime = stopTime;
    }

    public boolean isCurrentButton(int position) {
        if (position >= this.startTime && position < this.stopTime){
            return true;
        }
        return false;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
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
