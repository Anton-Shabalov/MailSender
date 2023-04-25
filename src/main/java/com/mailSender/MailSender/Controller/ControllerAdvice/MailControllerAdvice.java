//package com.mailSender.MailSender.Controller.ControllerAdvice;
//
//
//import com.mailSender.MailSender.DTO.Response;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//
//public class MailControllerAdvice {
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Response> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        StringBuilder responseString = new StringBuilder();
//        e.getBindingResult().getFieldErrors().stream().map(error -> error.getDefaultMessage() + ", ").forEach(responseString::append);
//        Response response = new Response(responseString.toString());
//        return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
//    }
//}
