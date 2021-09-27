package ru.fomin.nyakashop.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.fomin.nyakashop.dto.StringDto;
import ru.fomin.nyakashop.services.ProductService;
import ru.fomin.nyakashop.services.ResourceService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/resources")
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResourceController {

    final ResourceService resourceService;
    final ProductService productService;

    @PostMapping
    @ResponseBody
    public StringDto uploadProductImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("id") Long productId
    ) {
        UUID imageId = resourceService.uploadResource(file);
        productService.setImage(productId, imageId);
        return new StringDto(resourceService.getResourceUrl(imageId));
    }

}
