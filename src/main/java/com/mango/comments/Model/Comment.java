package com.mango.comments.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Comment {

    private int likes;
    private Date date;
    private UUID commentID;
    private String message;
    private String displayName;
    private UUID userID;
    private UUID postID;

    //Constructor
    public Comment() {

    }

    public Comment(UUID userID, UUID postID, String message) {
        date = new Date();
        UUID commentID = UUID.randomUUID();
        this.userID = userID;
        this.postID = postID;
        this.message = message;
        displayName = generateDisplayName();
    }

    public Comment(UUID commentID, UUID userID, UUID postID, String message) {
        date = new Date();
        this.commentID = commentID;
        this.userID = userID;
        this.postID = postID;
        this.message = message;
        displayName = generateDisplayName();
    }

    //Returns the current date as a string
    public String getCurrentDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentDate = formatter.format(date);
        return currentDate;
    }

    //Increases likes by 1
    public void incrementLikes(){ likes++; }

    //Decreases likes by 1
    public void decrementLikes(){ likes--; }

    //Generates a random display name and returns it. For example "Red_Apple"
    public String generateDisplayName (){
        DisplayNameGenerator aDisplayNameGenerator = new DisplayNameGenerator();
        return aDisplayNameGenerator.generateDisplayName();
    }

    public int getLikes()  { return likes; }
    public void setLikes(int likes)  { this.likes = likes; }

    public UUID getCommentID()  { return commentID; }
    public void setCommentID(UUID commentID)  { this.commentID = commentID; }

    public String getMessage()  { return message; }
    public void setMessage(String message)  { this.message = message; }

    public String getDisplayName()  { return displayName; }
    public void setDisplayName(String displayName)  { this.displayName= displayName; }

    public UUID getUserID() {
        return userID;
    }

    public UUID getPostID() {
        return postID;
    }

    public Date getDate() {
        return date;
    }
}
