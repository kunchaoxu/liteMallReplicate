package com.liteMallReplicate.litemallcore.storage;

import jakarta.annotation.Resource;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface Storage {

    void store(InputStream inputStream, long contentLength, String contentType, String keyNmae);

    Stream<Path> loadAll();

    Path load(String keyName);

    Resource loadAsResource(String keyName);

    void delete(String keyName);

    String generateUrl(String keyName);
}
