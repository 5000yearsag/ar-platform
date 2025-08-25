package com.vr.platform.modules.ar.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponse {
    private int totalCollections;
    private int totalScenes;
    private int totalAccess;
    private int totalUsers;
}