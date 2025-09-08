package com.vr.platform.modules.ar.service.impl;

import com.vr.platform.modules.ar.entity.UserUploadedImage;
import com.vr.platform.modules.ar.entity.response.UploadUserImageResponse;
import com.vr.platform.modules.ar.mapper.UserUploadedImageMapper;
import com.vr.platform.modules.ar.service.UserUploadedImageService;
import com.vr.platform.modules.oss.entity.UploadFileInfo;
import com.vr.platform.modules.oss.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserUploadedImageServiceImpl implements UserUploadedImageService {

    @Autowired
    private UserUploadedImageMapper userUploadedImageMapper;

    @Autowired
    private FileService fileService;

    @Override
    public UploadUserImageResponse uploadUserImage(MultipartFile file, String collectionUuid, String sceneUuid, String userOpenid) throws Exception {
        log.info("用户上传图片 - collectionUuid: {}, sceneUuid: {}, userOpenid: {}", collectionUuid, sceneUuid, userOpenid);
        
        // 上传文件到OSS
        UploadFileInfo uploadFileInfo = fileService.uploadFile(file, "user-images");
        
        // 创建用户图片记录
        UserUploadedImage userImage = new UserUploadedImage();
        userImage.setCollectionUuid(collectionUuid);
        userImage.setSceneUuid(sceneUuid);
        userImage.setUserOpenid(userOpenid);
        userImage.setImageUrl(uploadFileInfo.getUrl());
        userImage.setOriginalImageUrl(uploadFileInfo.getUrl()); // 如果有原图保存可以区分
        userImage.setUploadTime(new Date());
        
        // 保存到数据库
        int result = userUploadedImageMapper.insertUserImage(userImage);
        
        if (result > 0) {
            UploadUserImageResponse response = new UploadUserImageResponse();
            response.setImageUrl(uploadFileInfo.getUrl());
            response.setRecordId(Long.valueOf(userImage.getId()));
            response.setUploadTime(System.currentTimeMillis());
            
            log.info("用户图片上传成功 - recordId: {}, imageUrl: {}", userImage.getId(), uploadFileInfo.getUrl());
            return response;
        } else {
            throw new RuntimeException("保存用户图片记录失败");
        }
    }

    @Override
    public List<UserUploadedImage> getByCollectionAndScene(String collectionUuid, String sceneUuid) {
        log.info("获取用户图片 - collectionUuid: {}, sceneUuid: {}", collectionUuid, sceneUuid);
        return userUploadedImageMapper.getByCollectionAndScene(collectionUuid, sceneUuid);
    }

    @Override
    public List<UserUploadedImage> getByUserOpenid(String userOpenid) {
        log.info("获取用户图片 - userOpenid: {}", userOpenid);
        return userUploadedImageMapper.getByUserOpenid(userOpenid);
    }

    @Override
    public List<UserUploadedImage> getByCollectionUuid(String collectionUuid) {
        log.info("获取用户图片 - collectionUuid: {}", collectionUuid);
        return userUploadedImageMapper.getByCollectionUuid(collectionUuid);
    }

    @Override
    public void deleteById(Long id) {
        log.info("删除用户图片 - id: {}", id);
        userUploadedImageMapper.deleteById(id);
    }
}