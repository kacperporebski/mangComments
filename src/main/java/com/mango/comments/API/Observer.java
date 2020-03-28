package com.mango.comments.API;


import com.mango.comments.Model.CreateCommentCommand;

public interface Observer
{
    public void updateCreationEvent(CreateCommentCommand event);
}
