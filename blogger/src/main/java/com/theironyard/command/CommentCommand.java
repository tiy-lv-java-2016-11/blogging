package com.theironyard.command;

import javax.validation.constraints.Size;

/**
 * Created by sparatan117 on 1/12/17.
 */
public class CommentCommand {
    @Size(max = 500)
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
