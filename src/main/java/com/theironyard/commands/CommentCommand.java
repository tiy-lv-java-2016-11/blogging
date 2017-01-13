package com.theironyard.commands;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentCommand {
    @NotNull
    @Size(max = 140)
    private String content;

    public CommentCommand() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
