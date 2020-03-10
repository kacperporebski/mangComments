package com.mango.comments.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class Comment {

   private final UUID commentID;
   private UUID postID;
   private UUID userID;
   private String comment;

   public Comment(@JsonProperty("commentID") UUID id,
                 @JsonProperty("postID") UUID postID,
                 @JsonProperty("userID") UUID userID,
                 @JsonProperty("comment") String c){
       commentID=id;
       this.postID=postID;
       this.userID=userID;
       comment=c;
   }

    public String getComment() {
        return comment;
    }

    public UUID getId() {
        return commentID;
    }

    public UUID getPostID() {
        return postID;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setPostID(UUID postID) {
        this.postID = postID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }
}
