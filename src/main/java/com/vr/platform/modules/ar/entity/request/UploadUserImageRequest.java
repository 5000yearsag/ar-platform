package com.vr.platform.modules.ar.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UploadUserImageRequest {
    
    @ApiModelProperty(value = "合集UUID")
    @NotBlank
    private String collectionUuid;
    
    @ApiModelProperty(value = "场景UUID")
    @NotBlank
    private String sceneUuid;
    
    @ApiModelProperty(value = "用户openid")
    private String userOpenid;
}