package com.petlife.platform.app.mapper;

import com.petlife.platform.common.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    /**
     * 根据手机号和状态查询用户，状态在指定列表中，返回Optional封装单个User
     * @param phone 手机号
     * @param statuses 状态数组，如 {0, 2}
     * @return Optional<User>
     */
    Optional<User> selectByPhoneAndStatusIn(@Param("phone") String phone, @Param("statuses") int[] statuses);

    /**
     * 只查手机号
     * @param phone
     * @return
     */
    User selectByPhone(@Param("phone") String phone);

    /**
     * 插入新用户
     * @param user 用户实体
     * @return 影响的行数
     */
    int insert(User user);


    /**
     * 查询昵称是否已存在
     * @param nickname
     * @return
     */
    int countByNickname(@Param("nickname") String nickname);

    /**
     * 根据手机号前缀删除用户
     * @param phonePrefix 手机号前缀，如 "185162"
     * @return 删除的行数
     */
    int deleteByPhonePrefix(@Param("phonePrefix") String phonePrefix);

    /**
     * 根据手机号前缀查询用户数量
     * @param phonePrefix 手机号前缀，如 "185162"
     * @return 用户数量
     */
    int countByPhonePrefix(@Param("phonePrefix") String phonePrefix);

    /**
     * 根据手机号前缀查询用户详细信息
     * @param phonePrefix 手机号前缀，如 "185162"
     * @return 用户列表
     */
    List<User> selectByPhonePrefix(@Param("phonePrefix") String phonePrefix);

}
