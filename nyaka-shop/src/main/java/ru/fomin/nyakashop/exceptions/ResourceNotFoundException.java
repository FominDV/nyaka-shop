package ru.fomin.nyakashop.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    private final static String EXCEPTION_MESSAGE = "Resource %s was not found";

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Class<?> clazz) {
        super(String.format(EXCEPTION_MESSAGE, clazz.getSimpleName()));
    }
}
