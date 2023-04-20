package com.neoris.turnosrotativos.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", new Date());

        List<String> messageList = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        responseBody.put("messages", messageList);

        return new ResponseEntity<>(responseBody, headers, status);
    }
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	Map<String, Object> responseBody = new HashMap<>();
    	List<String> messageList=new ArrayList<>();

        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException) {
            InvalidFormatException e = (InvalidFormatException) cause;
            String field = e.getPath().get(0).getFieldName();
            String value = e.getValue().toString();
            String message = String.format("El valor '%s' para el campo '%s' no tiene el formato esperado.", value, field);
            messageList.add(message);
            responseBody.put("messages", messageList);
            return ResponseEntity.badRequest().body(responseBody);
        }
        messageList.add(ex.getMessage());
        responseBody.put("messages", messageList);
        return ResponseEntity.badRequest().body(responseBody);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
    	
    	List<String> messageList=new ArrayList<>();
    	Map<String, Object> responseBody = new HashMap<>();
    	String message = String.format("El parametro '%s' no tiene un formato válido.", ex.getName());
    	messageList.add(message);
        responseBody.put("messages", messageList);
        return ResponseEntity.badRequest().body(responseBody);
    }
  
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(
           NotFoundException ex, WebRequest request
    ) {
    	List<String> messageList=new ArrayList<>();
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", new Date());
        messageList.add(ex.getMessage());
        responseBody.put("messages",messageList);

        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflictException(
           ConflictException ex, WebRequest request
    ) {
    	List<String> messageList=new ArrayList<>();
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", new Date());
        messageList.add(ex.getMessage());
        responseBody.put("messages",messageList);

        return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(
    		BadRequestException ex, WebRequest request
    ) {
    	List<String> messageList=new ArrayList<>();
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", new Date());
        messageList.add(ex.getMessage());
        responseBody.put("messages",messageList);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

}
