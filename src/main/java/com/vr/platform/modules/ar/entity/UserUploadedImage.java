package com.vr.platform.modules.ar.entity;

import com.vr.platform.common.bean.entity.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserUploadedImage extends BaseInfo {

    @ApiModelProperty(value = "合集UUID")
    private String collectionUuid;

    @ApiModelProperty(value = "场景UUID")
    private String sceneUuid;

    @ApiModelProperty(value = "用户openid")
    private String userOpenid;

    @ApiModelProperty(value = "用户上传的图片URL")
    private String imageUrl;

    @ApiModelProperty(value = "原始图片URL")
    private String originalImageUrl;

    @ApiModelProperty(value = "上传时间")
    private Date uploadTime;
}