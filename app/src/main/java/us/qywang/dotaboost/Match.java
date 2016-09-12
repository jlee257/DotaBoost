package us.qywang.dotaboost;

import java.util.ArrayList;

/**
 * Created by Aspire on 9/9/2016.
 */
public class Match {

    public ArrayList<MatchPlayer> players = new ArrayList<MatchPlayer>();
    public boolean radiant_win;
    public int duration;
    public String match_id;
    public String radiant_score;
    public String dire_score;
    public int start_time;

    public Match() {

//        players.add(new MatchPlayer());
//        players.add(new MatchPlayer());
//        players.add(new MatchPlayer());
//        players.add(new MatchPlayer());
//        players.add(new MatchPlayer());
//        players.add(new MatchPlayer());
//        players.add(new MatchPlayer());
//        players.add(new MatchPlayer());
//        players.add(new MatchPlayer());
//        players.add(new MatchPlayer());
//
//        radiant_win = true;
//        duration = 3600;
//        match_id = "2620639797";
    }
}







class MatchPlayer {
    public String match_id;
    public String account_id;
    public Boolean team_radiant;
    public String hero_name;
    public String item_0_name;
    public String item_1_name;
    public String item_2_name;
    public String item_3_name;
    public String item_4_name;
    public String item_5_name;
    public Boolean is_rank;
    public boolean abandoned;
    public boolean win;
    public int kills;
    public int deaths;
    public int assists;
    public int gold;
    public int gold_per_min;
    public int level;
    public int hero_damage;
    public int tower_damage;

    public MatchPlayer() {
//        account_id = "364848976";
//        team_radiant = true;
//        hero_name = "npc_dota_hero_bloodseeker";
//        item_0_name = "item_blades_of_attack";
//        item_1_name = null;
//        item_2_name = null;
//        item_3_name = null;
//        item_4_name = null;
//        item_5_name = null;
//        kills = 7;
//        deaths = 4;
//        assists = 6;
//        gold = 28328;
//        gold_per_min = 4234;
//        level = 20;
//        hero_damage = 50920;
    }
}
