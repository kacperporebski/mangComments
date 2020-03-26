package com.mango.comments.API;


import com.mango.comments.EventSourcing.BasicClasses.CreateCommentCommand;

public interface Subject
{
    public void notifyObserver(CreateCommentCommand newComment);
}
