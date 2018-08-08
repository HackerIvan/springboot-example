package com.kingboy.repository.user;

import com.kingboy.repository.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/11/30 上午1:11
 * @desc ${DESCRIPTION}.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long>{

    UserEntity findByUsername(String username);

}
