package com.afkl.travel.exercise.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException{

    protected String message;
}
