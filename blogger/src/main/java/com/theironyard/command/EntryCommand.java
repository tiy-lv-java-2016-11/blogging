package com.theironyard.command;

import javax.validation.constraints.Size;

/**
 * Created by sparatan117 on 1/12/17.
 */
public class EntryCommand {
    @Size(max = 100)
    private String title;

    @Size(max = 500)
    private String shortDescription;

    @Size(max = 1000)
    private String post;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
