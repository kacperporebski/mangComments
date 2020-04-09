package com.mango.comments.EventSourcing.BasicClasses;

import java.util.ArrayList;

public class CommentList {

    public ArrayList<CommentCreatedEvent> commentCreatedEvents;

    public CommentList(){
        commentCreatedEvents = new ArrayList<>();
    }

    public void add(CommentCreatedEvent c){
        commentCreatedEvents.add(c);
    }

    @Override
    public String toString() {
       String s = "";
        for (CommentCreatedEvent c : commentCreatedEvents
             ) {

            s+= c + "\n";
        }
        return s;
    }
}
