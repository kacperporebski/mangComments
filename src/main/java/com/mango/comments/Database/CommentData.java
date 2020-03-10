package com.mango.comments.Database;

import com.mango.comments.Model.Comment;
import com.mango.comments.Model.CommentList;


import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;


public interface CommentData {

    int insertComment(UUID id, Comment c);

    default int insertComment(Comment c){
        UUID id = UUID.randomUUID();
        return insertComment(id, c);

    }

    ArrayList<Comment> selectAllComments();
    Optional<Comment> selectCommentByID(UUID id);
    CommentList selectCommentsByUserID(UUID uid);
    int deleteComment(UUID id);
    int updateComment(UUID id, Comment newCom);


}
