package ru.fomin.nyakashop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class MarketErrorDto {
    private String message;
    private Date timestamp;

    public MarketErrorDto(String message) {
        this.message = message;
        this.timestamp = new Date();
    }
}
