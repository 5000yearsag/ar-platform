package com.vr.platform.modules.ar.entity.response;

<<<<<<< HEAD
=======
import io.swagger.annotations.ApiModelProperty;
>>>>>>> 84e1d82 (Temporary save of local changes)
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponse {
<<<<<<< HEAD
    private int totalCollections;
    private int totalScenes;
    private int totalAccess;
=======

    @ApiModelProperty(value = "总合集数")
    private int totalCollections;

    @ApiModelProperty(value = "总场景数")
    private int totalScenes;

    @ApiModelProperty(value = "总访问数")
    private int totalAccess;

    @ApiModelProperty(value = "总用户数")
>>>>>>> 84e1d82 (Temporary save of local changes)
    private int totalUsers;
}