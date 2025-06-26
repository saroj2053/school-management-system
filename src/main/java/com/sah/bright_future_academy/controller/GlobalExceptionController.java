package com.sah.bright_future_academy.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionController {

    /*
    * @ExceptionHandler will register the given method for a exception type,
    * so that ControllerAdvice can invoke this method logic if a given exception
    * type is thrown inside the web application
    * */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("errorMsg", exception.getMessage());
        return modelAndView;
    }
}
