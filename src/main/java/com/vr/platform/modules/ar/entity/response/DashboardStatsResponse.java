package com.vr.platform.modules.ar.entity.response;

<<<<<<< HEAD
<<<<<<< HEAD
=======
import io.swagger.annotations.ApiModelProperty;
>>>>>>> 84e1d82 (Temporary save of local changes)
=======
>>>>>>> 684506b (feat: restore all missing functionality from remote main branch)
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
<<<<<<< HEAD
<<<<<<< HEAD
    private int totalCollections;
    private int totalScenes;
    private int totalAccess;
=======

    @ApiModelProperty(value = "总合集数")
=======
    
    /**
     * 总合集数量
     */
>>>>>>> 684506b (feat: restore all missing functionality from remote main branch)
    private int totalCollections;
    
    /**
     * 总场景数量
     */
    private int totalScenes;
    
    /**
     * 总访问次数
     */
    private int totalAccess;
<<<<<<< HEAD

    @ApiModelProperty(value = "总用户数")
>>>>>>> 84e1d82 (Temporary save of local changes)
=======
    
    /**
     * 总用户数量
     */
>>>>>>> 684506b (feat: restore all missing functionality from remote main branch)
    private int totalUsers;
}