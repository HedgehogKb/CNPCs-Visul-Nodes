package com.hedgehogkb.Exceptions;

public class ProjectExistsException extends Exception {
    public ProjectExistsException(String errorMessage) {
        super(errorMessage);
    }
}
