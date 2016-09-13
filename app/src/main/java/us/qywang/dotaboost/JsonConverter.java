package us.qywang.dotaboost;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 25717 on 9/9/2016.
 */



public class JsonConverter {


    public JsonConverter() throws JSONException {

    }



    public static Match get_match(String match_id) throws JSONException {
        String s = "String";

        Log.d("heroinfo", s);

        return get_match(match_id,
                new JSONObject(DotaAPI.get_hero_info()), new JSONObject(DotaAPI.get_item_info()));
    }




    public static Match get_match(String match_id, JSONObject hero_info, JSONObject item_info) throws JSONException {
        String match_str = DotaAPI.get_match_detail(match_id);

        Match match_detail = new Match();

        JSONObject obj = new JSONObject(match_str);

        JSONObject result = obj.getJSONObject("result");

        match_detail.match_id = match_id;
        match_detail.duration = result.getInt("duration");
        match_detail.radiant_win = result.getBoolean("radiant_win");
        match_detail.radiant_score = result.getString("radiant_score");
        match_detail.dire_score = result.getString("dire_score");
        match_detail.start_time = result.getInt("start_time");

        JSONArray players = result.getJSONArray("players");
        HashMap<Integer, String> item_map = new HashMap();

        JSONArray item_list = item_info.getJSONObject("result").getJSONArray("items");
        for (int k = 0; k < item_list.length(); k++){
            JSONObject item_loop = item_list.getJSONObject(k);
            item_map.put(item_loop.getInt("id") ,item_loop.getString("name").substring(5));
        }


        HashMap<Integer, String> hero_map = new HashMap();

        JSONArray hero_list = hero_info.getJSONObject("result").getJSONArray("heroes");
        for (int k = 0; k < hero_list.length(); k++){
            JSONObject hero_loop = hero_list.getJSONObject(k);
            hero_map.put(hero_loop.getInt("id"), hero_loop.getString("name").substring(14));
        }
        for (int i = 0; i < 10; i++){
            JSONObject jplayer = players.getJSONObject(i);
            MatchPlayer player = new MatchPlayer();
            player.match_id = match_id;
            player.account_id = jplayer.getString("account_id");
            player.team_radiant = jplayer.getInt("player_slot") < 5 ? true: false;
            player.win = player.team_radiant == match_detail.radiant_win? true:false;
            player.abandoned = jplayer.getInt("leaver_status") == 3? true: false;
            player.is_rank =result.getInt("lobby_type")== 7? true:false;
            player.hero_name = hero_map.get(jplayer.getInt("hero_id"));
            player.kills = jplayer.getInt("kills");
            player.assists = jplayer.getInt("assists");
            player.deaths = jplayer.getInt("deaths");
            player.gold = jplayer.getInt("gold");
            player.gold_per_min = jplayer.getInt("gold_per_min");
            player.level = jplayer.getInt("level");
            player.hero_damage = jplayer.getInt("hero_damage");
            player.tower_damage = jplayer.getInt("tower_damage");
            player.item_0_name = item_map.get(jplayer.getInt("item_0"));
            player.item_1_name = item_map.get(jplayer.getInt("item_1"));
            player.item_2_name = item_map.get(jplayer.getInt("item_2"));
            player.item_3_name = item_map.get(jplayer.getInt("item_3"));
            player.item_4_name = item_map.get(jplayer.getInt("item_4"));
            player.item_5_name = item_map.get(jplayer.getInt("item_5"));

            match_detail.players.add(player);
        }
        return match_detail;
    }







    public static Player get_player(String account_id) throws JSONException {
        int count = 20;

        Player player = new Player();
        String steam_id = BitConverter.converter(account_id);

        String steam_info = DotaAPI.steam_info(steam_id);
        JSONObject obj = new JSONObject(steam_info);
        JSONObject player_info = obj.getJSONObject("response").getJSONArray("players").getJSONObject(0);

        player.personaname =player_info.getString("personaname");
        player.avatarmedium =player_info.getString("avatarmedium");


        String match_str = DotaAPI.get_match_history(account_id);
        JSONObject objm = new JSONObject(match_str);

        JSONArray matches = objm.getJSONObject("result").getJSONArray("matches");

        ArrayList<Match> match_arr = new ArrayList<>();

        JSONObject map1 = new JSONObject(DotaAPI.get_hero_info());
        JSONObject map2 = new JSONObject(DotaAPI.get_item_info());

        for (int i = 0; i < count; i++){
            match_arr.add(get_match(
                            matches.getJSONObject(i).getString("match_id"),
                            map1,
                            map2));
        }

        int kill_total = 0;
        int assist_total = 0;
        int death_total = 0;
        int win = 0;
        ArrayList<PlayedHero> hero_pool = new ArrayList<>();
        ArrayList<MatchSimple> match_pool = new ArrayList<>();

        for (int j = 0; j < count; j++){
            ArrayList<MatchPlayer> players = match_arr.get(j).players;
            for (int k = 0; k < 10; k++){
                MatchPlayer mp = players.get(k);
                if (mp.account_id.equals(account_id)){
                    MatchSimple new_match = new MatchSimple();
                    new_match.assist = mp.assists;
                    new_match.death = mp.deaths;
                    new_match.kill = mp.kills;
                    new_match.match_id = mp.match_id;
                    new_match.match_type=mp.is_rank? "Rank":"Normal";
                    new_match.win = mp.win;
                    match_pool.add(new_match);

                    kill_total += mp.kills;
                    assist_total += mp.assists;
                    death_total+=mp.deaths;
                    win += mp.win? 1:0;
                    boolean hero_not_in_lst = true;
                    for (int h = 0; h< hero_pool.size(); h++){
                        if (hero_pool.get(h).hero_name.equals(mp.hero_name)){
                            hero_pool.get(h).count += 1;
                            hero_pool.get(h).win += mp.win?1:0;
                            hero_not_in_lst = false;
                        }
                    }
                    if (hero_not_in_lst){
                        PlayedHero new_hero = new PlayedHero();
                        new_hero.hero_name = mp.hero_name;
                        new_hero.count += 1;
                        hero_pool.add(new_hero);
                    }

                }
            }

        }
        double avg_killed = kill_total/count;
        double avg_assist = assist_total/count;
        double avg_death = death_total/count;

        player.kill = avg_killed;
        player.assist = avg_assist;
        player.death = avg_death;
        player.win_rate = win/count;
        player.kda = death_total==0? kill_total+assist_total : ((double) kill_total + (double) assist_total)/(double) death_total;
        player.most_played = hero_pool;


        return player;
    }


}
