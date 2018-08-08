package com.kingboy.service.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/11/30 上午1:10
 * @desc 用户信息载体.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String password;

    private String username;

    private String address;

}
