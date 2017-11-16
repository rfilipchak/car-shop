package com.playtika.carshop.web;

import com.playtika.carshop.Exeprions.CarNotFoundException;
import lombok.Data;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice

public class CarShopExceptioHandler {

    @ExceptionHandler(CarNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFound(Exception e) {
        return new ErrorResponse(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase(), e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleBadRequest(Exception e) {
        return new ErrorResponse(BAD_REQUEST.value(), BAD_REQUEST.getReasonPhrase(), e.getMessage());
    }

    @Data
    public static class ErrorResponse {
        final int code;
        final String message;
        final String description;
    }
}
