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
    private final CommentData commentDatabase;

    @Autowired
    public CommentService(@Qualifier("Real") CommentData comDat){
          this.commentDatabase=comDat;
        }

    public boolean addComment(Comment c){
        return commentDatabase.addComment(c);
    }

    public CommentList getAllComments(){
        return commentDatabase.selectAllComments();
    }

    public Optional<Comment> selectCommentByID(UUID id){
        return commentDatabase.selectCommentByID(id);
    }

/*    public CommentList selectCommentsByUserID(UUID id){
        return commentDatabase.selectCommentsByUserID(id);
    }*/

    public boolean deleteComment(UUID id){
        return commentDatabase.deleteComment(id);
    }

    public boolean updateComment(UUID id, Comment newCom){
        return commentDatabase.updateComment(id, newCom);
    }

    public CommentList selectCommentByParentID(UUID parent){ return commentDatabase.selectCommentByParentID(parent);}



}
