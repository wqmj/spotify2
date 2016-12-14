/*package com.example.caozhenjie.ec601_spotify;

import android.location.GpsStatus;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Get_Playlist extends AsyncTask<String, Void, HashMap<String, ArrayList<String>>>
{

    private Exception mException = null;

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        this.mException = null;
    }

    @Override
    protected HashMap<String, ArrayList<String>> doInBackground(String... params)
    {
        HttpURLConnection urlConnection;
        URL url;
        JSONObject object = null;
        try
        {
            url = new URL("https://api.spotify.com/v1/me/playlists");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.connect();
            InputStream inStream;
            inStream = urlConnection.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
            String temp, response = "";
            while ((temp = bReader.readLine()) != null)
                response += temp;
            bReader.close();
            inStream.close();
            urlConnection.disconnect();
            object = (JSONObject) new JSONTokener(response).nextValue();
        }
        catch (Exception e)
        {
            this.mException = e;
        }
        HashMap<String, ArrayList<String>> ploutput = new HashMap<>();
        try {
            //Get the instance of JSONArray that contains JSONObjects
            JSONObject firstreduced = object.optJSONObject("items");
            JSONArray names = firstreduced.optJSONArray("name");
            JSONArray pllocation = firstreduced.optJSONArray("uri");
            ArrayList<String> namestring = new ArrayList<>();
            for (int i=0;i<names.length();i++){
                namestring.add(names.getString(i));
            }
            Log.v("names in get_playlist: ", namestring.toString());
            ploutput.put("name", namestring);
            ArrayList<String> pllocationstring = new ArrayList<>();
            for (int j=0;j<pllocation.length();j++){
                pllocationstring.add(pllocation.getString(j));
            }
            Log.v("loc in get_playlist: ", pllocationstring.toString());
            ploutput.put("pllocation",pllocationstring);
        }
        catch(Exception e)
        {
            this.mException = e;
        }
        return(ploutput);
    }

    public void onPostExecute(HashMap<String,ArrayList<String>> ploutput){
        super.onPostExecute(ploutput);
    }
}*/