package com.kingboy.service.user.impl;

import com.kingboy.common.utils.mapper.MapperUtils;
import com.kingboy.service.user.UserService;
import com.kingboy.service.user.response.UserDTO;
import com.kingboy.repository.user.UserRepository;
import com.kingboy.repository.user.entity.Status;
import com.kingboy.repository.user.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/11/30 上午3:24
 * @desc .
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService{

    @Resource
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        return user;
    }


    @Override
    @Transactional
    public UserDTO getUser(Long id) {
        UserEntity one = userRepository.getOne(id);
        UserDTO userDTO = MapperUtils.mapperBean(one, UserDTO.class);
        return userDTO;
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        UserEntity userEntity = MapperUtils.mapperBean(userDTO, UserEntity.class);
        userEntity.setAuthorities("admin")
                .setPassword(new BCryptPasswordEncoder().encode(userEntity.getPassword()))
                .setExpiredTime(LocalDateTime.MAX)
                .setLastLoginTime(LocalDateTime.now())
                .setStatus(Status.ENABLE)
                .setLocked(false);
    }
}
