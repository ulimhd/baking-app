package com.baqoba.bakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baqoba.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static com.baqoba.bakingapp.ui.MasterListFragment.step;
import static com.baqoba.bakingapp.ui.MasterListFragment.totalStep;

public class StepsFragment extends Fragment implements ExoPlayer.EventListener{
//public class StepsFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    View rootView;

    int index=0;
    String videoUrl, stepDescription;

    private static final String TAG = IngredientActivity.class.getSimpleName();

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;

    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView playerView;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

    public static Bundle bundleSet = new Bundle();

    TextView tvDescription;
    Button btnNext, btnPrevious;

    private static long position = 0;

    public StepsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
 /*   public static StepsFragment newInstance(String param1, String param2) {
        StepsFragment fragment = new StepsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    } */
/*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.exo_player);
        tvDescription = (TextView) rootView.findViewById(R.id.tv_description);
        btnNext = (Button) rootView.findViewById(R.id.btn_next_step);
        btnPrevious = (Button) rootView.findViewById(R.id.btn_prev_step);

        Bundle bundle = this.getArguments();


        if(bundle != null){
            try {
                index = bundle.getInt("item_index");
                videoUrl = step.get(index).getVideoURL();
                stepDescription = step.get(index).getDescription();
                tvDescription.setText(stepDescription);

                if(!videoUrl.isEmpty()){
                    initializeMediaSession();
                 //   restExoPlayer(0, false);
                    initializePlayer(Uri.parse(videoUrl));
                }
                else{
                    mPlayerView.setVisibility(View.GONE);
                }





            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            mPlayerView.setVisibility(View.GONE);
            tvDescription.setText("Ingredients");
        }




        btnNext.setOnClickListener(new View.OnClickListener(){
            int nextVal = index+1;

            @Override
            public void onClick(View v) {
                if(nextVal<=totalStep-1) {
                    bundleSet.putInt("item_index", nextVal);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    StepsFragment stepsFragment = new StepsFragment();
                    stepsFragment.setArguments(bundleSet);
                    fragmentManager.beginTransaction()
                            .replace(R.id.detail_container, stepsFragment)
                            .commit();


                    }
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener(){
            int prevVal = index-1;

            @Override
            public void onClick(View v) {

                if(prevVal>=0) {
                    bundleSet.putInt("item_index", prevVal);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    StepsFragment stepsFragment = new StepsFragment();
                    stepsFragment.setArguments(bundleSet);
                    fragmentManager.beginTransaction()
                            .replace(R.id.detail_container, stepsFragment)
                            .commit();
                }else{
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    IngredientFragment ingredientFragment = new IngredientFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.detail_container, ingredientFragment)
                            .commit();
                }

            }
        });


        // Inflate the layout for this fragment
        return rootView;




    }
/*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



  /*  @Override
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
*/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
 /*   public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
*/

    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }

    private void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            mExoPlayer.addListener(this);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "StepsFragment");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    /**
     * Release the player when the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mMediaSession.setActive(false);
    }


    // ExoPlayer Event Listeners

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
    }

    /**
     * Method that is called when the ExoPlayer state changes. Used to update the MediaSession
     * PlayBackState to keep in sync.
     * @param playWhenReady true if ExoPlayer is playing, false if it's paused.
     * @param playbackState int describing the state of ExoPlayer. Can be STATE_READY, STATE_IDLE,
     *                      STATE_BUFFERING, or STATE_ENDED.
     */
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
    }

    @Override
    public void onPositionDiscontinuity() {
    }

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

}
