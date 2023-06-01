package ru.fomin.nyakashop.configs;



import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${minio.ip}")
    String ip;

    @Value("${minio.user}")
    String accessKey;

    @Value("${minio.password}")
    String secretKey;

    @Value("${minio.port}")
    Integer port;

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(ip, port, false)
                .credentials(accessKey, secretKey)
                .build();
    }

}
