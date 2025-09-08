package com.vr.platform.modules.ar.service;

import com.vr.platform.modules.ar.entity.UserUploadedImage;
import com.vr.platform.modules.ar.entity.response.UploadUserImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserUploadedImageService {

    /**
     * 用户上传图片
     */
    UploadUserImageResponse uploadUserImage(MultipartFile file, String collectionUuid, String sceneUuid, String userOpenid) throws Exception;

    /**
     * 根据合集和场景获取用户上传的图片
     */
    List<UserUploadedImage> getByCollectionAndScene(String collectionUuid, String sceneUuid);

    /**
     * 根据用户openid获取上传的图片
     */
    List<UserUploadedImage> getByUserOpenid(String userOpenid);

    /**
     * 根据合集UUID获取用户上传的图片
     */
    List<UserUploadedImage> getByCollectionUuid(String collectionUuid);

    /**
     * 删除用户上传的图片
     */
    void deleteById(Long id);
}