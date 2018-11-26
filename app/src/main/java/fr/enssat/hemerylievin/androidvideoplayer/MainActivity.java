package fr.enssat.hemerylievin.androidvideoplayer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import fr.enssat.hemerylievin.androidvideoplayer.models.Chapitre;
import fr.enssat.hemerylievin.androidvideoplayer.models.Chapitres;

public class MainActivity extends AppCompatActivity {

    private Chapitres chapitres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaController mediaController = new MediaController(this);
        Uri uri = Uri.parse("https://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4");
        VideoView simpleVideoView = findViewById(R.id.VideoView);
        simpleVideoView.setVideoURI(uri);
        simpleVideoView.setMediaController(mediaController);
        simpleVideoView.start();

        this.chapitres = extractJson();
    }

    public Chapitres extractJson() {
        Chapitres chapitres = new Chapitres();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(getApplicationContext()));
            JSONArray m_jArry = obj.getJSONArray("chapitres");

           for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                int numero = jo_inside.getInt("numero");
                String titre = jo_inside.getString("titre");
                double time = jo_inside.getDouble("timestamp");
                chapitres.add(new Chapitre(numero, titre, time));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return chapitres;
    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("summary.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
