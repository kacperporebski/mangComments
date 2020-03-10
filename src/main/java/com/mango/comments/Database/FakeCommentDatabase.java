package com.mango.comments.Database;

import com.mango.comments.Model.Comment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository("Fake")
public class FakeCommentDatabase implements CommentData {

    private static ArrayList<Comment> DB = new ArrayList<>();

    public FakeCommentDatabase(){
        DB.add( new Comment(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),"test comment"));
        DB.add( new Comment(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),"I love microservices"));
        DB.add(new Comment(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),"How do i do this"));
    }

    @Override
    public int insertComment(UUID id, Comment c) {
        DB.add(new Comment(id,c.getPostID(),c.getUserID(), c.getComment()));
        return 1;
    }

    @Override
    public ArrayList<Comment> selectAllComments() {
        return DB;
    }

    @Override
    public Optional<Comment> selectCommentByID(UUID id) {
      return  DB.stream().filter(Comment->Comment.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deleteComment(UUID id) {
        Optional<Comment> commentMaybe = selectCommentByID(id);
        if(commentMaybe.isPresent()){
            return 0;
        }
        DB.remove(commentMaybe.get());
        return 1;
    }

    @Override
    public int updateComment(UUID id, Comment newCom) {
        return selectCommentByID(id).map(com -> {
            int indexOfUpdate = DB.indexOf(com);
            if(indexOfUpdate>=0) {
                DB.set(indexOfUpdate, new Comment(id, newCom.getUserID(), newCom.getPostID(), newCom.getComment()));
                return 1;
            }
            return 0;
        }
        ).orElse(0);
    }

}
