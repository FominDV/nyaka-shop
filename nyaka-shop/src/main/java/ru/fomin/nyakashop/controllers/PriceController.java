package ru.fomin.nyakashop.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import ru.fomin.nyakashop.dto.PriceDto;
import ru.fomin.nyakashop.dto.PricePageDto;
import ru.fomin.nyakashop.dto.ProductDto;
import ru.fomin.nyakashop.entities.Price;
import ru.fomin.nyakashop.repositories.PriceRepository;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/prices")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PriceController {

    final PriceRepository priceRepository;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm");
    @Value("${pageSize.price}")
    int pageSize;

    @GetMapping()
    public PricePageDto getPrices(@RequestParam(name = "productId", required = false) Long productId, @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex) {
        if(productId==null){
            return PricePageDto.builder()
                    .totalPages(0)
                    .content(Collections.emptyList())
                    .build();
        }
        List<PriceDto> priceDtos = priceRepository.findAllByProduct_Id(productId).stream()
                .sorted(Comparator.comparing(Price::getCreatedAt))
                .map(p->PriceDto.builder()
                        .cost(p.getCost())
                        .updateAt(dtf.format(p.getCreatedAt()))
                        .build())
                .collect(Collectors.toList());
               Collections.reverse(priceDtos);
        Integer pagesLength = priceDtos.size()/pageSize + (priceDtos.size()%pageSize==0?0:1);
        pageIndex--;
        int startIndex = pageSize*pageIndex;
        int endIndex = pageIndex==pagesLength-1?startIndex -1+  priceDtos.size() - (pagesLength-1)*pageSize:startIndex + pageSize -1;
        priceDtos= priceDtos.isEmpty()?priceDtos: priceDtos.subList(startIndex,endIndex+1);
        return PricePageDto.builder()
                .totalPages(pagesLength)
                .content(priceDtos)
                .build();
    }

}
