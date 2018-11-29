package fr.enssat.hemerylievin.androidvideoplayer.custom;

import android.content.Context;
import android.widget.Button;

public class CustomButton {
    public Button button;
    public int startTime;
    public int stopTime;

    public CustomButton(String title, int tag, int startTIme, int stopTime, Context appContext) {
        this.button = new Button(appContext);
        this.button.setText(title);
        this.button.setTag(tag);
        this.startTime = startTIme;
        this.stopTime = stopTime;
    }

    public Boolean isCurrentButton(int position) {
        if (position >= this.startTime && position < this.stopTime){
            return true;
        }
        return false;
    }
}
