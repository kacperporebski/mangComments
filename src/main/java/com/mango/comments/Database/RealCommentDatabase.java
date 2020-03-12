package com.mango.comments.Database;

import com.mango.comments.Database.datatables.CommentTable;
import com.mango.comments.Model.Comment;
import com.mango.comments.Model.CommentList;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository("Real")
public class RealCommentDatabase implements CommentData {

    private CommentTable DB = new CommentTable();

    public RealCommentDatabase(){}

    @Override
    public boolean addComment(Comment c) {
        return DB.addComment(new Comment(c.getCommentID(),c.getPostID(),c.getUserID(), c.getMessage()));
    }

    @Override
    public CommentList selectAllComments() {
        return DB.selectAllComments();
    }

    @Override
    public Optional<Comment> selectCommentByID(UUID id)
    {
      return  DB.selectCommentByID(id);
    }

    @Override
    public CommentList selectCommentsByUserID(UUID uid) {
       return DB.selectCommentsByUserID(uid);
    }


    @Override
    public boolean deleteComment(UUID id) {
        return DB.deleteComment(id);
    }

    @Override
    public boolean updateComment(UUID id, Comment newCom) {
       return DB.updateComment(id, newCom);
    }

}
