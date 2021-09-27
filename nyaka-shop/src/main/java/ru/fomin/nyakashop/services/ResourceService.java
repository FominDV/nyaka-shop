package ru.fomin.nyakashop.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ResourceService {

    String getResourceUrl(String bucketName, UUID resourceId);

    String getResourceUrl(UUID resourceId);

    UUID uploadResource(String bucketName, MultipartFile file);

    UUID uploadResource(MultipartFile file);

}
