package com.petlife.platform.app.mapper;

import com.petlife.platform.common.pojo.dto.UserProfileDTO;
import com.petlife.platform.common.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
     * 查询同一用户 ID 下是否有相同昵称
     * @param userId
     * @param nickname
     * @return
     */
    int countByNickname( @Param("userId") Long userId,@Param("nickname") String nickname);

    /**
     * 更新用户基本资料
     */
    void updateProfile(UserProfileDTO userProfileDTO);

}
