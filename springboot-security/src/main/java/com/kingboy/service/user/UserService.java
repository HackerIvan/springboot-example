package com.kingboy.service.user;

import com.kingboy.service.user.response.UserDTO;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/11/30 上午3:24
 * @desc 用户服务.
 */
public interface UserService {

    UserDTO getUser(Long id);

    void saveUser(UserDTO userDTO);
}
