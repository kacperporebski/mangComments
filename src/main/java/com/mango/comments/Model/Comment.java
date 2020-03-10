package com.mango.comments.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class Comment {

   private final UUID id;
   private UUID postID;
   private UUID userID;
   private String comment;

   public Comment(@JsonProperty("id") UUID id,
                 @JsonProperty("pid") UUID postID,
                 @JsonProperty("uid") UUID userID,
                 @JsonProperty("data") String c){
       this.id=id;
       this.postID=postID;
       this.userID=userID;
       comment=c;
   }

    public String getComment() {
        return comment;
    }

    public UUID getId() {
        return id;
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
