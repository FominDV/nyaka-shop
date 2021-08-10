package ru.fomin.nyakashop.mappers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataMapper {

    @Value("${formatter.dateTime}")
    String dateTimePattern;

    DateTimeFormatter dateTimeFormatter;

    @PostConstruct
    public void init() {
        dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);
    }

    public String convertToString(LocalDateTime date) {
        return dateTimeFormatter.format(date);
    }

}
