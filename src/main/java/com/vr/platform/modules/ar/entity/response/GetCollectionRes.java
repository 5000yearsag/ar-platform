package com.vr.platform.modules.ar.entity.response;

import com.vr.platform.modules.ar.entity.CollectionAppInfo;
import com.vr.platform.modules.ar.entity.CollectionInfo;
import lombok.Data;

import java.util.List;

@Data
public class GetCollectionRes extends CollectionInfo{
    private int sceneCount;
    private List<CollectionAppInfo> collectionAppList;
    private int pvCount;        // 打开
    private int click1Count;    // 进入
    private int click2Count;    // 播放  
    private int click3Count;    // 拍照分享
    private int click4Count;    // 录像分享
    private int click5Count;    // 资源加载
}
