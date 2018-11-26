package fr.enssat.hemerylievin.androidvideoplayer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
    private Button button;
    private VideoView simpleVideoView;
    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

        setContentView(R.layout.activity_main);

        MediaController mediaController = new MediaController(this);
        Uri uri = Uri.parse("https://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4");
        simpleVideoView = findViewById(R.id.VideoView);
        simpleVideoView.setVideoURI(uri);
        simpleVideoView.setMediaController(mediaController);
        simpleVideoView.start();

        this.chapitres = extractJson();
        setButtonsListener();
    }

    private void setButtonsListener() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VideoView videoPlayer = MainActivity.getInstance().findViewById(R.id.VideoView);
                Chapitre chaptitre = MainActivity.getInstance().getChapitre(1);
                videoPlayer.seekTo(chaptitre.getMarque());
                System.out.println(chaptitre.getTitre());
            }
        });


        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VideoView videoPlayer = MainActivity.getInstance().findViewById(R.id.VideoView);
                Chapitre chaptitre = MainActivity.getInstance().getChapitre(2);
                videoPlayer.seekTo(chaptitre.getMarque());
                System.out.println(chaptitre.getTitre());
            }
        });
    }

    public static MainActivity getInstance() {
        return instance;
    }

    public Chapitre getChapitre(int chapter) {
        for (Chapitre chapitre : this.chapitres.getChapitres()) {
            if (chapitre.getNumero() == chapter) {
                return chapitre;
            }
        }
        return null;
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
                int time = jo_inside.getInt("timestamp");
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
