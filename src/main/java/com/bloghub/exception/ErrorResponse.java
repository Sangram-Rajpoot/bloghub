package com.bloghub.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter//
@Setter//generating getter and setter methods
@AllArgsConstructor//generating constructor with all arguments

public class ErrorResponse {//class to represent error response

    private int statusCode;//http status code
    private String errorMessage;//error message


}
