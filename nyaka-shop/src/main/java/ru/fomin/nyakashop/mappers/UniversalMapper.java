package ru.fomin.nyakashop.mappers;

import java.util.List;

public interface UniversalMapper {

    <T, R> R convert(T source, Class<R> targetClass);

    <T, R> List<R> convertList(List<T> sourceList, Class<R> targetClass);

}
