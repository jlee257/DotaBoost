package us.qywang.dotaboost;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Aspire on 9/9/2016.
 */
public class DotaApiHandler extends AsyncTask<String, String, String> {

    private HttpURLConnection urlConnection;
    private String API_Key = "E4E2F6CCA37356210C162C1CC98DCF2F";
    private String match_id0;


    public DotaApiHandler(String match_id) {
        match_id0 = match_id;
    }

    @Override
    protected String doInBackground(String... args) {

        StringBuilder result = new StringBuilder();
        String gmd_url = "http://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/v1";

        try {
            URL url = new URL(gmd_url + "?key=" + API_Key + "&match_id=" + match_id0);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        }catch( Exception e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }


        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        //Do something with the JSON string
        Log.d("JSON", result + "END");
    }
}
