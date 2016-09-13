package us.qywang.dotaboost;

import android.util.Log;

/**
 * Created by 25717 on 9/11/2016.
 */
public class BitConverter {

    final private static Long factor = new Long("76561197960265728");


    public static String converter(String account_id){

        Long id = new Long(account_id);
        id = id + factor;
        Log.d("BitConverter", "converted " + account_id + " to " + id.toString());
        return id.toString();
    }
}
