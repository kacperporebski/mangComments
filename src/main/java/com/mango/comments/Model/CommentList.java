package com.mango.comments.Model;

import java.util.ArrayList;
import java.util.Collections;

public class CommentList {

    public ArrayList<Comment> comments;

    public CommentList(){
        comments = new ArrayList<>();
    }

    public void add(Comment c){
        comments.add(c);
        Collections.sort(comments, Collections.reverseOrder());
    }

}
