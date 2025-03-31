package com.example.demo.controller;

import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MinioTestController {

    @Autowired
    private MinioClient minioClient;

    @GetMapping("/minio/list")
    public List<String> listObjects(@RequestParam String bucketName) {
        List<String> objectNames = new ArrayList<>();
        try {
            new ListObjectsArgs();
            Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
            for (Result<Item> result : results) {
                objectNames.add(result.get().objectName());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error listing objects in bucket: " + bucketName, e);
        }
        return objectNames;
    }

    @GetMapping("/minio/buckets")
    public List<String> listBuckets() {
        try {
            return minioClient.listBuckets().stream().map(bucket -> bucket.name()).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error listing buckets", e);
        }
    }
}
