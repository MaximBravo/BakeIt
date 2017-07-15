package com.maximbravo.bakeit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jarvanmo.exoplayerview.ui.ExoVideoView;
import com.jarvanmo.exoplayerview.ui.SimpleMediaSource;
import com.jarvanmo.exoplayerview.widget.SuperAspectRatioFrameLayout;
import com.squareup.picasso.Picasso;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
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
        rootView.findViewById(R.id.loadingOverlay).setVisibility(View.VISIBLE);
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
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final View rootView = view;
        //there is a url either video or picture
        if(!currentStep.getUrl().equals("") && !currentStep.getUrl().isEmpty() && currentStep.getUrl() != null) {
            //videoView actually needs to be initialized
            if(videoView == null) {
                //if the video url holds a video url
                if (currentStep.isVideo()) {
                    initializePlayer(rootView, currentStep.getUrl(), currentStep.getShortDescription());
                } else { //video url is acturlly a picture
                    videoView = (ExoVideoView) rootView.findViewById(R.id.playerView);
                    videoView.setVisibility(View.GONE);

                    /*
                        Note:
                        The reason that I am doing the url this way is because if there is a
                        videoUrl and not a thumbnailUrl then it should load the video
                        If there is no video url but there is thumbnail then we will load the thumbnail
                        if there is not thumbnail or video than we load nothing. In the Step class
                        we only have one field called url because when we get the data from the json
                        will do the above explained logic and adjust the isVideo parameter. Is there
                        anything wrong with this approach?

                        Also I saw that in review #4 there was a comment in recipe_list_item.xml
                        saying that i should add an ImageView there but it doesn't seem like the right
                        place since I thought that we need to load the image for the actual step not
                        the recipe item.
                     */
                    ImageView pictureView = (ImageView) rootView.findViewById(R.id.pictureView);
                    pictureView.setVisibility(View.VISIBLE);
                    System.out.println("Video URL is:" + currentStep.getUrl() + ":");
                    Picasso.with(getActivity()).load(currentStep.getUrl()).into(pictureView);
                    rootView.findViewById(R.id.loadingOverlay).setVisibility(View.INVISIBLE);
                }
            } else {// video needs to be resumed
                SimpleMediaSource mediaSource = new SimpleMediaSource(currentStep.getUrl());
                mediaSource.setDisplayName(currentStep.getShortDescription());
                videoView.play(mediaSource);
            }
        } else {
            videoView = (ExoVideoView) rootView.findViewById(R.id.playerView);
            videoView.setVisibility(View.GONE);
            rootView.findViewById(R.id.loadingOverlay).setVisibility(View.INVISIBLE);
        }
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
            RelativeLayout relativeLayout = (RelativeLayout) rootView.findViewById(R.id.loadingOverlay);
            relativeLayout.setVisibility(View.INVISIBLE);
        } else {
            videoView.setVisibility(View.GONE);
            RelativeLayout relativeLayout = (RelativeLayout) rootView.findViewById(R.id.loadingOverlay);
            relativeLayout.setVisibility(View.INVISIBLE);
        }

    }

    private void release() {
        videoView.releaseSelfPlayer();
        videoView.invalidate();
        videoView = null;
    }
}
