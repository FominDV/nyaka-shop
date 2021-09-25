package ru.fomin.nyakashop.services.impl;

import io.minio.*;
import io.minio.http.Method;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.fomin.nyakashop.services.ResourceService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResourceServiceImpl implements ResourceService {

    final MinioClient minioClient;

    @Value("${minio.bucket}")
    String bucketName;

    @Value("${minio.partSize}")
    Long partSize;

    @SneakyThrows
    @Override
    public String getResourceUrl(String bucketName, UUID resourceId) {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName)
                .object(resourceId.toString())
                .build();
        return minioClient.getPresignedObjectUrl(args);
    }

    @Override
    public String getResourceUrl(UUID resourceId) {
        return getResourceUrl(bucketName, resourceId);
    }

    @Override
    @SneakyThrows
    public UUID uploadResource(String bucketName, MultipartFile file) {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
        UUID fileId = UUID.randomUUID();
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(fileId.toString())
                .stream(file.getInputStream(), file.getSize(), partSize)
                .contentType(file.getContentType())
                .build();
        minioClient.putObject(args);
        return fileId;
    }

    @Override
    public UUID uploadResource(MultipartFile file) {
        return uploadResource(bucketName, file);
    }

}
