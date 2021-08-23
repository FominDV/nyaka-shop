package ru.fomin.nyakashop.mappers.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Component;
import ru.fomin.nyakashop.mappers.UniversalMapper;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UniversalMapperImpl implements UniversalMapper {

    final ConversionService conversionService;

    @Override
    public <T, R> R convert(T source, Class<R> targetClass) {
        return conversionService.convert(source, targetClass);
    }

    @Override
    public <T, R> List<R> convertList(List<T> sourceList, Class<R> targetClass) {
        return (List<R>) conversionService.convert(sourceList,
                TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(sourceList.getClass())),
                TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(targetClass)));
    }

}
