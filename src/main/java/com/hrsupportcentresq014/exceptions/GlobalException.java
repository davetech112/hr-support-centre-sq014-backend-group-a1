package com.hrsupportcentresq014.exceptions;

import com.hrsupportcentresq014.dtos.request.ErrorDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.SendFailedException;
import jakarta.mail.internet.AddressException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(MessagingException.class) //  This is the superclass for all exceptions related to sending or receiving email messages.
    public ResponseEntity<ErrorDetails> handleMessagingException(MessagingException exception,
                                                                 WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SendFailedException.class) // This exception is thrown when the sending of an email message fails.
    public ResponseEntity<ErrorDetails> handleSendFailedException(SendFailedException exception,
                                                                  WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AddressException.class) // This exception is thrown when there is an error in the format of an email address.
    public ResponseEntity<ErrorDetails> handleSendFailedException(AddressException exception,
                                                                  WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class) // handling error handling
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                              WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
