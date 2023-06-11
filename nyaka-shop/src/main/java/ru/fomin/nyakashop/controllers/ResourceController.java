package ru.fomin.nyakashop.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.fomin.nyakashop.dto.StringDto;
import ru.fomin.nyakashop.repositories.ProductRepository;
import ru.fomin.nyakashop.services.ProductService;
import ru.fomin.nyakashop.services.ResourceService;

import java.io.*;
import java.nio.file.Files;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/resources")
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResourceController {

    final ResourceService resourceService;
    final ProductService productService;
    final ProductRepository productRepository;

    @PostMapping
    @ResponseBody
    public StringDto uploadProductImage(
            @RequestParam("file") MultipartFile Multfile,
            @RequestParam("id") Long productId
    ) throws FileNotFoundException {
//        var id = UUID.randomUUID().toString();
//        var type=Multfile.getContentType().substring(6);
//        if(type.equals("jpeg")){
//            type="jpg";
//        }
//        var name = "img/"+productId+"."+type;
//        File file = new File("src/main/resources/static/img/"+productId+"."+type);
//
//        try (OutputStream os = new FileOutputStream(file)) {
//            os.write(Multfile.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        productService.setImage(productId,name);
//        return new StringDto(name);
        var id = UUID.randomUUID().toString();
        var type=Multfile.getContentType().substring(6);
        if(type.equals("jpeg")){
            type="jpg";
        }
        var name = "img/"+productId+"."+type;
        File file = new File("src/img/"+productId+"."+type);

        try (OutputStream os = new FileOutputStream(file)) {
            os.write(Multfile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        productService.setImage(productId,"api/v1/resources/image/"+productId+"."+type);
        return new StringDto(name);
    }

    @GetMapping
    public String getImageUrl(@RequestParam(name = "id") Long productId){
        return productRepository.getById(productId).getImage();
    }

    @GetMapping("/image/{url}")
    @ResponseBody
    public byte[] getImage(@PathVariable(name = "url") String url) throws IOException {
//        InputStream in = getClass()
//                .getResourceAsStream("src/img/"+url);
//        var butes= IOUtils.toByteArray(in);
//        in.close();
//        return butes;
        File fi = new File("src/img/"+url);
        return  Files.readAllBytes(fi.toPath());
    }

}
