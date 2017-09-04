package com.baqoba.bakingapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baqoba.bakingapp.R;
import com.baqoba.bakingapp.data.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
//import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 31/08/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<Step> stepResults;
    private Context mContext;
    String layoutName;
    private SimpleExoPlayer player;
    private BandwidthMeter bandwidthMeter;
    private Timeline.Window window;
    private DataSource.Factory mediaDataSourceFactory;
    private boolean shouldAutoPlay;
    private DefaultTrackSelector trackSelector;

    private final StepAdapterOnClickHandler mClickHandler;

    public StepAdapter(Context context, StepAdapterOnClickHandler clickHandler, List<Step> steps, String layoutName) {
        this.mContext = context;
        //  StepResults= new ArrayList<>();
        this.stepResults = steps;
        mClickHandler = clickHandler;
        Log.d("fadksfa;", "dalfd");
        this.layoutName = layoutName;

    }

    public interface StepAdapterOnClickHandler {
        void onClick(int index);
    }

    public class MyItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvShortDescription;



        public MyItemHolder(View itemView) {
            super(itemView);
            tvShortDescription = (TextView) itemView.findViewById(R.id.tv_short_description);



            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mClickHandler.onClick(clickedPosition);
        }
    }
/*
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    //    RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        viewHolder = getViewHolder(parent, inflater);
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.list_step, parent, false);
        viewHolder = new MyItemHolder(view);
        return viewHolder;
    }
*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_step , viewGroup , false);
        viewHolder =new MyItemHolder(view);
        return  viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Step result = stepResults.get(position);
        final MyItemHolder myItemHolder = (MyItemHolder) holder;
        switch(holder.getItemViewType()){
            case 0:
                myItemHolder.tvShortDescription.setText(result.getShortDescription());
                Log.d("holder.get0", String.valueOf(holder.getItemViewType()));
                break;
            case 1:
                myItemHolder.tvShortDescription.setVisibility(View.GONE);
/*
                shouldAutoPlay = true;
                bandwidthMeter = new DefaultBandwidthMeter();
                mediaDataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(mContext, "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);
                window = new Timeline.Window();

                myItemHolder.simpleExoPlayerView.requestFocus();
                TrackSelection.Factory videoTrackSelectionFactory =
                        new AdaptiveTrackSelection.Factory(bandwidthMeter);

                trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

                player = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);

                myItemHolder.simpleExoPlayerView.setPlayer(player);
                */
/*
                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();
                player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
                myItemHolder.simpleExoPlayerView.setPlayer(player);
                // Prepare the MediaSource.
                String userAgent = Util.getUserAgent(mContext, "ClassicalMusicQuiz");
                MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(result.getVideoURL()), new DefaultDataSourceFactory(
                        mContext, userAgent), new DefaultExtractorsFactory(), null, null);
                player.prepare(mediaSource);
                player.setPlayWhenReady(true);
*/
                Log.d("holder.get1", String.valueOf(holder.getItemViewType()));
                break;
            default:
                break;
        }



    }

    @Override
    public int getItemViewType(int position) {
        if(layoutName.equals("R.layout.fragment_master_list")){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return stepResults == null ? 0 : stepResults.size();
    }

    public void add(Step r) {
        stepResults.add(r);
        notifyItemInserted(stepResults.size() - 1);
    }

    public void addAll(List<Step> trailerResults) {
        for (Step result : trailerResults) {
            add(result);
        }
    }
}
