package com.mango.comments.Service;

import com.mango.comments.Database.CommentData;
import com.mango.comments.Model.Comment;
import com.mango.comments.Model.CommentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {
//TODO change from fake to actual MySQL implementation
    @Qualifier("Fake")
    private final CommentData commentDatabase;

    @Autowired
    public CommentService(CommentData comDat){
          this.commentDatabase=comDat;
        }

    public int addComment(Comment c){
        return commentDatabase.insertComment(c);
    }

    public ArrayList<Comment> getAllComments(){
        return commentDatabase.selectAllComments();
    }

    public Optional<Comment> selectCommentByID(UUID id){
        return commentDatabase.selectCommentByID(id);
    }

    public CommentList selectCommentsByUserID(UUID id){
        return commentDatabase.selectCommentsByUserID(id);
    }

    public int deleteComment(UUID id){
        return commentDatabase.deleteComment(id);
    }

    public int updateComment(UUID id, Comment newCom){
        return commentDatabase.updateComment(id, newCom);
    }



}
