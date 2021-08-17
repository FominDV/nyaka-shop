package ru.fomin.nyakashop.exceptions;

public class AccessResourceDeniedException extends RuntimeException {

    private final static String EXCEPTION_MESSAGE = "Access denied to resource %s for current user";

    public AccessResourceDeniedException(String message) {
        super(message);
    }

    public AccessResourceDeniedException(Class<?> clazz) {
        super(String.format(EXCEPTION_MESSAGE, clazz.getSimpleName()));
    }

}
