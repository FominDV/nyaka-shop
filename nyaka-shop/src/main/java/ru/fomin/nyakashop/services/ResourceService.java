package ru.fomin.nyakashop.services;

public interface ResourceService {

    String getResourceUrl(String bucketName,String sourceName);

    String getResourceUrl(String sourceName);

}
