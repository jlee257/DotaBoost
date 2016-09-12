package us.qywang.dotaboost;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MatchViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MatchViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String hero_prefix = "http://cdn.dota2.com/apps/dota2/images/heroes/";
    private static final String hero_suffix = "_lg.png";
    private static final String item_prefix = "http://cdn.dota2.com/apps/dota2/images/items/";
    private static final String item_suffix = "_lg.png";


    // TODO: Rename and change types of parameters
    private Match mMatch;

    private OnFragmentInteractionListener mListener;

    public MatchViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param match Match.
     * @return A new instance of fragment MatchViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MatchViewFragment newInstance(Match match) {
        MatchViewFragment fragment = new MatchViewFragment();
        Bundle args = new Bundle();
        args.putSerializable("MATCH", match);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMatch = (Match) getArguments().getSerializable("MATCH");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mListener != null) {
            mListener.onFragmentInteraction("Match");
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("MatchView", "Rendering Match View");

        if (mMatch.radiant_win) {
            ((TextView) getActivity().findViewById(R.id.matchResult)).setText("Radiant Victory");
            ((TextView) getActivity().findViewById(R.id.matchResult)).setTextColor(getResources().getColor(R.color.dotaGreen));
        } else {
            ((TextView) getActivity().findViewById(R.id.matchResult)).setText("Dire Victory");
            ((TextView) getActivity().findViewById(R.id.matchResult)).setTextColor(getResources().getColor(R.color.dotaRed));
        }
        ((TextView) getActivity().findViewById(R.id.matchDuration)).setText(String.format(
                "(%02d:%02d:%02d)",
                TimeUnit.SECONDS.toHours(mMatch.duration),
                TimeUnit.SECONDS.toMinutes(mMatch.duration) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(mMatch.duration)),
                mMatch.duration - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(mMatch.duration))
        ));
        ((TextView) getActivity().findViewById(R.id.matchIdNumber)).setText(mMatch.match_id);



        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layoutMatchPlayerList = (LinearLayout) getActivity().findViewById(R.id.matchPlayerList);


        Log.d("PlayerView", ">>>>Rendering Match View Players");

        int highest_gold = 1;
        int highest_damage = 1;
        for (int i = 0; i < mMatch.players.size(); i++) {
            MatchPlayer mPlayer = mMatch.players.get(i);
            if (mPlayer.gold > highest_gold) {
                highest_gold = mPlayer.gold;
            }
            if (mPlayer.hero_damage > highest_damage) {
                highest_damage = mPlayer.hero_damage;
            }
        }

        for (int i = 0; i < mMatch.players.size(); i++) {
            Log.d("PlayerView", ">>>>>>>>Rendering Match View Player " + Integer.toString(i));
            View matchPlayer = inflater.inflate(R.layout.fragment_match_view_player_detail, null);
            MatchPlayer mPlayer = mMatch.players.get(i);

            if (mPlayer.team_radiant) {
                ((LinearLayout) matchPlayer.findViewById(R.id.matchPlayerLayout))
                        .setBackgroundColor(getResources().getColor(R.color.backgroundRadiant));
            } else {
                ((LinearLayout) matchPlayer.findViewById(R.id.matchPlayerLayout))
                        .setBackgroundColor(getResources().getColor(R.color.backgroundDire));
            }

            final String playerIdPass = mPlayer.account_id;

            ((LinearLayout) matchPlayer.findViewById(R.id.matchPlayerLayout))
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String title = playerIdPass;
                            Toast.makeText(getActivity(), "Loading Player data...", Toast.LENGTH_LONG).show();
                            PlayerViewFragment playerViewFragment = PlayerViewFragment.newInstance(new Player(), playerIdPass);
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(
                                    R.id.mainLayout,
                                    playerViewFragment,
                                    playerViewFragment.getTag()
                            );
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    });

            new DownloadImageTask((ImageView) matchPlayer.findViewById(R.id.matchPlayerIcon))
                    .execute(hero_prefix + mPlayer.hero_name + hero_suffix);

            ((TextView) matchPlayer.findViewById(R.id.matchPlayerName)).setText(mPlayer.account_id);

            new DownloadImageTask((ImageView) matchPlayer.findViewById(R.id.matchPlayerItemIcon0))
                    .execute(item_prefix + mPlayer.item_0_name + item_suffix);
            new DownloadImageTask((ImageView) matchPlayer.findViewById(R.id.matchPlayerItemIcon1))
                    .execute(item_prefix + mPlayer.item_1_name + item_suffix);
            new DownloadImageTask((ImageView) matchPlayer.findViewById(R.id.matchPlayerItemIcon2))
                    .execute(item_prefix + mPlayer.item_2_name + item_suffix);
            new DownloadImageTask((ImageView) matchPlayer.findViewById(R.id.matchPlayerItemIcon3))
                    .execute(item_prefix + mPlayer.item_3_name + item_suffix);
            new DownloadImageTask((ImageView) matchPlayer.findViewById(R.id.matchPlayerItemIcon4))
                    .execute(item_prefix + mPlayer.item_4_name + item_suffix);
            new DownloadImageTask((ImageView) matchPlayer.findViewById(R.id.matchPlayerItemIcon5))
                    .execute(item_prefix + mPlayer.item_5_name + item_suffix);


            ((TextView) matchPlayer.findViewById(R.id.matchPlayerGold))
                    .setText(String.format("%d (%d/min)", mPlayer.gold, mPlayer.gold_per_min));
            if (mPlayer.deaths < 1) {
                ((TextView) matchPlayer.findViewById(R.id.matchPlayerKDA))
                        .setText(String.format("%d / %d / %d (Perfect)",
                                mPlayer.kills, mPlayer.deaths, mPlayer.assists));
            } else {
                ((TextView) matchPlayer.findViewById(R.id.matchPlayerKDA))
                        .setText(String.format("%d / %d / %d (%.2f)",
                                mPlayer.kills, mPlayer.deaths, mPlayer.assists,
                                ((double) mPlayer.kills + mPlayer.assists) / mPlayer.deaths));
            }
            ((TextView) matchPlayer.findViewById(R.id.matchPlayerDamage))
                    .setText(String.format("%d", mPlayer.hero_damage));

            layoutMatchPlayerList.addView(matchPlayer);

            ViewGroup.LayoutParams p = ((LinearLayout) matchPlayer.findViewById(R.id.matchPlayerGoldBarBack)).getLayoutParams();
            ViewGroup.LayoutParams p_gold = ((RelativeLayout) matchPlayer.findViewById(R.id.matchPlayerGoldBar)).getLayoutParams();
            ViewGroup.LayoutParams p_kill = ((RelativeLayout) matchPlayer.findViewById(R.id.matchPlayerKillBar)).getLayoutParams();
            ViewGroup.LayoutParams p_death = ((RelativeLayout) matchPlayer.findViewById(R.id.matchPlayerDeathBar)).getLayoutParams();
            ViewGroup.LayoutParams p_damage = ((RelativeLayout) matchPlayer.findViewById(R.id.matchPlayerDamageBar)).getLayoutParams();

            int kda_sum = mPlayer.kills + mPlayer.deaths + mPlayer.assists;
            if (kda_sum < 1) kda_sum = 1;

            p_gold.width = (int) (((double) mPlayer.gold / highest_gold) * p.width);
            p_kill.width = (int) (((double) mPlayer.kills / kda_sum) * p.width);
            p_death.width = (int) (((double) mPlayer.deaths / kda_sum) * p.width);
            p_damage.width = (int) (((double) mPlayer.hero_damage / highest_damage) * p.width);

            ((RelativeLayout) matchPlayer.findViewById(R.id.matchPlayerGoldBar)).requestLayout();
            ((RelativeLayout) matchPlayer.findViewById(R.id.matchPlayerKillBar)).requestLayout();
            ((RelativeLayout) matchPlayer.findViewById(R.id.matchPlayerDeathBar)).requestLayout();
            ((RelativeLayout) matchPlayer.findViewById(R.id.matchPlayerDamageBar)).requestLayout();
        }

        Log.d("PlayerView", ">>>>Rendering Match View Players FINISHED");
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String title);
    }
}
