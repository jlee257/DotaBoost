package us.qywang.dotaboost;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by 25717 on 9/9/2016.
 */
public class JsonConverter {


    public static ArrayList get_match_id(String player_id) throws JSONException {
        Log.d("PlayerID is", player_id);

        Log.d("detail is", DotaAPI.get_player_detail("364848976"));

        JSONObject obj = new JSONObject(DotaAPI.get_player_detail(player_id));


        JSONArray matches =  obj.getJSONObject("result").getJSONArray("matches");

//        JSONObject matches = obj.getJSONObject("result").getJSONObject("matches");

//        Log.d("num_results", obj.getJSONObject("result").get("num_results").toString());
//        Log.d("id", obj.getJSONObject("result").getJSONArray("matches").getJSONObject(1).get("match_id").toString());



//        Log.d("num_results", obj.getJSONObject("result").getJSONObject("matches").getJSONObject("1").get("match_id").toString());



        ArrayList match_ids = new ArrayList();


        for (int i = 0; i< 10; i++){
            match_ids.add(matches.getJSONObject(i).get("match_id"));


        }

        return match_ids;


    }


}
