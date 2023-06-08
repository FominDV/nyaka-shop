package ru.fomin.nyakashop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.fomin.nyakashop.dto.CategoryDto;
import ru.fomin.nyakashop.dto.ShipmentDto;
import ru.fomin.nyakashop.dto.ShipmentPage;
import ru.fomin.nyakashop.entities.Shipment;
import ru.fomin.nyakashop.repositories.ProductRepository;
import ru.fomin.nyakashop.repositories.ShipmentsRepository;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shipments")
public class ShipmentController {
    @Value("${pageSize.shipment}")
    int pageSize;
    final ShipmentsRepository shipmentsRepository;
    final ProductRepository productRepository;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm");

    @GetMapping
    public ShipmentPage getShipments(@RequestParam(name = "productId") Long productId, @RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex) {
        var shipments = shipmentsRepository.findAllByProduct_Id(productId).stream()
                .sorted(Comparator.comparing(Shipment::getCreatedAt))
                .map(sh->ShipmentDto.builder()
                        .createAt(dtf.format(sh.getCreatedAt()))
                        .quantity(sh.getQuantity())
                        .build())
                .collect(Collectors.toList());
        Collections.reverse(shipments);
        pageIndex--;
        Integer pagesLength = shipments.size()/pageSize + (shipments.size()%pageSize==0?0:1);
        int startIndex = pageSize*pageIndex;
        int endIndex = pageIndex==pagesLength-1?startIndex -1+  shipments.size() - (pagesLength-1)*pageSize:startIndex + pageSize -1;
        shipments= shipments.isEmpty()?shipments: shipments.subList(startIndex,endIndex+1);
        return ShipmentPage.builder()
                .totalPages(pagesLength)
                .content(shipments)
                .build();
    }

    @PostMapping
    @Transactional
    void creatShipment(@RequestBody ShipmentDto shipment){
        Shipment newShipment = Shipment.builder()
                .quantity(shipment.getQuantity())
                .product(productRepository.getById(shipment.getProductId()))
                .build();
        shipmentsRepository.save(newShipment);
    }

}
