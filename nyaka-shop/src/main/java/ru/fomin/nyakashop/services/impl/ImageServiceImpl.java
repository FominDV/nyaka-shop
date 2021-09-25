package ru.fomin.nyakashop.services.impl;

import io.minio.MinioClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageServiceImpl {

  //  final MinioClient minioClient;

    public String getImageUrl(String name){
        //minioClient.getPresignedObjectUrl();
        return "dd";
    }

}
