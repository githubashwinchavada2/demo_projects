package com.rating.service.exceptions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    private String message = "Error processing the request! Please contact your administrator!";

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        if (ex.getMessage() != null) {
            message = ex.getMessage();
        } else if (ex.getCause() != null && ex.getCause().getCause() != null) {
            message = ex.getCause().getCause().getMessage();
        }

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        ExceptionResponse errorDetails = new ExceptionResponse();
        errorDetails.setTimestamp(new Date());
        errorDetails.setError(httpStatus.toString());
        errorDetails.setMessage(message);

        if (message.contains("\"")) {
            httpStatus = HttpStatus.valueOf(message.substring(4, message.indexOf("\"") - 1));
            errorDetails.setError(message.substring(0, message.indexOf("\"") - 1));
            errorDetails.setMessage(message.substring(message.indexOf("\"") + 1, message.lastIndexOf("\"")));
        }
        return new ResponseEntity(errorDetails, httpStatus);
    }

//    @ExceptionHandler(ResponseStatusException.class)
//    public final ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
//        if (ex.getReason() != null) {
//            message = ex.getReason();
//        }
//        ExceptionResponse errorDetails = new ExceptionResponse(new Date(), String.valueOf(ex.getStatusCode()), message);
//
//        return new ResponseEntity(errorDetails, ex.getStatusCode());
//    }
}
