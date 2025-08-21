package com.vr.platform.modules.ar.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
public class AccessStatistics {
    private Long id;
    private String collectionUuid;
    private String openId;
    private String statisticType;
    private String userIp;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}