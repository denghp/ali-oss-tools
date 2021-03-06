package org.mvnsearch.ali.oss.spring.services.impl;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.*;
import junit.framework.Test;
import junit.framework.TestCase;
import org.apache.commons.lang3.StringUtils;
import org.mvnsearch.ali.oss.spring.services.OSSUri;
import org.springframework.mail.javamail.ConfigurableMimeFileTypeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * aliyun oss service implementation test case
 *
 * @author denghp
 */
public class AliyunOssServiceImplTest extends TestCase {
    /**
     * aliyun oss service
     */
    private AliyunOssServiceImpl aliyunOssService;
    /**
     * bucket name
     */
    private String bucketName = "denghp";
    private static ConfigurableMimeFileTypeMap mimeTypes = new ConfigurableMimeFileTypeMap();


    /**
     * setup logic, please create .aliyunoss.cfg in user home directory and fill access info
     *
     * @throws Exception exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        aliyunOssService = new AliyunOssServiceImpl();
        ConfigServiceImpl configService = new ConfigServiceImpl();
        configService.init();
        aliyunOssService.setConfigService(configService);
        aliyunOssService.refreshToken();
    }

    /**
     * test to list buckets
     *
     * @throws Exception exception
     */
    public void testListBuckets() throws Exception {
        List<Bucket> buckets = aliyunOssService.getBuckets();
        for (Bucket bucket : buckets) {
            System.out.println(bucket.getName());
        }
    }

    /**
     * test to list bucket
     *
     * @throws Exception exception
     */
    public void testList() throws Exception {
        long start = System.currentTimeMillis();
        ObjectListing objectListing = aliyunOssService.list(bucketName, "", 1000);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            objectSummary.setBucketName(bucketName);
            System.out.println("key : " + objectSummary.getKey());
        }
        end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    /**
     * test to get object metadata
     *
     * @throws Exception exception
     */
    public void testGetObjectMetadata() throws Exception {
        ObjectMetadata objectMetadata = aliyunOssService.getObjectMetadata(new OSSUri(bucketName, "media/1.jpg"));
        printMetaData(objectMetadata);
    }

    public void testGetObject() throws Exception {
        aliyunOssService.get(new OSSUri(bucketName, "media/syntax-3.0.83/js/shBrushJava.js"),"/home/denghp/aliyun_oss/denghp");
    }

    public void testGetAllObject() throws Exception {
        aliyunOssService.getAll(bucketName,"media/syntax-3.0.83/js/","/home/denghp/aliyun_oss/denghp");
    }


    /**
     * print meta data information
     *
     * @param objectMetadata object metadata
     */
    private void printMetaData(ObjectMetadata objectMetadata) {
        System.out.println("---raw metadata-----");
        for (Map.Entry<String, Object> entry : objectMetadata.getRawMetadata().entrySet()) {
            System.out.println(StringUtils.rightPad(entry.getKey(), 20, ' ') + " : " + entry.getValue());
        }
        System.out.println("---user metadata-----");
        for (Map.Entry<String, String> entry : objectMetadata.getUserMetadata().entrySet()) {
            System.out.println(StringUtils.rightPad(entry.getKey(), 20, ' ') + " : " + entry.getValue());
        }
    }

    /**
     * test to put object
     *
     * @throws Exception exception
     */
    public void testPutObject() throws Exception {
        String destFile = "demo.jpg";
        String sourceFile = "/Users/denghp/demo.jpg";
        aliyunOssService.put(sourceFile, new OSSUri(bucketName, destFile));
        ObjectMetadata objectMetadata = aliyunOssService.getObjectMetadata(new OSSUri(bucketName, destFile));
        printMetaData(objectMetadata);
    }
}
