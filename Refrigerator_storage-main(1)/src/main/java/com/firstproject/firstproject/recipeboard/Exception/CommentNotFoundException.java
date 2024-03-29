package com.firstproject.firstproject.recipeboard.Exception;

public class CommentNotFoundException  extends RuntimeException{
    public CommentNotFoundException(String message){
        super(message);
    }
}
