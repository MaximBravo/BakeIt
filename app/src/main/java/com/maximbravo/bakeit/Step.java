package com.maximbravo.bakeit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Kids on 6/13/2017.
 */

class Step {
    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;

    public Step(int id, String shortDescription, String description, String videoURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
    }

    public int getId() {
        return id;
    }

    public View getView(Context context){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        LinearLayout stepView = (LinearLayout) layoutInflater.inflate(R.layout.step_list_item, null);

        TextView stepNumber = (TextView) stepView.findViewById(R.id.step_id);
        TextView stepNameView = (TextView) stepView.findViewById(R.id.step_short_description);
        TextView stepDescriptionView = (TextView) stepView.findViewById(R.id.step_description);

        stepNumber.setText("" + (id ));
        stepNameView.setText(shortDescription);
        stepDescriptionView.setText(description);

        return stepView;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }
}
