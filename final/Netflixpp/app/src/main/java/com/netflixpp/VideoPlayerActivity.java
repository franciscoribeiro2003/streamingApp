package com.netflixpp;

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.netflixpp.models.OnSwipeTouchListener;

public class VideoPlayerActivity extends AppCompatActivity implements View.OnClickListener {
    PlayerView playerView;
    SimpleExoPlayer player;
    String videoTitle;
    TextView title;
    Movie movie;
    //int position;
    ImageView videoBack, videoMore;
    TextView total_duration;
    AudioManager audioManager;
    ConcatenatingMediaSource concatenatingMediaSource;
    PlaybackParameters parameters;
    RelativeLayout double_tap_playpause;
    boolean double_tap = false;
    boolean singleTap = false;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.movie_display);
        hideBottomBar();
        playerView = findViewById(R.id.exoplayer_view);

        // Retrieve the Movie object from the Intent extras
        if (getIntent() != null && getIntent().hasExtra("MOVIE_OBJECT")) {
            movie = (Movie) getIntent().getSerializableExtra("MOVIE_OBJECT");
            videoTitle = movie.getTitlemovie();
            // If you want to retrieve other details from 'movie', do it here
        } else {
            // Handle the case where 'MOVIE_OBJECT' is not passed correctly
            return;
        }


        videoTitle= movie.getTitlemovie();
        //position = getIntent().getIntExtra("position", 1);

        initViews();
        playVideo();

        playerView.setOnTouchListener(new OnSwipeTouchListener(this, playerView) {
            @Override
            public void onDoubleTouch() {
                super.onDoubleTouch();
                if (double_tap) {
                    player.setPlayWhenReady(true);
                    double_tap_playpause.setVisibility(View.GONE);
                    double_tap = false;
                } else {
                    player.setPlayWhenReady(false);
                    double_tap_playpause.setVisibility(View.VISIBLE);
                    double_tap = true;
                }
            }

            @Override
            public void onSingleTouch() {
                super.onSingleTouch();
                if (singleTap) {
                    playerView.showController();
                    singleTap = false;
                } else {
                    playerView.hideController();
                    singleTap = true;
                }
                if (double_tap_playpause.getVisibility() == View.VISIBLE) {
                    double_tap_playpause.setVisibility(View.GONE);
                }
            }
        });
    }

    private void playVideo() {
        String path = movie.getLinklow();
        Uri uri = Uri.parse(path);
        player = new SimpleExoPlayer.Builder(this).build();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(
                this, Util.getUserAgent(this, "app"));
        concatenatingMediaSource = new ConcatenatingMediaSource();
        MediaSource mediaSource;
        mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        concatenatingMediaSource.addMediaSource(mediaSource);

        playerView.setPlayer(player);
        playerView.setKeepScreenOn(true);
        player.setPlaybackParameters(parameters);
        player.prepare(concatenatingMediaSource);
        //player.seekTo(position, C.TIME_UNSET);
        playError();
    }

    private void playError() {
    }

    private void initViews() {
        total_duration = findViewById(R.id.exo_duration);
        title = findViewById(R.id.video_title);
        videoBack = findViewById(R.id.video_back);
        videoMore = findViewById(R.id.video_more);
        double_tap_playpause = findViewById(R.id.double_tap_play_pause);
        if (double_tap_playpause != null) {
            double_tap_playpause.setVisibility(View.VISIBLE);
        }

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        title.setText(videoTitle);

        videoBack.setOnClickListener(this);
        videoMore.setOnClickListener(this);

    }


    @Override
    protected void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
        player.getPlaybackState();
        if (isInPictureInPictureMode()) {
            player.setPlayWhenReady(true);
        } else {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.setPlayWhenReady(true);
        player.getPlaybackState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        player.setPlayWhenReady(true);
        player.getPlaybackState();
    }

    private void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void hideBottomBar() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decodeView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decodeView.setSystemUiVisibility(uiOptions);
        }
    }



    @Override
    public void onClick(View v) {
        /*
        switch (v.getId()) {
            case R.id.video_back:
                if (player != null) {
                    player.release();
                }
                finish();
                break;

        }
         */
        playerView = findViewById(R.id.exoplayer_view);

        // Create an instance of the OnSwipeTouchListener
        OnSwipeTouchListener swipeTouchListener = new OnSwipeTouchListener(this, playerView);

        // Set the OnTouchListener to your PlayerView
        playerView.setOnTouchListener(swipeTouchListener);
    }


}
