package com.vr.platform.modules.ar.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserUploadedImage {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "状态  0：禁用   1：正常")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

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