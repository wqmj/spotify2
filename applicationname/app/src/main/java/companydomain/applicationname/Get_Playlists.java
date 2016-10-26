
package companydomain.applicationname;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Get_Playlists extends AsyncTask<String, Void, JSONObject>
{
    private Exception mException = null;
    
    protected void onPreExecute()
    {
        super.onPreExecute();
        this.mException = null;
    }
    
    protected JSONObject doInBackground(String... params)
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
        JSONObject ploutput = new JSONObject();
        try {
            //Get the instance of JSONArray that contains JSONObjects
            JSONObject firstreduced = object.optJSONObject("items");
            JSONArray names = firstreduced.optJSONArray("name");
            JSONArray pllocation = firstreduced.optJSONArray("uri");

            ploutput.put("names",names);
            ploutput.put("pllocation",pllocation);
        }
        catch(Exception e)
        {
            this.mException = e;
        }
        return(ploutput);
    }

}
