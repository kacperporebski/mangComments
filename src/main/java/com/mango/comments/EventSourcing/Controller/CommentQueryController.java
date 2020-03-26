package com.mango.comments.EventSourcing.Controller;

import com.mango.comments.EventSourcing.CommentMicroservice.Query.CommentQueryService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

//Used for calling functions responsible for retrieving comments in the event store
@RestController
@RequestMapping(value = "/comments")/*See note below*/
@Api(value = "Query Api value goes here", description = "Query Api description goes here", tags = "Query Api tags goes here")
public class CommentQueryController {
    private final CommentQueryService commentQueryService;

    //This constructor is called immediately when the application is run
    public CommentQueryController(CommentQueryService commentQueryService) {
        this.commentQueryService = commentQueryService;
        System.out.println("Inside Constructor CommentQueryController(CommentQueryService commentQueryService)");
    }

    // Returns the comment event in Byte form.
    @GetMapping("/{commentID}/events")/*See note below*/
     public List<Object> getCommentBytesFromEventStore(@PathVariable(value = "commentID") UUID commentID){
         System.out.println("Inside listEventsForComment(@PathVariable(value = \"commentID\") String commentID)");
         return  commentQueryService.getCommentBytesFromEventStore(commentID);
    }

    /*Note: @RequestMapping(value = "/comments") specifies the first path. @GetMapping("/{commentID}/events") specifies the second path.
    For example: http://localhost:8080/comments/e148667a-c258-43f5-b504-27ba086ff6c3/events
    Notice that after 3rd '/' is 'comments'. This is the first path
    Notice that after 4rd '/' is [commentID]/events. This is the second path
     */
}
