package com.liteMallReplicate.litemallcore.storage;

import org.apache.juli.logging.LogFactory;
import org.apache.commons.logging.Log;

import java.io.InputStream;
import java.io.OutputStream;

public class AliyunStorage implements Storage {

    private final Log logger = LogFactory.getLog(AliyunStorage.class);

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    private OSSClient getOSSClient() {
        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    private String getBaseUrl() {
        return "https://" + bucketName + "." + endpoint + "/";
    }

    @Override
    public void store(InputStream inputStream, long contentLength, String contentType, String keyName) {}

}
