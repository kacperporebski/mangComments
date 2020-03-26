package com.mango.comments.EventSourcing.CommentMicroservice.Command;


import com.mango.comments.EventSourcing.BasicClasses.Comment;

import java.util.concurrent.CompletableFuture;

public interface CommentCommandService {
      CompletableFuture<String> createComment(Comment comment);
}
