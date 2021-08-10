package ru.fomin.nyakashop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class MarketError {
    private String message;
    private Date timestamp;

    public MarketError(String message) {
        this.message = message;
        this.timestamp = new Date();
    }
}
