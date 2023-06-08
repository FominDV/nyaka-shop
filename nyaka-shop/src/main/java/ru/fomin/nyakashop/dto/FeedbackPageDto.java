package ru.fomin.nyakashop.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackPageDto {

    List<FeedbackDto> feedbacks;
    Integer totalPages;
}
