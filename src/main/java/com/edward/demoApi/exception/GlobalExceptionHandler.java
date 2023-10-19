package com.edward.demoApi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Date;

@ControllerAdvice(annotations = RestController.class)
@EnableWebMvc
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorObject> handlePersonNotFound(PersonNotFoundException ex){
        logger.info(ex.toString());
        ErrorObject errorObject = new ErrorObject();
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());
        errorObject.setStatusCode(404);
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorObject> handleDataAccessException(DataAccessException ex){
        logger.info(ex.toString());
        ErrorObject errorObject = new ErrorObject();
        errorObject.setMessage("Internal Error, please contact an admin with the details below");
        errorObject.setTimestamp(new Date());
        errorObject.setStatusCode(500);
        return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<String> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unsupported media type: " + ex.getContentType());
    }


}
