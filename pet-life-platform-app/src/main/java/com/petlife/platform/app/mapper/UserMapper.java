package com.petlife.platform.app.mapper;

import com.petlife.platform.app.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserMapper {

    /**
     * 根据手机号和状态查询用户，状态在指定列表中，返回Optional封装单个User
     * @param mobile 手机号
     * @param statuses 状态数组，如 {0, 2}
     * @return Optional<User>
     */
    Optional<User> selectByPhoneAndStatusIn(@Param("mobile") String mobile, @Param("statuses") int[] statuses);
}
