package com.vr.platform.modules.ar.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vr.platform.common.bean.response.BizReturnCode;
import com.vr.platform.common.exception.BizException;
import com.vr.platform.common.service.CommonService;
import com.vr.platform.common.utils.CommonUtils;
import com.vr.platform.modules.ar.entity.CollectionInfo;
import com.vr.platform.modules.ar.entity.SceneInfo;
import com.vr.platform.modules.ar.entity.WxAppInfo;
import com.vr.platform.modules.ar.entity.request.*;
import com.vr.platform.modules.ar.mapper.CollectionInfoMapper;
import com.vr.platform.modules.ar.mapper.SceneInfoMapper;
import com.vr.platform.modules.ar.mapper.WxAppMapper;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@Service
public class WxAppService {

    @Resource
    private WxAppMapper wxAppMapper;

    @Resource
    private WxMaService wxMaService;

    public List<WxAppInfo> getAllWxAppList() {
        return wxAppMapper.getAllWxApp();
    }

    public WxAppInfo getAppByAppId(GetWxAppRequest request) {
        return wxAppMapper.getAppByAppId(request.getAppId());
    }

    public void addWxApp(AddWxAppRequest appRequest) {
        WxAppInfo appByAppId = wxAppMapper.getAppByAppId(appRequest.getAppId());
        if(ObjectUtil.isNotEmpty(appByAppId)){
            throw new BizException(BizReturnCode.BIZ_WX_APP_EXIST);
        }
        wxAppMapper.addNewWxApp(appRequest);
    }

    public void updateApp(UpdateWxAppRequest appRequest) {
        WxAppInfo appByAppId = wxAppMapper.findById(appRequest.getId());
        if(ObjectUtil.isEmpty(appByAppId)){
            throw new BizException(BizReturnCode.BIZ_WX_APP_NOT_EXIST);
        }
        wxAppMapper.updateApp(appRequest);
    }

    public void deleteApp(Long id) {
        wxAppMapper.deleteApp(id);
    }

    public void deleteByAppId(String appId) {
        wxAppMapper.deleteByAppId(appId);
    }

    public String getOpenId(String code, String appId) {
        try {
            if (wxMaService == null) {
                log.warn("WeChat service not available, returning mock openId");
                return "mock_openid_" + System.currentTimeMillis();
            }
            WxMaJscode2SessionResult session = wxMaService.switchoverTo(appId).getUserService().getSessionInfo(code);
            return session.getOpenid();
        } catch (Exception e) {
            log.error("获取openId失败: code={}, appId={}, error={}", code, appId, e.getMessage());
            return "mock_openid_" + System.currentTimeMillis();
        }
    }
}
