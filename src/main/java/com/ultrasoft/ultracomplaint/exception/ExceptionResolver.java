package com.ultrasoft.ultracomplaint.exception;

import com.ultrasoft.ultracomplaint.responsebody.APIResponseBody;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionResolver {

    @CrossOrigin
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(HttpServletRequest request, Exception e) {
        return new ResponseEntity<Object>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
    }

    @CrossOrigin
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<Object> handleHttpMediaTypeNotAcceptableException(HttpServletRequest request, HttpMediaTypeNotAcceptableException e) {
        return new ResponseEntity<Object>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
    }

    @CrossOrigin
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNotFoundResourceException(HttpServletRequest request, NoHandlerFoundException e) {
        return new ResponseEntity<Object>(new APIResponseBody("Resource not found"), HttpStatus.OK);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex,HttpServletRequest request){
        return new ResponseEntity<Object>(new APIResponseBody("Method not allowed"), HttpStatus.OK);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Object> handleMissingRequestHeaderException(MissingRequestHeaderException ex,HttpServletRequest request){
        return new ResponseEntity<Object>(new APIResponseBody(ex.getHeaderName()+" is required"), HttpStatus.OK);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Object> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex,HttpServletRequest request){
        return new ResponseEntity<Object>(new APIResponseBody("Unsupported media type"), HttpStatus.OK);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,HttpServletRequest request){
        return new ResponseEntity<Object>(new APIResponseBody("Bad Request"), HttpStatus.OK);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleHttpMissingServletRequestParameterException(MissingServletRequestParameterException ex,HttpServletRequest request){
        return new ResponseEntity<Object>(new APIResponseBody(ex.getParameterName()+ "is required"), HttpStatus.OK);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<Object> handleHttpMissingParametersException(MissingPathVariableException ex,HttpServletRequest request){
        return new ResponseEntity<Object>(new APIResponseBody(ex.getVariableName()+ "is required"), HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleHttpMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,HttpServletRequest request){
        return new ResponseEntity<Object>(new APIResponseBody(ex.getName()+ "is required"), HttpStatus.OK);
    }


    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<Object> handlePropertyValueException(PropertyValueException ex,HttpServletRequest request){
        return new ResponseEntity<Object>(new APIResponseBody(ex.getPropertyName()+ "is required"), HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException (MethodArgumentNotValidException ex,HttpServletRequest request){
        Map<String, List<String>> body = new HashMap<>();
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        body.put("errors", errors);
        return new ResponseEntity<Object>(new APIResponseBody(errors.get(0)), HttpStatus.OK);
    }
}
