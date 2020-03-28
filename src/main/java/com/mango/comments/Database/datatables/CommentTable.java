package com.mango.comments.Database.datatables;

import com.mango.comments.Database.CommentData;
import com.mango.comments.Database.SQLDatabase;
import com.mango.comments.Model.Comment;
import com.mango.comments.Model.CommentList;

import java.sql.*;
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
                        "Date DATETIME NOT NULL," +
                        "PostID VARCHAR(255) NOT NULL," +
                        "ParentID VARCHAR(255)," +
                        "CommentContent TEXT NOT NULL," +
                        "PRIMARY KEY (IDNum), " +
                        "FOREIGN KEY (ParentID) REFERENCES Comments(IDNum)" +
                        "ON DELETE CASCADE ON UPDATE CASCADE);";
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
            String query = "INSERT INTO Comments (IDNum, DisplayName, Date, PostID, ParentID, CommentContent)" +
                    "VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, c.getCommentID().toString());
            pState.setString(2, c.getDisplayName());
            pState.setTimestamp(3, new java.sql.Timestamp(c.getDate().getTime()));
            pState.setString(4, c.getPostID().toString());
            if(c.getParentID().isPresent())
                pState.setString(5, c.getParentID().get().toString());
            else
                pState.setNull(5, Types.VARCHAR);

            pState.setString(6, c.getMessage());

            pState.execute();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public CommentList selectAllComments()
    {
        CommentList comments = new CommentList();
        try
        {
            String query = "SELECT * FROM Comments";
            PreparedStatement pState = connection.prepareStatement(query);
            resultSet = pState.executeQuery();
            while (resultSet.next())
            {
                UUID commentID = UUID.fromString(resultSet.getString("IDNum"));
                Optional<UUID> parentID = Optional.of(UUID.fromString(resultSet.getString("ParentID")));
                UUID postID = UUID.fromString(resultSet.getString("PostID"));
                String message = resultSet.getString("CommentContent");
                Date date = new Date(resultSet.getTimestamp("Date").getTime());
                String displayName = resultSet.getString("DisplayName");
                comments.add(new Comment(commentID, parentID, postID, message, date, displayName));
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
            if (resultSet.next())
            {
                System.out.println("Inside select by id");
                Optional<UUID> parentID;
                if(resultSet.getString("ParentID") != null)
                    parentID = Optional.ofNullable(UUID.fromString(resultSet.getString("ParentID")));
                else
                    parentID = Optional.ofNullable(null);
                UUID postID = UUID.fromString(resultSet.getString("PostID"));
                String message = resultSet.getString("CommentContent");
                Date date = new Date(resultSet.getDate("Date").getTime());
                String displayName = resultSet.getString("DisplayName");

                comment = new Comment(id, parentID, postID, message, date, displayName);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return Optional.ofNullable(comment);
    }

/*    @Override
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
                Date date = resultSet.getDate("Date");
                String displayName = resultSet.getString("DisplayName");

                comments.add(new Comment(commentID, userID, postID, message, date, displayName));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return (comments);
    }*/

    @Override
    public CommentList selectCommentByParentID(UUID parentID)
    {
        CommentList comments = new CommentList();
        try
        {
            String query = "SELECT * FROM Comments WHERE ParentID = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, parentID.toString());

            resultSet = pState.executeQuery();
            while (resultSet.next())
            {
                UUID commentID = UUID.fromString(resultSet.getString("IDNum"));
                UUID postID = UUID.fromString(resultSet.getString("PostID"));
                String message = resultSet.getString("CommentContent");
                Date date = new Date(resultSet.getDate("Date").getTime());
                String displayName = resultSet.getString("DisplayName");

                comments.add(new Comment(commentID, Optional.ofNullable(parentID), postID, message, date, displayName));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return (comments);
    }

    @Override
    public CommentList selectCommentsByPostID(UUID postID)
    {
        CommentList comments = new CommentList();
        try
        {
            String query = "SELECT * FROM Comments WHERE PostID = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, postID.toString());

            resultSet = pState.executeQuery();
            while (resultSet.next())
            {
                UUID commentID = UUID.fromString(resultSet.getString("IDNum"));
                Optional<UUID> parentID;
                if(resultSet.getString("ParentID") != null)
                    parentID = Optional.of(UUID.fromString(resultSet.getString("ParentID")));
                else
                    parentID = Optional.ofNullable(null);
                String message = resultSet.getString("CommentContent");
                Date date = new Date(resultSet.getDate("Date").getTime());
                String displayName = resultSet.getString("DisplayName");

                comments.add(new Comment(commentID, parentID, postID, message, date, displayName));
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
            String query = "UPDATE Comments SET CommentContent = ?, Likes = ? WHERE IDNum = ?";
            PreparedStatement pState = connection.prepareStatement(query);
            pState.setString(1, newCom.getMessage());
            pState.setInt(2, newCom.getLikes());
            pState.setString(3, id.toString());
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
        UUID id1 = UUID.randomUUID();
        UUID postID = UUID.randomUUID();
        Comment comment1 = new Comment(id1, Optional.ofNullable(null), postID, "THIS IS A PARENT COMMENT");
        System.out.println(commentTable.addComment(comment1));
        System.out.println(commentTable.selectCommentByID(id1).get().getMessage());

        UUID id2 = UUID.randomUUID();
        Comment comment2 = new Comment(id2, Optional.ofNullable(id1), postID, "THIS IS A CHILD COMMENT");
        System.out.println(commentTable.addComment(comment2));
        System.out.println(commentTable.selectCommentByID(id2).get().getMessage());
        System.out.println(commentTable.selectCommentByParentID(id1));

        System.out.println("Printing commentList: " + commentTable.selectCommentsByPostID(postID).toString());


    }
}
