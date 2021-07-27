package com.training.blinkist.exceptions;

   public class BookDoesNotExistInUserLibraryException extends Exception{
        public BookDoesNotExistInUserLibraryException(String message){
            super(message);
        }
    }

