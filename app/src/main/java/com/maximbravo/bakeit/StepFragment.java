package com.maximbravo.bakeit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.jarvanmo.exoplayerview.ui.ExoVideoPlaybackControlView;
import com.jarvanmo.exoplayerview.ui.ExoVideoView;
import com.jarvanmo.exoplayerview.ui.SimpleMediaSource;
import com.jarvanmo.exoplayerview.widget.SuperAspectRatioFrameLayout;
import com.squareup.picasso.Picasso;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.jarvanmo.exoplayerview.ui.ExoVideoPlaybackControlView.SENSOR_LANDSCAPE;
import static com.jarvanmo.exoplayerview.ui.ExoVideoPlaybackControlView.SENSOR_PORTRAIT;
import static com.maximbravo.bakeit.BakingUtils.currentRecipe;
import static com.maximbravo.bakeit.BakingUtils.currentStep;

/**
 * Created by Kids on 6/13/2017.
 */

public class StepFragment extends Fragment {
    private OnFabSelected mCallbacks;

    private ExoVideoView videoView;
    public StepFragment() {

    }

    public interface OnFabSelected {
        void onNextSelected();
        void onPrevSelected();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mCallbacks = (OnFabSelected) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mCallbacks = (OnFabSelected) activity;
    }

    public static boolean fullscreenmode = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_step, container, false);

        if(getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE && !BakingUtils.twopanemode) {
            fullscreenmode = true;
        } else {
            fullscreenmode = false;
        }

        if(!fullscreenmode) {
            TextView descriptionTextView = (TextView) rootView.findViewById(R.id.step_description);
            descriptionTextView.setText(currentStep.getDescription());
            descriptionTextView.setMovementMethod(new ScrollingMovementMethod());
            if (!BakingUtils.twopanemode) {
                TextView shortDescriptionTextView = (TextView) rootView.findViewById(R.id.step_short_description);
                if (currentStep.getShortDescription().length() > 18) {
                    shortDescriptionTextView.setTextSize(25);
                }
                shortDescriptionTextView.setText(currentStep.getShortDescription());

                FloatingActionButton next = (FloatingActionButton) rootView.findViewById(R.id.next_fab);
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mCallbacks.onNextSelected();
                    }
                });

                FloatingActionButton prev = (FloatingActionButton) rootView.findViewById(R.id.prev_fab);
                prev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mCallbacks.onPrevSelected();

                    }
                });

            }

        }

        if(videoView == null) {
            if(currentStep.isVideo()) {
                initializePlayer(rootView, currentStep.getVideoURL(), currentStep.getShortDescription());
            } else if(!currentStep.getVideoURL().equals("") && !currentStep.getVideoURL().isEmpty() && currentStep.getVideoURL() != null) {
                videoView = (ExoVideoView) rootView.findViewById(R.id.playerView);
                videoView.setVisibility(View.GONE);

                ImageView pictureView = (ImageView) rootView.findViewById(R.id.pictureView);
                pictureView.setVisibility(View.VISIBLE);
                System.out.println("Video URL is:" + currentStep.getVideoURL() + ":");
                Picasso.with(getActivity()).load(currentStep.getVideoURL()).into(pictureView);
            } else {
                videoView = (ExoVideoView) rootView.findViewById(R.id.playerView);
                videoView.setVisibility(View.GONE);
            }
        }

        return rootView;
    }


    @Override
    public void onPause() {
        super.onPause();
        if(videoView != null) {
            release();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(videoView != null) {
            release();
        }
    }


    private void initializePlayer(View rootView, String videoURL, String displayName) {
        videoView = (ExoVideoView) rootView.findViewById(R.id.playerView);
        videoView.setResizeMode(SuperAspectRatioFrameLayout.RESIZE_MODE_FIT);

        if(videoURL.length() != 0) {
            SimpleMediaSource mediaSource = new SimpleMediaSource(videoURL);
            mediaSource.setDisplayName(displayName);

            videoView.initSelfPlayer();
            videoView.play(mediaSource);
        } else {
            videoView.setVisibility(View.GONE);
        }
    }

    private void release() {
        videoView.releaseSelfPlayer();
        videoView.invalidate();
        videoView = null;
    }
}
