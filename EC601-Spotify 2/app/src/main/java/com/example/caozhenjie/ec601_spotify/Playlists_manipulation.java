package com.example.caozhenjie.ec601_spotify;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
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


import java.util.ArrayList;
import java.util.HashMap;

public class Playlists_manipulation extends AppCompatActivity{

    private ImageButton camera;
    public String oauth;
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("beginning", "PL_manip");
        setContentView(R.layout.activity_spotifyloginpage);
        Bundle b = this.getIntent().getExtras();
        oauth = b.getString("playertoken");
        playlist();
    }


    public void playlist() {
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        final ArrayList<String> names = new ArrayList<>();
        names.add("happy");
        names.add("sad");
        names.add("hype");
        names.add("uplifting");
        ArrayList<String> pllocation = new ArrayList<>();
        pllocation.add("spotify:user:spotify:playlist:65V6djkcVRyOStLd8nza8E");
        pllocation.add("spotify:user:spotify:playlist:69H6RgTVs1jrv1IuuLe1a5");
        pllocation.add("spotify:user:spotify:playlist:5T4KBJYQt9oQYrLgkgaQFW");
        pllocation.add("spotify:user:topsify:playlist:6Qf2sXTjlH3HH30Ijo6AUp");
        map.put("name", names);
        map.put("pllocation", pllocation);
        final ArrayList<String> uris = map.get("pllocation");
        //new Get_Playlist().execute();
        HashMap<String, ArrayList<String>> info = map;
        Log.v("uris: ", uris.toString());
        Log.v("names: ", names.toString());

        final Spinner spinner1 = (Spinner) findViewById(R.id.playlist_name1);
        ArrayAdapter<String> pl1adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, names);
        pl1adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setPrompt("Choose Playlist");

        spinner1.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        pl1adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        final Spinner spinner2 = (Spinner) findViewById(R.id.playlist_name2);
        ArrayAdapter<String> pl2adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, names);
        pl1adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setPrompt("Choose Playlist");

        spinner2.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        pl2adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        final Spinner spinner3 = (Spinner) findViewById(R.id.playlist_name3);
        ArrayAdapter<String> pl3adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, names);
        pl1adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setPrompt("Choose Playlist");

        spinner3.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        pl3adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));


        /*String pl1 = names.get(0);
        String uri1 = uris.get(0);
        Log.v("Playlist 1: ", pl1);
        Log.v("uri 1: ", uri1);
        String uri = uris.get(1);
        mPlayer.playUri(null, uri1, 0, 0);
        mListView = (ListView) findViewById(R.id.list_view);
        List<HashMap<String, Object>> outputs= new ArrayList<>();
        outputs.add(map);
        ListAdapter adapter = new SimpleAdapter(spotifyloginpage.this, outputs, R.layout.list_item,
                new String[] {"names","pllocation"},
                new int[] { R.id.playlist_name, R.id.emoji});*/

        //pause_play = (ImageButton) findViewById(R.id.pause_play);
        camera = (ImageButton) findViewById(R.id.camera);
        /*peace_emoji = (ImageButton) findViewById(R.id.peace_emoji);
        raised_hand = (ImageButton) findViewById(R.id.raised_hand);
        forwardbtn = (ImageButton) findViewById(R.id.forwardbtn);
        backbtn = (ImageButton) findViewById(R.id.backbtn);*/


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Playlists_manipulation.this, MainActivity.class);
                String fist_hand_emoji_pl_name = spinner1.getSelectedItem().toString();
                int fheplnumber = names.indexOf(fist_hand_emoji_pl_name);
                String peace_emoji_pl_name = spinner2.getSelectedItem().toString();
                int peplnumber = names.indexOf(peace_emoji_pl_name);
                String raised_hand_pl_name = spinner3.getSelectedItem().toString();
                int rhplnumber = names.indexOf(raised_hand_pl_name);
                intent.putExtra("Playlist1", uris.get(fheplnumber));
                intent.putExtra("Playlist2", uris.get(peplnumber));
                intent.putExtra("Playlist3", uris.get(rhplnumber));
                intent.putExtra("playertoken", oauth);
                startActivity(intent);
                //mPlayer.playUri(null, uris.get(fheplnumber), 0, 0);
                //Log.v(fist_hand_emoji_pl_name, uris.get(fheplnumber));
                //Toast.makeText(Playlists_manipulation.this, fist_hand_emoji_pl_name, Toast.LENGTH_SHORT).show();
            }

        });

        /*peace_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String peace_emoji_pl_name = spinner2.getSelectedItem().toString();
                int peplnumber = names.indexOf(peace_emoji_pl_name);
                Toast.makeText(Playlists_manipulation.this, peace_emoji_pl_name, Toast.LENGTH_SHORT).show();
                mPlayer.playUri(null, uris.get(peplnumber), 0, 0);
            }

        });

        raised_hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(SpotifyLoginPage.this, MainActivity.class);
                //startActivity(intent);
                String raised_hand_pl_name = spinner3.getSelectedItem().toString();
                int rhplnumber = names.indexOf(raised_hand_pl_name);
                mPlayer.playUri(null, uris.get(rhplnumber), 0, 0);
                Toast.makeText(Playlists_manipulation.this, raised_hand_pl_name, Toast.LENGTH_SHORT).show();
            }

        });

        pause_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePauseResume();
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mPlayer.skipToPrevious(null);
            }
        });
        forwardbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mPlayer.skipToNext(null);
            }
        });

    }

    public void togglePauseResume() {
        if (mPlayer.getPlaybackState().isPlaying) {
            mPlayer.pause(null);
        } else {
            mPlayer.resume(null);
        }*/
    }
}