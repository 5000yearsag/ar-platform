package com.vr.platform.modules.ar.entity.response;

import com.vr.platform.modules.ar.entity.CollectionAppInfo;
import com.vr.platform.modules.ar.entity.CollectionInfo;
import lombok.Data;

import java.util.List;

@Data
public class GetCollectionRes extends CollectionInfo{
    private int sceneCount;
    private List<CollectionAppInfo> collectionAppList;
    private Integer pvCount;        // 打开
    private Integer click1Count;    // 进入
    private Integer click2Count;    // 播放  
    private Integer click3Count;    // 拍照分享
    private Integer click4Count;    // 录像分享
    private Integer click5Count;    // 资源加载
}
