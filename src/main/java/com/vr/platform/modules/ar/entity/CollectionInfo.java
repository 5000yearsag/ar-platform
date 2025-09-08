package com.vr.platform.modules.ar.entity;

import com.vr.platform.common.bean.entity.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CollectionInfo extends BaseInfo {

    @ApiModelProperty(value = "合集uuid")
    private String collectionUuid;

    @ApiModelProperty(value = "合集名称")
    private String collectionName;

    @ApiModelProperty(value = "合集封面")
    private String coverImgUrl;

    @ApiModelProperty(value = "合集描述")
    private String description;

    @ApiModelProperty(value = "合集类型")
    private Integer collectionType;

    @ApiModelProperty(value = "加载类型 0-普通加载 1-分段加载")
    private Integer loadType;

    @ApiModelProperty(value = "是否启用用户上传图片功能 0-否 1-是")
    private Integer enableUserImage;

    @ApiModelProperty(value = "页面浏览量")
    private Integer pvCount;

    @ApiModelProperty(value = "点击统计1")
    private Integer click1Count;

    @ApiModelProperty(value = "点击统计2")
    private Integer click2Count;

    @ApiModelProperty(value = "点击统计3")
    private Integer click3Count;

    @ApiModelProperty(value = "点击统计4")
    private Integer click4Count;

    @ApiModelProperty(value = "点击统计5")
    private Integer click5Count;
}
