package com.vr.platform.modules.ar.controller;

import cn.binarywang.wx.miniapp.api.WxMaQrcodeService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaCodeLineColor;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.aliyun.oss.model.OSSObjectSummary;
import com.vr.platform.common.bean.response.ResponseFormat;
import com.vr.platform.modules.ar.entity.SceneInfo;
import com.vr.platform.modules.ar.entity.request.GetSceneRequest;
import com.vr.platform.modules.ar.entity.request.GetAllCollectionRequest;
import com.vr.platform.modules.ar.entity.response.GetAllSceneRes;
import com.vr.platform.modules.ar.entity.response.UploadUserImageResponse;
import com.vr.platform.modules.ar.entity.response.WxGetAllSceneRes;
import com.vr.platform.modules.ar.entity.response.GetCollectionRes;
import com.vr.platform.modules.ar.service.SceneInfoService;
import com.vr.platform.modules.ar.service.UserUploadedImageService;
import com.vr.platform.modules.ar.service.StatisticsService;
import com.vr.platform.modules.ar.service.CollectionInfoService;
import com.vr.platform.modules.ar.service.WxAppService;
import com.vr.platform.modules.ar.entity.UserHistory;
import com.github.pagehelper.PageInfo;
import com.vr.platform.modules.oss.entity.UploadFileInfo;
import com.vr.platform.modules.oss.service.AliOssService;
import com.vr.platform.modules.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/guest")
@RequiredArgsConstructor
@Api(value = "小程序前端", tags = "小程序访问接口")
public class GuestController {

    @Autowired
    private SceneInfoService sceneInfoService;

    @Resource
    private WxAppService wxAppService;

    @Resource
    private WxMaService wxMaService;

    @Resource
    private AliOssService aliOssService;

    @Resource
    private FileService fileService;

    @Autowired
    private FileStorageService fileStorageService;//注入实列

    @Resource
    private StatisticsService statisticsService;
    
    @Autowired
    private CollectionInfoService collectionInfoService;

    @Autowired
    private UserUploadedImageService userUploadedImageService;

    @ApiModelProperty(value = "小程序获取所有场景")
    @RequestMapping(value = "/getAllSceneByCollection", method = RequestMethod.GET)
    public ResponseFormat<WxGetAllSceneRes> getAllSceneByCollection(@RequestParam(name ="collectionUuid") String collectionUuid) {
        log.info("find All sceneInfo by collection uuid {}",collectionUuid);
        return ResponseFormat.success(sceneInfoService.getAllSceneByCollectionForGuest(collectionUuid));
    }

    @PostMapping("/getScene")
    public ResponseFormat<SceneInfo> getScene(@RequestBody GetSceneRequest request) {
        log.info("findById:{}", request.getSceneUuid());
        return ResponseFormat.success(sceneInfoService.getScene(request.getSceneUuid()));
    }

    @PostMapping("/uploadFile")
    public ResponseFormat<UploadFileInfo> uploadFile(@RequestPart("file") MultipartFile file) throws Exception {
        log.info("uploadFile");
        return ResponseFormat.success(fileService.uploadFile(file,"collection"));
    }

    @GetMapping("/listPic")
    public ResponseFormat<List<OSSObjectSummary>> listPic() {
        log.info("listPic");
        return ResponseFormat.success(aliOssService.list());
    }

    @PostMapping("/testUpload")
    public ResponseFormat<UploadFileInfo> testUpload(@RequestPart("file") MultipartFile file) throws Exception {
        log.info("testUpload");
        fileStorageService.of(file)
                .setPlatform("tencent-cos-1")
                .upload();
        return ResponseFormat.success();
    }

    @ApiModelProperty(value = "生成小程序码")
    @RequestMapping(value = "/genQrCode", method = RequestMethod.GET)
    public void genQrCode() throws WxErrorException, IOException {
        log.info("genQrCode");

        String appId ="wx360d6d845e60562e";
        String appSecret = "1c6324d57b647323f7d5317f316bdb81";
//        String wxScenePage ="pages/index/index?collectionUuid=72ae19ddb6fe458c8798c6762d65d26f";
        String wxScenePage ="pages/index/index";
        String fileEnv ="trial";
        Map<String, WxMaConfig> configs = new HashMap<>();
        configs.put(appId, new WxMaDefaultConfigImpl() {{
            setAppid(appId);
            setSecret(appSecret);
        }});
        wxMaService.setMultiConfigs(configs);
        WxMaQrcodeService qrcodeService = wxMaService.getQrcodeService();
        log.info("小程序码生成url:{}, fileEnv:{}",wxScenePage, fileEnv);

//        byte[] qrcodeFileBytes = qrcodeService.createWxaCodeUnlimitBytes("?collectionUuid=3d186d8386314f46", wxScenePage, false, fileEnv, 250, true, null, false);
        String encodedUrl = URLEncoder.encode("http://123.57.231.35:8081/api/guest/getAllSceneByCollection"+"?collectionUuid="+"240715204907771", "UTF-8");
        String sceneParam = wxScenePage+"?url="+encodedUrl;
        log.info("小程序码生成url:{}, fileEnv:{}",sceneParam, fileEnv);
        WxMaCodeLineColor wxMaCodeLineColor = new WxMaCodeLineColor("0","0","0");
        byte[] qrcodeFileBytes = qrcodeService.createWxaCodeBytes(sceneParam, fileEnv,  250, false, wxMaCodeLineColor, false);

        // 获取当前路径
        String filePath = System.getProperty("user.dir");
        log.info("当前路径：{}",filePath);
        OutputStream os = Files.newOutputStream(Paths.get(filePath+"\\qrcde.jpg"));
        os.write(qrcodeFileBytes);
    }

    @ApiModelProperty(value = "访问统计")
    @GetMapping("/statistic")
    public ResponseFormat<Void> recordStatistic(
            @RequestParam(name = "collectionUuid") String collectionUuid,
            @RequestParam(name = "type") String type,
            @RequestParam(name = "openId", required = false) String openId,
            HttpServletRequest request) {
        statisticsService.recordAccess(collectionUuid, type, openId, request);
        return ResponseFormat.success();
    }

    @ApiModelProperty(value = "获取微信openId")
    @GetMapping("/openId")
    public ResponseFormat<String> getOpenId(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "appId") String appId) {
        String openId = wxAppService.getOpenId(code, appId);
        return ResponseFormat.success(openId);
    }

    @ApiModelProperty(value = "记录访问历史")
    @GetMapping("/historyRecord")
    public ResponseFormat<Void> recordHistory(
            @RequestParam(name = "openId") String openId,
            @RequestParam(name = "appId") String appId,
            @RequestParam(name = "collectionUuid") String collectionUuid) {
        statisticsService.recordUserHistory(openId, appId, collectionUuid);
        return ResponseFormat.success();
    }

    @ApiModelProperty(value = "获取访问历史")
    @GetMapping("/history")
    public ResponseFormat<List<UserHistory>> getUserHistory(
            @RequestParam(name = "openId") String openId) {
        List<UserHistory> history = statisticsService.getUserHistory(openId);
        return ResponseFormat.success(history);
    }
    
    @ApiModelProperty(value = "小程序专用 - 获取所有合集列表")
    @PostMapping("/getAllCollections")
    public ResponseFormat getAllCollections(
            @RequestBody Map<String, Object> request) {
        log.info("getAllCollections for miniprogram: {}", request);
        
        try {
            // 验证小程序AppID - 只允许我们的小程序调用
            String appId = (String) request.get("appId");
            if (!"wx360d6d845e60562e".equals(appId)) {
                log.warn("非法AppID访问: {}", appId);
                return ResponseFormat.fail();
            }
            
            // 验证时间戳防重放攻击（可选）
            Long timestamp = request.get("timestamp") != null ? 
                Long.valueOf(request.get("timestamp").toString()) : 0L;
            long currentTime = System.currentTimeMillis();
            if (Math.abs(currentTime - timestamp) > 300000) { // 5分钟有效期
                log.warn("时间戳验证失败: {}", timestamp);
                return ResponseFormat.fail();
            }
            
            // 构造分页请求
            GetAllCollectionRequest collectionRequest = new GetAllCollectionRequest();
            collectionRequest.setPageNum(request.get("pageNum") != null ? 
                Integer.valueOf(request.get("pageNum").toString()) : 1);
            collectionRequest.setPageSize(request.get("pageSize") != null ? 
                Integer.valueOf(request.get("pageSize").toString()) : 20);
            
            PageInfo<GetCollectionRes> result = collectionInfoService.getAllCollection(collectionRequest);
            log.info("返回合集数量: {}", result.getList().size());
            return ResponseFormat.success(result);
            
        } catch (Exception e) {
            log.error("获取合集列表失败", e);
            return ResponseFormat.fail();
        }
    }

    @ApiModelProperty(value = "用户上传图片")
    @PostMapping("/uploadUserImage")
    public ResponseFormat<UploadUserImageResponse> uploadUserImage(
            @RequestPart("file") MultipartFile file,
            @RequestParam("collectionUuid") String collectionUuid,
            @RequestParam("sceneUuid") String sceneUuid,
            @RequestParam("userOpenid") String userOpenid) throws Exception {
        log.info("用户上传图片 - collectionUuid: {}, sceneUuid: {}, userOpenid: {}", collectionUuid, sceneUuid, userOpenid);
        UploadUserImageResponse response = userUploadedImageService.uploadUserImage(file, collectionUuid, sceneUuid, userOpenid);
        return ResponseFormat.success(response);
    }

}
