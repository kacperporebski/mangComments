package com.mango.comments.Database.datatables;

import com.mango.comments.Database.CommentData;
import com.mango.comments.Database.SQLDatabase;
import com.mango.comments.Model.Comment;
import com.mango.comments.Model.CommentList;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class CommentTable extends SQLDatabase implements CommentData
{

    public CommentTable()
    {
        super();
        createCommentTable();
    }

    /**
     * Creates a comment table in the database if it does not exist upon startup
     */
    private void createCommentTable()
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "Comments", null);

            if (rs.next() == false)
            {
                String query = "CREATE TABLE Comments " +
                        "(IDNum VARCHAR(255) NOT NULL," +
                        "DisplayName VARCHAR(255) NOT NULL," +
                        "Likes INT DEFAULT 0," +
                        "Date DATE NOT NULL," +
                        "PostID VARCHAR(255) NOT NULL," +
                        "UserID VARCHAR(255) NOT NULL," +
                        "CommentContent TEXT NOT NULL," +
                        "PRIMARY KEY (IDNum) )";
                statement.executeUpdate(query);
                System.out.println("Created comment table\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addComment(Comment c)
    {
        try
        {
            String query = "INSERT INTO Comments (IDNum, DisplayName, Date, PostID, UserID, CommentContent)" +
                    "VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, c.getCommentID().toString());
            pState.setString(2, c.getDisplayName());
            pState.setDate(3, new java.sql.Date(c.getDate().getTime()));
            pState.setString(4, c.getPostID().toString());
            pState.setString(5, c.getUserID().toString());
            pState.setString(5, c.getMessage());

            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Comment> selectAllComments()
    {
        ArrayList<Comment> comments = new ArrayList<>();
        try
        {
            String query = "SELECT * FROM Comments";
            PreparedStatement pState = connection.prepareStatement(query);
            resultSet = pState.executeQuery();
            while (resultSet.next())
            {
                UUID commentID = UUID.fromString(resultSet.getString("IDNum"));
                UUID userID = UUID.fromString(resultSet.getString("UserID"));
                UUID postID = UUID.fromString(resultSet.getString("PostID"));
                String message = resultSet.getString("CommentContent");

                comments.add(new Comment(commentID, userID, postID, message));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return comments;
    }

    @Override
    public Optional<Comment> selectCommentByID(UUID id) {
        Comment comment = null;
        try
        {
            String query = "SELECT * FROM Comments WHERE IDNum = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, id.toString());

            resultSet = pState.executeQuery();
            while (resultSet.next())
            {
                UUID commentID = UUID.fromString(resultSet.getString("IDNum"));
                UUID userID = UUID.fromString(resultSet.getString("UserID"));
                UUID postID = UUID.fromString(resultSet.getString("PostID"));
                String message = resultSet.getString("CommentContent");

                new Comment(commentID, userID, postID, message);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return Optional.ofNullable(comment);
    }

    @Override
    public CommentList selectCommentsByUserID(UUID uid)
    {
        CommentList comments = new CommentList();
        try
        {
            String query = "SELECT * FROM Comments WHERE UserID = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, uid.toString());

            resultSet = pState.executeQuery();
            while (resultSet.next())
            {
                UUID commentID = UUID.fromString(resultSet.getString("IDNum"));
                UUID userID = UUID.fromString(resultSet.getString("UserID"));
                UUID postID = UUID.fromString(resultSet.getString("PostID"));
                String message = resultSet.getString("CommentContent");

                comments.add(new Comment(commentID, userID, postID, message));

            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return (comments);
    }

    @Override
    public boolean deleteComment(UUID id)
    {
        try
        {
            String query = "DELETE FROM Comments WHERE IDNum = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, id.toString());
            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }    }

    @Override
    public boolean updateComment(UUID id, Comment newCom)
    {
        try
        {
            String query = "UPDATE Comments SET CommentContent = ? WHERE IDNum = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, newCom.getMessage());
            pState.setString(2, id.toString());
            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }    }

    public static void main(String args[])
    {
        CommentTable commentTable = new CommentTable();
        System.out.println(commentTable.addComment(new Comment( UUID.randomUUID(), UUID.randomUUID(), "Ello")));


    }
}
