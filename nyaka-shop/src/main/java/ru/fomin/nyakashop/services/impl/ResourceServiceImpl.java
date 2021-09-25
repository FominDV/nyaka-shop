package ru.fomin.nyakashop.services.impl;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.fomin.nyakashop.services.ResourceService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResourceServiceImpl implements ResourceService {

    final MinioClient minioClient;

    @Value("${minio.bucket}")
    String bucketName;

    @SneakyThrows
    @Override
    public String getResourceUrl(String bucketName, String sourceName) {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName)
                .object(sourceName)
                .build();
        return minioClient.getPresignedObjectUrl(args);
    }

    @Override
    public String getResourceUrl(String sourceName) {
        return getResourceUrl(bucketName, sourceName);
    }

}
