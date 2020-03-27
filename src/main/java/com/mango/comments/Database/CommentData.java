package com.mango.comments.Database;

import com.mango.comments.Model.Comment;
import com.mango.comments.Model.CommentList;


import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;


public interface CommentData {

    boolean addComment(Comment c);
    CommentList selectAllComments();
    Optional<Comment> selectCommentByID(UUID id);
    CommentList selectCommentsByUserID(UUID uid);
    boolean deleteComment(UUID id);
    boolean updateComment(UUID id, Comment newCom);
    CommentList selectCommentByParentID(UUID parent);
    CommentList selectCommentsByPostID(UUID postId);



}
