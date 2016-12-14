package com.example.caozhenjie.ec601_spotify;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;
import com.spotify.sdk.android.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Play_tracks extends AppCompatActivity implements Player.NotificationCallback, ConnectionStateCallback {

    public ImageButton pause_play, backbtn, forwardbtn;
    private static final String CLIENT_ID = "0e1efd1fec78491fa7335a197d5a9d8b";
    private static final String REDIRECT_URI = "spotifygesturecontrol://callback";
    private static final int REQUEST_CODE = 1337;
    private GoogleApiClient client;
    public String uri;
    public String oauth;
    public SpotifyPlayer mPlayer;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        author_token();
    }

    public void author_token() {
        Bundle b = this.getIntent().getExtras();
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        if (b != null) {
            oauth = b.getString("playertoken");
            uri = b.getString("Playlist");
            Log.v("oauth", oauth);
            Config playerConfig = new Config(this, oauth, CLIENT_ID);
            Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                @Override
                public void onInitialized(SpotifyPlayer spotifyPlayer) {
                    mPlayer = spotifyPlayer;
                    mPlayer.addConnectionStateCallback(Play_tracks.this);
                    mPlayer.addNotificationCallback(Play_tracks.this);
                    Log.v("player", "initialized");
                    start_playback();
                }

                @Override
                public void onError(Throwable throwable) {
                    Log.e("Mspotifyloginpage", "Could not initialize player: " + throwable.getMessage());
                }
            });
        }
    }

    public void start_playback() {
        Log.v("current uri : ", uri);
        mPlayer.playUri(null, uri ,0,0);

        pause_play = (ImageButton) findViewById(R.id.pause_play);
        forwardbtn = (ImageButton) findViewById(R.id.forwardbtn);
        backbtn = (ImageButton) findViewById(R.id.backbtn);

        pause_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePauseResume();
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.skipToPrevious(null);
            }
        });
        forwardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.skipToNext(null);
            }
        });

    }

    public void togglePauseResume() {
        if (mPlayer.getPlaybackState().isPlaying) {
            mPlayer.pause(null);
        } else {
            mPlayer.resume(null);
        }
    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d("spotifyloginpage", "Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d("spotifyloginpage", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("spotifyloginpage", "User logged in");
        start_playback();
    }


    @Override
    public void onLoggedOut() {
        Log.d("spotifyloginpage", "User logged out");
    }

    @Override
    public void onLoginFailed(int i) {
        Log.d("spotifyloginpage", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("spotifyloginpage", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("spotifyloginpage", "Received connection message: " + message);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("spotifyloginpage Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }


    @Override
    public void onStop() {
        super.onStop();

    }
}