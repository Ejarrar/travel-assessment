package com.afkl.travel.exercise.exception;

public class LocationNotFoundException extends BusinessException {

    private static final String MESSAGE = "Location does not exist";

    public LocationNotFoundException() {
        this.message = MESSAGE;
    }
}
