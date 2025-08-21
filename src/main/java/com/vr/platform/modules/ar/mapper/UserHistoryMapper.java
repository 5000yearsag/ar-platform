package com.vr.platform.modules.ar.mapper;

import com.vr.platform.modules.ar.entity.UserHistory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserHistoryMapper {

    @Insert("INSERT INTO user_history(open_id, app_id, collection_uuid, collection_name) " +
            "VALUES(#{openId}, #{appId}, #{collectionUuid}, #{collectionName})")
    void insert(UserHistory history);

    @Select("SELECT * FROM user_history WHERE open_id = #{openId} AND collection_uuid = #{collectionUuid}")
    UserHistory findByOpenIdAndCollection(@Param("openId") String openId, @Param("collectionUuid") String collectionUuid);

    @Update("UPDATE user_history SET access_count = access_count + 1, update_time = NOW() " +
            "WHERE open_id = #{openId} AND collection_uuid = #{collectionUuid}")
    void updateAccessTime(@Param("openId") String openId, @Param("collectionUuid") String collectionUuid);

    @Select("SELECT * FROM user_history WHERE open_id = #{openId} ORDER BY update_time DESC LIMIT 50")
    List<UserHistory> findByOpenIdOrderByTime(@Param("openId") String openId);
}