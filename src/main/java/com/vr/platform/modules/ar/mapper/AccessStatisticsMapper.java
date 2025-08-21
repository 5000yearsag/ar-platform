package com.vr.platform.modules.ar.mapper;

import com.vr.platform.modules.ar.entity.AccessStatistics;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccessStatisticsMapper {

    @Insert("INSERT INTO access_statistics(collection_uuid, open_id, statistic_type, user_ip) " +
            "VALUES(#{collectionUuid}, #{openId}, #{statisticType}, #{userIp})")
    void insert(AccessStatistics statistics);
}