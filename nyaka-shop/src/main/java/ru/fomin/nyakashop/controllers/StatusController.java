package ru.fomin.nyakashop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fomin.nyakashop.dto.StatusDto;
import ru.fomin.nyakashop.enums.OrderStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/status")
public class StatusController {

    @GetMapping
    List<StatusDto> getStatuses(){
        return Arrays.stream(OrderStatus.values())
                .map(s->StatusDto.builder().statusName(s.name()).DsName(s.getName()).build())
                .collect(Collectors.toList());
    }

}
