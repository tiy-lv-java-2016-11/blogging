package com.theironyard.commands;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EntryCommand {
    @NotNull
    @Size(min = 5, max = 40)
    private String title;
    @Size(min = 5, max = 140)
    private String snippet;
    @NotNull
    private String post;

    public EntryCommand() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
