package com.vr.platform.modules.ar.service;

import com.vr.platform.common.utils.IPUtils;
import com.vr.platform.modules.ar.entity.AccessStatistics;
import com.vr.platform.modules.ar.entity.UserHistory;
import com.vr.platform.modules.ar.mapper.AccessStatisticsMapper;
import com.vr.platform.modules.ar.mapper.UserHistoryMapper;
import com.vr.platform.modules.ar.mapper.CollectionInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
public class StatisticsService {

    @Resource
    private AccessStatisticsMapper accessStatisticsMapper;
    
    @Resource 
    private UserHistoryMapper userHistoryMapper;
    
    @Resource
    private CollectionInfoMapper collectionInfoMapper;

    public void recordAccess(String collectionUuid, String type, String openId, HttpServletRequest request) {
        try {
            AccessStatistics statistics = new AccessStatistics();
            statistics.setCollectionUuid(collectionUuid);
            statistics.setStatisticType(type);
            statistics.setOpenId(openId);
            statistics.setUserIp(IPUtils.getIpAddr(request));
            
            accessStatisticsMapper.insert(statistics);
        } catch (Exception e) {
            log.error("记录访问统计失败: collection={}, type={}, error={}", collectionUuid, type, e.getMessage());
        }
    }

    public void recordUserHistory(String openId, String appId, String collectionUuid) {
        try {
            UserHistory existingHistory = userHistoryMapper.findByOpenIdAndCollection(openId, collectionUuid);
            if (existingHistory != null) {
                userHistoryMapper.updateAccessTime(openId, collectionUuid);
            } else {
                String collectionName = collectionInfoMapper.getCollectionNameByUuid(collectionUuid);
                UserHistory history = new UserHistory();
                history.setOpenId(openId);
                history.setAppId(appId);
                history.setCollectionUuid(collectionUuid);
                history.setCollectionName(collectionName);
                userHistoryMapper.insert(history);
            }
        } catch (Exception e) {
            log.error("记录用户访问历史失败: openId={}, collection={}, error={}", openId, collectionUuid, e.getMessage());
        }
    }

    public List<UserHistory> getUserHistory(String openId) {
        return userHistoryMapper.findByOpenIdOrderByTime(openId);
    }
}