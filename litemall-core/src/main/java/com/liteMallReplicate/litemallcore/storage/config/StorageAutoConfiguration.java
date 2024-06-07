package com.liteMallReplicate.litemallcore.storage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.liteMallReplicate.litemallcore.storage.*;

@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class StorageAutoConfiguration {

    private final StorageProperties properties;

    public StorageAutoConfiguration(StorageProperties properties) {this.properties = properties;}

    @Bean
    public StorageService storageService() {
        StorageService storageService = new StorageService();
        String active = this.properties.getActive();
        storageService.setActivate(active);
        if (active.equals("local")) {
            storageService.setStorage(localStorage());
        } else if (active.equals("aliyun")) {
            storageService.setStorage(AliyunStorage());
        } else if (active.equals("qiniu")) {
            storageService.setStorage(qiniuStorage());
        } else {
            throw new RuntimeException(("Current storage model" + active + " is not supported"));
        }

        return storageService;
    }

    @Bean
    public LocalStorage localStorage() {
        LocalStorage localStorage = new LocalStorage();
        StorageProperties.Local local = this.properties.getLocal();
        localStorage.setAddress(local.getAddress());
        localStorage.setStoragePath(local.getLocalPath());
        return localStorage;
    }

    @Bean
    public AliyunStorage aliyunStorage() {
        AliyunStorage aliyunStorage = new AliyunStorage();
        StorageProperties.Aliyun aliyun = this.properties.getAliyun();
        aliyunStorage.setAccessKeyId(aliyun.getAccessKeyId());
        aliyunStorage.setAccessKeySecret(aliyun.getAccessKeySecret());
        aliyunStorage.setBucketName(aliyun.getBucketName());
        aliyunStorage.setEndpoint(aliyun.getEndpoint());
        return aliyunStorage;
    }

    @Bean
    public TencentStorage tencentStorage() {
        TencentStorage tencentStorage = new TencentStorage();
        StorageProperties.Tencent tencent = this.properties.getTencent();
        tencentStorage.setSecretId(tencent.getSecretId());
        tencentStorage.setSecretKey(tencent.getSecretKey());
        tencentStorage.setBucketName(tencent.getBucketName());
        tencentStorage.setRegion(tencent.getRegion());
        return tencentStorage;
    }

    @Bean
    public QiniuStorage qiniuStorage() {
        QiniuStorage qiniuStorage = new QiniuStorage();
        StorageProperties.Qiniu qiniu = this.properties.getQiniu();
        qiniuStorage.setAccessKey(qiniu.getAccessKey());
        qiniuStorage.setSecretKey(qiniu.getSecretKey());
        qiniuStorage.setBucketName(qiniu.getBucketName());
        qiniuStorage.setEndpoint(qiniu.getEndpoint());
        return qiniuStorage;
    }
}
