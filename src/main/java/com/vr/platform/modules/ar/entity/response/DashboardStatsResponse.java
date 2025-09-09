package com.vr.platform.modules.ar.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 仪表盘统计数据响应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponse {
    
    /**
     * 总合集数量
     */
    private int totalCollections;
    
    /**
     * 总场景数量
     */
    private int totalScenes;
    
    /**
     * 总访问次数
     */
    private int totalAccess;
    
    /**
     * 总用户数量
     */
    private int totalUsers;
}