package com.mango.comments.API;


import com.mango.comments.EventSourcing.BasicClasses.CreateCommentCommand;

public interface Observer
{
    public void updateCreationEvent(CreateCommentCommand event);
}
