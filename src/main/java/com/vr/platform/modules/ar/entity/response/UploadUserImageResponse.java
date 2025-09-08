package com.vr.platform.modules.ar.entity.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UploadUserImageResponse {
    
    @ApiModelProperty(value = "图片URL")
    private String imageUrl;
    
    @ApiModelProperty(value = "记录ID")
    private Long recordId;
    
    @ApiModelProperty(value = "上传时间戳")
    private Long uploadTime;
}