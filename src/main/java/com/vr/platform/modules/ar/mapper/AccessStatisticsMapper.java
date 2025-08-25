package com.vr.platform.modules.ar.mapper;

import com.vr.platform.modules.ar.entity.AccessStatistics;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccessStatisticsMapper {

    @Insert("INSERT INTO access_statistics(collection_uuid, open_id, statistic_type, user_ip) " +
            "VALUES(#{collectionUuid}, #{openId}, #{statisticType}, #{userIp})")
    void insert(AccessStatistics statistics);

    @Select("SELECT count(*) FROM access_statistics")
    int getTotalAccessCount();

    @Select("SELECT count(DISTINCT open_id) FROM access_statistics WHERE open_id IS NOT NULL")
    int getTotalUserCount();

    @Select("SELECT count(*) FROM access_statistics WHERE collection_uuid = #{collectionUuid} AND statistic_type = #{statisticType}")
    int getCollectionStatCount(@Param("collectionUuid") String collectionUuid, @Param("statisticType") String statisticType);
}