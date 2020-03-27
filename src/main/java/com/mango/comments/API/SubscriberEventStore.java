package com.mango.comments.API;


import com.mango.comments.Database.RealCommentDatabase;
import com.mango.comments.EventSourcing.BasicClasses.CreateCommentCommand;
import com.mango.comments.Model.Comment;
import com.mango.comments.Service.CommentService;

import java.util.Optional;
import java.util.UUID;

public class SubscriberEventStore implements Observer
{
    private final CommentService commentDatabase;

    public SubscriberEventStore()
    {
        commentDatabase = new CommentService(new RealCommentDatabase());
    }

    @Override
    public void updateCreationEvent(CreateCommentCommand event)
    {
        System.out.println("WE ARE IN OBSERVER");
        System.out.println(event.message);
        Comment comment = new Comment(event.commentID, Optional.ofNullable(event.parentCommentID), event.postID, event.message);
        if(comment.getParentID().isPresent())
        {
            System.out.println("THIS IS WRONG");
        }
        if(comment.getParentID().get() == null)
        {
            System.out.println("WHOOOOO IT WORKED");
        }
        commentDatabase.addComment(comment);
    }
}