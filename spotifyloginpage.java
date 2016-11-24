package com.example.caozhenjie.ec601_spotify;



import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;










public class spotifyloginpage extends AppCompatActivity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback
{


    private ImageButton btn1;
    private ImageButton btn2;
    private ImageButton btn3;
    private ImageButton btn4;

    // TODO: Replace with your client ID
    private static final String CLIENT_ID = "0e1efd1fec78491fa7335a197d5a9d8b";
    // TODO: Replace with your redirect URI
    private static final String REDIRECT_URI = "spotifygesturecontrol://callback";

    public Player mPlayer;

    private static final int REQUEST_CODE = 1337;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotifyloginpage);

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        mPlayer = spotifyPlayer;
                        mPlayer.addConnectionStateCallback(spotifyloginpage.this);
                        mPlayer.addNotificationCallback(spotifyloginpage.this);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("Mspotifyloginpage", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
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

        playlist();



    }

    private void playlist() {
            btn1 = (ImageButton) findViewById(R.id.btn1);
            btn2 = (ImageButton) findViewById(R.id.btn2);
            btn3 = (ImageButton) findViewById(R.id.btn3);
            btn4 = (ImageButton) findViewById(R.id.btn4);



            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mPlayer.playUri(null, "spotify:user:spotify:playlist:65V6djkcVRyOStLd8nza8E", 0, 0);
                    btn1.setBackgroundResource(R.drawable.happy);
                    Toast.makeText(spotifyloginpage.this, "btn1", Toast.LENGTH_SHORT).show();
                }

            });

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn2.setBackgroundResource(R.drawable.sad);
                    Toast.makeText(spotifyloginpage.this, "btn2", Toast.LENGTH_SHORT).show();
                    mPlayer.playUri(null, "spotify:user:spotify:playlist:69H6RgTVs1jrv1IuuLe1a5", 0, 0);
                }

            });

            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                mPlayer.playUri(null, "spotify:user:spotify:playlist:5T4KBJYQt9oQYrLgkgaQFW", 0, 0);
                btn3.setBackgroundResource(R.drawable.hype);
                Toast.makeText(spotifyloginpage.this, "btn3", Toast.LENGTH_SHORT).show();
                 }

            });

            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                mPlayer.playUri(null, "spotify:user:topsify:playlist:6Qf2sXTjlH3HH30Ijo6AUp", 0, 0);
                    btn4.setBackgroundResource(R.drawable.uplifting);
                    Intent intent = new Intent(spotifyloginpage.this, MainActivity.class);
                    startActivity(intent);
//                Toast.makeText(spotifyloginpage.this, "btn4", Toast.LENGTH_SHORT).show();
                }

            });





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
}