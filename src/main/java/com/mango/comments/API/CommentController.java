package com.mango.comments.API;


import com.mango.comments.Model.Comment;
import com.mango.comments.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RequestMapping("/comments")
@RestController
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService comServ){
        commentService=comServ;
    }

    @PostMapping
    public void addComment(@RequestBody Comment c){
        commentService.addComment(c);
    }

    @GetMapping
    public ArrayList<Comment> getAllComments(){
        return commentService.getAllComments();
    }

    @GetMapping(path = "{id}")
    public Comment selectCommentByID(@PathVariable("id")UUID id ){
        return commentService.selectCommentByID(id)
                .orElse(null);
        //return null if not found
    }

    @DeleteMapping(path = "{id}")
    public void deleteComment(@PathVariable("id") UUID id){
            commentService.deleteComment(id);
    }

    @PutMapping(path = "{id}")
    public void updateComment(@PathVariable("id") UUID id, @RequestBody Comment commentToUpdate){
            commentService.updateComment(id,commentToUpdate);
    }

}
