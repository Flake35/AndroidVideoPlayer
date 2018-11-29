package fr.enssat.hemerylievin.androidvideoplayer;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.ArrayList;

import fr.enssat.hemerylievin.androidvideoplayer.custom.CustomButton;
import fr.enssat.hemerylievin.androidvideoplayer.models.Chapitre;
import fr.enssat.hemerylievin.androidvideoplayer.models.WebUrl;

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance;

    private final String videoUrl = "https://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4";

    private int position = 0;
    private ArrayList<Chapitre> chapitres;
    private ArrayList<CustomButton> customButtons;
    private ArrayList<WebUrl> webUrls;
    private VideoView videoView;
    private MediaController mediaController;
    private CustomButton currentButton;
    private WebUrl currentWebUrl;

    private WebView webView;
    private Handler handler = new Handler();
    private Runnable updatePosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        this.chapitres = JsonLoader.getChapitres();
        this.customButtons = new ArrayList<>();
        this.webUrls = JsonLoader.getWebUrls();
        this.currentWebUrl = this.webUrls.get(0);

        setContentView(R.layout.activity_main);
        this.videoView = findViewById(R.id.VideoView);
        this.webView = findViewById(R.id.webView);

        this.initializeVideoPlayer();
        this.initializeWebView();
        this.initializeButtons();
        this.createThread();

        this.updatePosition.run();

    }

    private void initializeVideoPlayer() {
        this.mediaController = new MediaController(this);
        this.mediaController.setAnchorView(this.videoView);
        this.videoView.setMediaController(this.mediaController);
        this.videoView.setVideoURI(Uri.parse(videoUrl));

        this.videoView.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.seekTo(position);
                if (position == 0) {
                    videoView.start();
                }
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });

        this.videoView.requestFocus();
    }

    private void initializeWebView() {
        this.webView.setWebViewClient(new WebViewClient());
        this.webView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        this.webView.loadUrl(this.currentWebUrl.getUrl());
    }

    private void initializeButtons() {
        LinearLayout buttonPanel = findViewById(R.id.buttonLayout);
        Chapitre lastChapter = new Chapitre();

        for (Chapitre chapitre : this.chapitres) {
            CustomButton button = new CustomButton(chapitre.getTitre(), chapitre.getNumero(), chapitre.getMarque(), chapitre.getNextMarque(), getApplicationContext());
            button.getButton().setTextColor(Color.GRAY);
            button.getButton().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int progress = getChapitre((int) v.getTag()).getMarque();
                    videoView.seekTo(progress);
                }
            });
            buttonPanel.addView(button.getButton());
            customButtons.add(button);
            lastChapter = chapitre;
        }
        lastChapter.setNextMarque(this.videoView.getDuration());
        this.currentButton = this.customButtons.get(0);
        this.currentButton.getButton().setTextColor(Color.DKGRAY);
    }

    private void checkCurrentButton() {
        if (!currentButton.isCurrentButton(this.position)){
            currentButton.getButton().setTextColor(Color.GRAY);
            for (CustomButton button : this.customButtons){
                if (button.isCurrentButton(this.position)){
                    currentButton = button;
                    currentButton.getButton().setTextColor(Color.DKGRAY);
                }
                else {
                    button.getButton().setTextColor(Color.GRAY);
                }
            }
        }
    }

    private void checkCurrentUrl() {
        if (!currentWebUrl.isCurrentUrl(this.position)){
            for (WebUrl webUrl : this.webUrls){
                if (webUrl.isCurrentUrl(this.position)){
                    currentWebUrl = webUrl;
                    initializeWebView();
                }
            }
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
                position = videoView.getCurrentPosition();
                checkCurrentButton();
                checkCurrentUrl();
                handler.postDelayed(this, 1000);
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
