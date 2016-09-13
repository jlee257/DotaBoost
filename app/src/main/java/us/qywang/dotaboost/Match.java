package us.qywang.dotaboost;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Aspire on 9/9/2016.
 */
public class Match implements Serializable {

    public ArrayList<MatchPlayer> players = new ArrayList<MatchPlayer>();
    public boolean radiant_win;
    public int duration;
    public String match_id;
    public String radiant_score;
    public String dire_score;
    public int start_time;

    public Match() {

        // Replace with your code
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
//        duration = (int) (Math.random() * 4500);
//        match_id = "2620639797";
    }
}







class MatchPlayer implements Serializable {

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

        // Replace with your code
//        account_id = "364848976";
//        team_radiant = true;
//        hero_name = "bloodseeker";
//        item_0_name = "blades_of_attack";
//        item_1_name = null;
//        item_2_name = null;
//        item_3_name = null;
//        item_4_name = null;
//        item_5_name = null;
//        kills = (int) (Math.random() * 5);
//        deaths = (int) (Math.random() * 4);
//        assists = (int) (Math.random() * 10);
//        gold = (int) (Math.random() * 20000);
//        gold_per_min = (int) (Math.random() * 700);
//        level = (int) (Math.random() * 24) + 1;
//        hero_damage = (int) (Math.random() * 50000);;
    }
}
