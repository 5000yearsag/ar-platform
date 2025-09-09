package com.vr.platform.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/test")
public class OssTestController {

    @Value("${dromara.x-file-storage.aliyun-oss[0].access-key}")
    private String accessKeyId;

    @Value("${dromara.x-file-storage.aliyun-oss[0].secret-key}")
    private String accessKeySecret;

    @Value("${dromara.x-file-storage.aliyun-oss[0].end-point}")
    private String endpoint;

    @Value("${dromara.x-file-storage.aliyun-oss[0].bucket-name}")
    private String bucketName;

    @GetMapping("/oss-connection")
    public Map<String, Object> testOssConnection() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            log.info("开始测试OSS连接...");
            log.info("Endpoint: {}", endpoint);
            log.info("Bucket: {}", bucketName);
            
            // 创建OSS客户端
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            
            // 测试1: 检查bucket是否存在
            boolean bucketExists = ossClient.doesBucketExist(bucketName);
            result.put("bucketExists", bucketExists);
            log.info("Bucket存在性检查: {}", bucketExists);
            
            if (bucketExists) {
                // 测试2: 尝试上传一个小文件
                String testKey = "test/connection-test-" + System.currentTimeMillis() + ".txt";
                String testContent = "OSS连接测试 - " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                
                try {
                    ossClient.putObject(bucketName, testKey, new ByteArrayInputStream(testContent.getBytes()));
                    result.put("uploadTest", "成功");
                    log.info("上传测试成功: {}", testKey);
                    
                    // 测试3: 尝试下载文件
                    OSSObject ossObject = ossClient.getObject(bucketName, testKey);
                    result.put("downloadTest", "成功");
                    log.info("下载测试成功: {}", testKey);
                    ossObject.close();
                    
                    // 清理测试文件
                    ossClient.deleteObject(bucketName, testKey);
                    result.put("cleanupTest", "成功");
                    log.info("清理测试文件成功: {}", testKey);
                    
                } catch (Exception e) {
                    result.put("uploadTest", "失败: " + e.getMessage());
                    log.error("上传测试失败", e);
                }
            }
            
            // 测试4: 网络连接测试
            try {
                long startTime = System.currentTimeMillis();
                ossClient.doesBucketExist(bucketName);
                long endTime = System.currentTimeMillis();
                result.put("responseTime", (endTime - startTime) + "ms");
                log.info("响应时间: {}ms", (endTime - startTime));
            } catch (Exception e) {
                result.put("responseTime", "测试失败: " + e.getMessage());
                log.error("响应时间测试失败", e);
            }
            
            ossClient.shutdown();
            result.put("status", "success");
            result.put("message", "OSS连接测试完成");
            
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", "OSS连接测试失败: " + e.getMessage());
            result.put("errorType", e.getClass().getSimpleName());
            log.error("OSS连接测试失败", e);
        }
        
        return result;
    }
    
    @GetMapping("/network-info")
    public Map<String, Object> getNetworkInfo() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 获取系统网络信息
            result.put("javaVersion", System.getProperty("java.version"));
            result.put("osName", System.getProperty("os.name"));
            result.put("osVersion", System.getProperty("os.version"));
            result.put("userTimezone", System.getProperty("user.timezone"));
            
            // 网络相关系统属性
            result.put("httpProxyHost", System.getProperty("http.proxyHost"));
            result.put("httpProxyPort", System.getProperty("http.proxyPort"));
            result.put("httpsProxyHost", System.getProperty("https.proxyHost"));
            result.put("httpsProxyPort", System.getProperty("https.proxyPort"));
            
            result.put("status", "success");
            
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", e.getMessage());
        }
        
        return result;
    }
}
