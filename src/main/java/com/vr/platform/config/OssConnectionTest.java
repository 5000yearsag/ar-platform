package com.vr.platform.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OssConnectionTest implements CommandLineRunner {

    @Value("${dromara.x-file-storage.aliyun-oss[0].access-key}")
    private String accessKey;

    @Value("${dromara.x-file-storage.aliyun-oss[0].secret-key}")
    private String secretKey;

    @Value("${dromara.x-file-storage.aliyun-oss[0].end-point}")
    private String endpoint;

    @Value("${dromara.x-file-storage.aliyun-oss[0].bucket-name}")
    private String bucketName;

    @Override
    public void run(String... args) throws Exception {
        log.info("开始测试OSS连接...");
        log.info("Endpoint: {}", endpoint);
        log.info("Bucket: {}", bucketName);
        log.info("AccessKey: {}", accessKey.substring(0, 8) + "***");
        
        try {
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKey, secretKey);
            
            // 测试连接
            boolean exists = ossClient.doesBucketExist(bucketName);
            log.info("Bucket '{}' 存在: {}", bucketName, exists);
            
            if (exists) {
                // 获取bucket信息
                Bucket bucket = ossClient.getBucketInfo(bucketName).getBucket();
                log.info("Bucket区域: {}", bucket.getLocation());
                log.info("Bucket创建时间: {}", bucket.getCreationDate());
                log.info("OSS连接测试成功！");
            } else {
                log.error("Bucket '{}' 不存在！", bucketName);
            }
            
            ossClient.shutdown();
        } catch (Exception e) {
            log.error("OSS连接测试失败: {}", e.getMessage(), e);
        }
    }
}
