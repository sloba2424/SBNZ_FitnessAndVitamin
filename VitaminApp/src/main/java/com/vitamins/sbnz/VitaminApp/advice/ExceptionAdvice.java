package com.sbnz.ibar.advice;

import com.sbnz.ibar.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(new ErrorDto(message, ""));
    }

    @ExceptionHandler(EntityDoesNotExistException.class)
    public ResponseEntity<ErrorDto> handleEntityDoesNotExistException(EntityDoesNotExistException ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorDto(ex.getMessage(), ""));
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorDto(ex.getMessage(), ""));
    }

    @ExceptionHandler(UserNotLoggedInException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> onUserNotLoggedInException(UserNotLoggedInException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto(exception.getMessage(), ""));
    }

    @ExceptionHandler(IpTemporarilyBlockedException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> onIpTemporarilyBlockedException(IpTemporarilyBlockedException exception) {
        return ResponseEntity.badRequest().body(new ErrorDto("Your IP address was temporarily blocked",
                exception.getMessage()));
    }

    @ExceptionHandler(EmailTemporarilyBlockedException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> onEmailTemporarilyBlockedException(EmailTemporarilyBlockedException exception) {
        return ResponseEntity.badRequest().body(new ErrorDto("Your account was temporarily blocked",
                exception.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> onAuthenticationException(AuthenticationException exception) {
        return ResponseEntity.badRequest().body(new ErrorDto("Bad credentials", exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Void> onException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
