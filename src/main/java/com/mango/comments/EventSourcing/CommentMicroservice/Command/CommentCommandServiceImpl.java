package com.mango.comments.EventSourcing.CommentMicroservice.Command;


import com.mango.comments.API.Subject;
import com.mango.comments.API.SubscriberEventStore;
//import com.mango.comments.EventSourcing.BasicClasses.Comment;
import com.mango.comments.Model.Comment;
import com.mango.comments.EventSourcing.BasicClasses.CreateCommentCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

//This class is used for storing comment events in the event store
@Service
public class CommentCommandServiceImpl implements CommentCommandService, Subject {

    private final CommandGateway commandGateway;
    private SubscriberEventStore observer;


    //This constructor is called immediately when the application is run
    public CommentCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
        observer= new SubscriberEventStore();
        System.out.println("Inside Constructor CommentCommandServiceImpl(CommandGateway commandGateway");
    }

    //Store comment event into the event store. Requires the comment data members to have already been initialized.
    @Override
    public CompletableFuture<String> createComment(Comment comment) {
        System.out.println("Inside createComment(Comment comment)");
        CreateCommentCommand createCommentCommand;
        if(comment.getParentID().isPresent())
             createCommentCommand = new CreateCommentCommand(comment.getPostID(), comment.getCommentID(), comment.getParentID().get(), comment.getMessage());
        else
            createCommentCommand = new CreateCommentCommand(comment.getPostID(), comment.getCommentID(), null, comment.getMessage());

        notifyObserver(createCommentCommand);
        return commandGateway.send(createCommentCommand);
    }

    @Override
    public void notifyObserver(CreateCommentCommand newComment)
    {
        observer.updateCreationEvent(newComment);
    }

}

