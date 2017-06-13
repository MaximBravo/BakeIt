package com.maximbravo.bakeit;

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
