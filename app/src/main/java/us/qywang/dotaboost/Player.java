package us.qywang.dotaboost;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Aspire on 9/9/2016.
 */
public class Player implements Serializable {

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

        // Replace with your code
//        personaname = "Blink.blink182";
//        avatarmedium = "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/avatars/d4/d44f50ad630368dfdf8c4458586a64b5f6a1d29b_medium.jpg";
//
//        kda = Math.random() * 4;
//        win_rate = (int) (Math.random() * 100);
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



class PlayedHero implements Serializable {

    public String hero_name;
    public int count;
    public int win;

    public PlayedHero() {

        // Replace with your code
//        hero_name = "bloodseeker";
//        win = (int) (Math.random() * 3);
//        count = win + (int) (Math.random() * 3);
    }
}


class MatchSimple implements Serializable {
    public String match_id;
    public Boolean win;
    public String match_type;
    public String hero_name;
    public int kill;
    public int death;
    public int assist;

    public MatchSimple() {

//        // Replace with your code
//        hero_name = "bloodseeker";
//        match_id = "2620639797";
//        win = true;
//        match_type = "Ranked";
//        kill = (int) (Math.random() * 5);
//        death = (int) (Math.random() * 4);
//        assist = (int) (Math.random() * 10);
    }
}