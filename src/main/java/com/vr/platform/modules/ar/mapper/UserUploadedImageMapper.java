package com.vr.platform.modules.ar.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vr.platform.modules.ar.entity.UserUploadedImage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserUploadedImageMapper extends BaseMapper<UserUploadedImage> {

    @Insert("INSERT INTO user_uploaded_images(collection_uuid, scene_uuid, user_openid, image_url, original_image_url, upload_time) " +
            "VALUES(#{collectionUuid}, #{sceneUuid}, #{userOpenid}, #{imageUrl}, #{originalImageUrl}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUserImage(UserUploadedImage userImage);

    @Select("SELECT * FROM user_uploaded_images WHERE collection_uuid = #{collectionUuid} AND scene_uuid = #{sceneUuid}")
    List<UserUploadedImage> getByCollectionAndScene(@Param("collectionUuid") String collectionUuid, 
                                                    @Param("sceneUuid") String sceneUuid);

    @Select("SELECT * FROM user_uploaded_images WHERE user_openid = #{userOpenid} ORDER BY upload_time DESC")
    List<UserUploadedImage> getByUserOpenid(@Param("userOpenid") String userOpenid);

    @Select("SELECT * FROM user_uploaded_images WHERE collection_uuid = #{collectionUuid} ORDER BY upload_time DESC")
    List<UserUploadedImage> getByCollectionUuid(@Param("collectionUuid") String collectionUuid);

    @Delete("DELETE FROM user_uploaded_images WHERE id = #{id}")
    void deleteById(@Param("id") Long id);
}