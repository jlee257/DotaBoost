package us.qywang.dotaboost;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Qingyu on 9/8/2016.
 */
public class DotaAPI extends AsyncTask<Void, Void, String> {
    private static String API_Key = "E4E2F6CCA37356210C162C1CC98DCF2F";


    public static String get_match_detail(String match_num) {
        String gmd_url = "http://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/v1";

        try {
            URL url = new URL(gmd_url + "?key=" + API_Key + "&match_id=" + match_num);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {

                InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());

                BufferedReader bufferedReader = new BufferedReader(reader);

                StringBuilder stringBuilder = new StringBuilder();

                String line;

                while ((line = bufferedReader.readLine()) != null) {

                    stringBuilder.append(line);
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR!!!", e.getMessage(), e);
            return null;
        }
    }

    public static String get_hero_info() {

        StringBuilder result = new StringBuilder();

        String gmd_url = "http://api.steampowered.com/IEconDOTA2_570/GetHeroes/v1";
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(gmd_url + "?key=" + API_Key);
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
            return result.toString();
        }

    }

    public static String get_item_info() {

        StringBuilder result = new StringBuilder();

        String gmd_url = "http://api.steampowered.com/IEconDOTA2_570/GetGameItems/v1";
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(gmd_url + "?key=" + API_Key);
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
            return result.toString();
        }

    }



    public static String get_match_history(String playerid) {

        StringBuilder result = new StringBuilder();

        String gmd_url = "http://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/v1";
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(gmd_url + "?key=" + API_Key + "&account_id=" + playerid);
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
            return result.toString();
        }

    }

    public static String steam_info(String steam_id){
        StringBuilder result = new StringBuilder();

        String gmd_url = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/";
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(gmd_url + "?key=" + API_Key + "&steamids=" + steam_id);
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
            return result.toString();
        }


    }


    @Override
    protected String doInBackground(Void... params) {
        return null;
    }
}
