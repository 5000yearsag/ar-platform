package com.vr.platform.modules.ar.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class UpdateCollectionRequest {
    @ApiModelProperty(value = "合集uuid")
    @NotBlank
    private String collectionUuid;

    @ApiModelProperty(value = "合集名称")
    private String collectionName;

    @ApiModelProperty(value = "合集封面")
    private String coverImgUrl;

    @ApiModelProperty(value = "小程序信息列表")
    private List<AddCollectionAppRequest> wxAppInfoList;

    @ApiModelProperty(value = "合集描述")
    private String description;

    @ApiModelProperty(value = "合集类型")
    private Integer collectionType;

    @ApiModelProperty(value = "加载类型 0-普通加载 1-分段加载")
    private Integer loadType;

    @ApiModelProperty(value = "是否启用用户上传图片功能 0-否 1-是")
    private Integer enableUserImage;
}
