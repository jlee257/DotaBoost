package us.qywang.dotaboost;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayerViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayerViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String hero_prefix = "http://cdn.dota2.com/apps/dota2/images/heroes/";
    private static final String hero_suffix = "_lg.png";
    private static final String item_prefix = "http://cdn.dota2.com/apps/dota2/images/items/";
    private static final String item_suffix = "_lg.png";

    // TODO: Rename and change types of parameters
    private Player mPlayer;
    private String mPlayerId;

    private OnFragmentInteractionListener mListener;

    public PlayerViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param player    player that being displayed
     * @return A new instance of fragment PlayerViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayerViewFragment newInstance(Player player, String playerId) {
        PlayerViewFragment fragment = new PlayerViewFragment();
        Bundle args = new Bundle();
        args.putSerializable("PLAYER", player);
        args.putString("PLAYERID", playerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPlayer = (Player) getArguments().getSerializable("PLAYER");
            mPlayerId = getArguments().getString("PLAYERID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mListener != null) {
            mListener.onFragmentInteraction(mPlayer.personaname);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player_view, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("PlayerView", "Rendering Player View");

        ((TextView) getActivity().findViewById(R.id.playerName)).setText(mPlayer.personaname);
        ((TextView) getActivity().findViewById(R.id.playerId)).setText(mPlayerId);
        new DownloadImageTask((ImageView) getActivity().findViewById(R.id.playerPicture))
                .execute(mPlayer.avatarmedium);

        ((TextView) getActivity().findViewById(R.id.playerKDA)).setText(String.format("%.2f", mPlayer.kda));
        ((TextView) getActivity().findViewById(R.id.playerWinRate)).setText(String.format("%d%%", mPlayer.win_rate));


        LayoutInflater inflater =  (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layoutMostPlayed = (LinearLayout) getActivity().findViewById(R.id.playerMostPlayedList);

        Log.d("PlayerView", ">>>>Rendering Player View Most Played");
        for (int i = 0; i < mPlayer.most_played.size(); i++) {

            Log.d("PlayerView", ">>>>>>>>Rendering Player View Most Played " + Integer.toString(i));
            View mostPlayed = inflater.inflate(R.layout.fragment_player_view_most_played, null);
            PlayedHero mHero = mPlayer.most_played.get(i);

            new DownloadImageTask((ImageView) mostPlayed.findViewById(R.id.playerMostPlayedIcon))
                    .execute(hero_prefix + mHero.hero_name + hero_suffix);

            if (mHero.count < 1) {
                mHero.count = 1;
            }

            ((TextView) mostPlayed.findViewById(R.id.playerMostPlayedCount)).setText(Integer.toString(mHero.count));
            ((TextView) mostPlayed.findViewById(R.id.playerMostPlayedVictory)).setText(Integer.toString(mHero.win) + "W");
            ((TextView) mostPlayed.findViewById(R.id.playerMostPlayedDefeat)).setText(Integer.toString(mHero.count - mHero.win) + "L");
            ((TextView) mostPlayed.findViewById(R.id.playerMostPlayedRate)).setText(
                    String.format("%.1f%%", ((double) mHero.win) * 100 / mHero.count));

//            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65, getResources().getDisplayMetrics());
            layoutMostPlayed.addView(mostPlayed);

            ViewGroup.LayoutParams p = ((LinearLayout) mostPlayed.findViewById(R.id.playerMostPlayedBar)).getLayoutParams();
            ViewGroup.LayoutParams p_child = ((RelativeLayout) mostPlayed.findViewById(R.id.playerMostPlayedBarVictory)).getLayoutParams();
            p_child.width = (int) (((double) mHero.win / (double) mHero.count) * p.width);

            ((RelativeLayout) mostPlayed.findViewById(R.id.playerMostPlayedBarVictory)).requestLayout();
        }
        Log.d("PlayerView", ">>>>Rendering Player View Most Played FINISHED");
        LinearLayout layoutRecentMatches = (LinearLayout) getActivity().findViewById(R.id.playerMatchesList);

        Log.d("PlayerView", ">>>>Rendering Player View Recent Matches");
        for (int i = 0; i < mPlayer.matches.size(); i++) {

            Log.d("PlayerView", ">>>>>>>>Rendering Player View Recent Match " + Integer.toString(i));
            View simpleMatch = inflater.inflate(R.layout.fragment_player_view_simple_match, null);
            MatchSimple mMatch = mPlayer.matches.get(i);

            new DownloadImageTask((ImageView) simpleMatch.findViewById(R.id.playerMatchHeroIcon))
                    .execute(hero_prefix + mMatch.hero_name + hero_suffix);

            if (mMatch.win) {
                ((RelativeLayout) simpleMatch.findViewById(R.id.playerMatchLayout))
                        .setBackgroundColor(getResources().getColor(R.color.backgroundVictory));
            } else {
                ((RelativeLayout) simpleMatch.findViewById(R.id.playerMatchLayout))
                        .setBackgroundColor(getResources().getColor(R.color.backgroundDefeat));
            }

            final String matchIdPass = mMatch.match_id;

            ((RelativeLayout) simpleMatch.findViewById(R.id.playerMatchLayout))
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Match match = new Match();
                            try {
                                match = JsonConverter.get_match(matchIdPass);
                            } catch (Exception e) {
                                Log.d("PlayerView", ">>>>Could not load match");
                            }
                            MatchViewFragment matchViewFragment = MatchViewFragment.newInstance(match);
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(
                                    R.id.mainLayout,
                                    matchViewFragment,
                                    matchViewFragment.getTag()
                            );
                            Log.d("PlayerView",
                                    ">>>>>>>>Rendering Player View Recent match_id: " +
                                            matchIdPass);

                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    }
                    );

            ((TextView) simpleMatch.findViewById(R.id.playerMatchType)).setText(mMatch.match_type);
            if (mMatch.death < 1) {
                ((TextView) simpleMatch.findViewById(R.id.playerMatchKDA)).setText(
                        String.format("%d / %d / %d (Perfect)",
                                mMatch.kill, mMatch.death, mMatch.assist));
            } else {
                ((TextView) simpleMatch.findViewById(R.id.playerMatchKDA)).setText(
                        String.format("%d / %d / %d (%.2f)",
                                mMatch.kill, mMatch.death, mMatch.assist,
                                ((double) mMatch.kill + mMatch.assist) / mMatch.death));

            }


            layoutRecentMatches.addView(simpleMatch);

            int total = mMatch.kill + mMatch.death + mMatch.assist;
            if (total < 1) total = 1;

            ViewGroup.LayoutParams p = ((LinearLayout) simpleMatch.findViewById(R.id.playerMatchBarKDA)).getLayoutParams();
            ViewGroup.LayoutParams p_kill = ((RelativeLayout) simpleMatch.findViewById(R.id.playerMatchBarKill)).getLayoutParams();
            ViewGroup.LayoutParams p_death = ((RelativeLayout) simpleMatch.findViewById(R.id.playerMatchBarDeath)).getLayoutParams();

            p_kill.width = (int) (((double) mMatch.kill / total) * p.width);
            p_death.width = (int) (((double) mMatch.death / total) * p.width);

            ((RelativeLayout) simpleMatch.findViewById(R.id.playerMatchBarKill)).requestLayout();
            ((RelativeLayout) simpleMatch.findViewById(R.id.playerMatchBarDeath)).requestLayout();

        }
        Log.d("PlayerView", ">>>>Rendering Player View Recent Matches FINISHED");

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
