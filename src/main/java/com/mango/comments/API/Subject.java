package com.mango.comments.API;


import com.mango.comments.Model.CreateCommentCommand;

public interface Subject
{
    public void notifyObserver(CreateCommentCommand newComment);
}
