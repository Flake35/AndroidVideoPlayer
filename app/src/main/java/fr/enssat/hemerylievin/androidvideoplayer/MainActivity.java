package fr.enssat.hemerylievin.androidvideoplayer;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.ArrayList;

import fr.enssat.hemerylievin.androidvideoplayer.models.Chapitre;

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance;

    private final String videoUrl = "https://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4";

    private int position = 0;
    private ArrayList<Chapitre> chapitres;
    private VideoView videoView;
    private MediaController mediaController;

    private WebView webView;
    private Handler handler = new Handler();
    private Runnable updatePosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        this.chapitres = JsonLoader.getChapitres();

        setContentView(R.layout.activity_main);
        this.videoView = findViewById(R.id.VideoView);
        this.webView = findViewById(R.id.webView);

        System.out.println("3");
        this.initializeVideoPlayer();
        System.out.println("4");
        this.initializeButtons();
        System.out.println("5");
        this.createThread();
        this.updatePosition.run();

    }

    private void initializeVideoPlayer() {
        this.mediaController = new MediaController(this);
        this.mediaController.setAnchorView(this.videoView);
        this.videoView.setMediaController(this.mediaController);
        this.videoView.setVideoURI(Uri.parse("https://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4"));

        this.videoView.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                VideoView videoPlayer = MainActivity.getInstance().getVideoView();
                int position = MainActivity.getInstance().getPosition();
                videoPlayer.seekTo(position);
                if (position == 0) {
                    videoPlayer.start();
                }
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        MainActivity.getInstance().getMediaControl().setAnchorView(videoView);
                    }
                });
            }
        });
        this.webView.loadUrl("https://en.wikipedia.org/wiki/Big_Buck_Bunny");
        this.videoView.requestFocus();
    }

    private void initializeButtons() {
        LinearLayout buttonPanel = findViewById(R.id.buttonLayout);

        for (Chapitre chapitre : this.chapitres) {
            Button button = new Button(getApplicationContext());
            button.setText(chapitre.getTitre());
            button.setTag(chapitre.getNumero());
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    VideoView videoPlayer = MainActivity.getInstance().findViewById(R.id.VideoView);
                    int progress = MainActivity.getInstance().getChapitre((int) v.getTag()).getMarque();
                    videoPlayer.seekTo(progress);
                }
            });
            buttonPanel.addView(button);
        }
    }

    public Chapitre getChapitre(int chapter) {
        for (Chapitre chapitre : this.chapitres) {
            if (chapitre.getNumero() == chapter) {
                return chapitre;
            }
        }
        return null;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("CurrentPosition", videoView.getCurrentPosition());
        videoView.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("CurrentPosition");
        videoView.seekTo(position);
    }

    private void createThread() {
        this.updatePosition = new Runnable() {
            @Override
            public void run() {
                int position = videoView.getCurrentPosition();
                System.out.println(position);
                MainActivity.getInstance().getHandler().postDelayed(this, 1000);
            }
        };
    }

    public VideoView getVideoView() {
        return this.videoView;
    }

    public MediaController getMediaControl() {
        return this.mediaController;
    }

    public Handler getHandler() {
        return handler;
    }

    public int getPosition() {
        return position;
    }

    public static MainActivity getInstance() {
        return instance;
    }
}
