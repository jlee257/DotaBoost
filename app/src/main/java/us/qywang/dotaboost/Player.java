package us.qywang.dotaboost;

import java.util.ArrayList;

public class Player {

    public String personaname;
    public String avatarmedium;
    public double kill;
    public double death;
    public double assist;
    public double kda;
    public int win_rate;
    public ArrayList<PlayedHero> most_played = new ArrayList<PlayedHero>();
    public ArrayList<MatchSimple> matches = new ArrayList<MatchSimple>();

    public Player() {

//        // Replace with your code
//        personaname = "Blink.blink182";
//        avatarmedium = "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/avatars/d4/d44f50ad630368dfdf8c4458586a64b5f6a1d29b_medium.jpg";
//
//        kda = 3.2;
//        win_rate = 58;
//        most_played.add(new PlayedHero());
//        most_played.add(new PlayedHero());
//        most_played.add(new PlayedHero());
//
//        matches.add(new MatchSimple());
//        matches.add(new MatchSimple());
//        matches.add(new MatchSimple());
//        matches.add(new MatchSimple());
//        matches.add(new MatchSimple());
//        matches.add(new MatchSimple());

    }
}



class PlayedHero {

    public String hero_name;
    public int count;
    public int win;
    public double hero_win_rate;


    public PlayedHero() {

//        // Replace with your code
//        hero_name = "npc_dota_hero_bloodseeker";
//        count = 7;
//        hero_win_rate = 65;
    }
}


class MatchSimple {
    public String match_id;
    public Boolean win;
    public String match_type;
    public int kill;
    public int death;
    public int assist;

    public MatchSimple() {
//
//        // Replace with your code
//        match_id = "2620639797";
//        win = true;
//        match_type = "Ranked";
//        kill = 4;
//        death = 5;
//        assist = 11;
    }
}
