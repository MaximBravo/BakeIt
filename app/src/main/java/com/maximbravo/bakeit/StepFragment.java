package com.maximbravo.bakeit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.jarvanmo.exoplayerview.ui.ExoVideoPlaybackControlView.SENSOR_LANDSCAPE;
import static com.jarvanmo.exoplayerview.ui.ExoVideoPlaybackControlView.SENSOR_PORTRAIT;

/**
 * Created by Kids on 6/13/2017.
 */

public class StepFragment extends Fragment {
    private int position;
    private Recipe recipe;
    private OnFabSelected mCallbacks;

    private ExoVideoView videoView;
    public StepFragment() {

    }

    public interface OnFabSelected {
        public void onNextSelected(Recipe recipe, int stepPosition);
        public void onPrevSelected(Recipe recipe, int currentPosition);
    }

    public void setPosition(Recipe recipe, int position){
        this.position = position;
        this.recipe = recipe;
        BakingUtils.currentRecipe = recipe;
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
        recipe = BakingUtils.currentRecipe;

        Step currentStep = BakingUtils.currentStep;
        if(currentStep == null) {
            currentStep = recipe.getSteps().get(position);
        }

        if(getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE && !BakingUtils.twopanemode) {
            fullscreenmode = true;
        } else {
            fullscreenmode = false;
        }

        if(!fullscreenmode) {
            TextView descriptionTextView = (TextView) rootView.findViewById(R.id.step_description);
            descriptionTextView.setText(currentStep.getDescription());

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
                        //release();
                        mCallbacks.onNextSelected(recipe, position);
                    }
                });

                FloatingActionButton prev = (FloatingActionButton) rootView.findViewById(R.id.prev_fab);
                prev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // initializePlayer(rootView, "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4", "Little mouse");
                        //release();
                        mCallbacks.onPrevSelected(recipe, position);

                    }
                });

            }


        }

        if(videoView == null) {
            initializePlayer(rootView, currentStep.getVideoURL(), currentStep.getShortDescription());
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onPause() {
        super.onPause();
        BakingUtils.currentRecipe = recipe;
        if(videoView != null) {
            //release();
            videoView.pause();
        }
        BakingUtils.currentStep = BakingUtils.currentRecipe.getSteps().get(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //release();
    }


    private void initializePlayer(View rootView, String videoURL, String displayName) {
        videoView = (ExoVideoView) rootView.findViewById(R.id.playerView);
        videoView.setResizeMode(SuperAspectRatioFrameLayout.RESIZE_MODE_FIT);

        if(videoURL.length() != 0) {
            SimpleMediaSource mediaSource = new SimpleMediaSource(videoURL);
            mediaSource.setDisplayName(displayName);

            videoView.initSelfPlayer();
            videoView.play(mediaSource);
        }
    }
    private void changeToPortrait() {

        WindowManager.LayoutParams attr = getActivity().getWindow().getAttributes();
        attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Window window = getActivity().getWindow();
        window.setAttributes(attr);
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }


    private void changeToLandscape() {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window window = getActivity().getWindow();
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }
    private void release() {
        videoView.releaseSelfPlayer();
        videoView.invalidate();
        videoView = null;

    }

}
