package com.kingboy.common.config;

import com.kingboy.common.config.autentication.KingFailureAuthentication;
import com.kingboy.common.config.autentication.KingSuucessfuleAuthentication;
import com.kingboy.common.config.filter.ImageCodeFilter;
import com.kingboy.common.config.property.SecurityProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/11/30 上午1:24
 * @desc 安全配置.
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Resource
    SecurityProperty securityProperty;

    @Resource
    KingSuucessfuleAuthentication kingSuucessfuleAuthentication;

    @Resource
    KingFailureAuthentication kingFailureAuthentication;




    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ImageCodeFilter imageCodeFilter = new ImageCodeFilter(kingFailureAuthentication);

        http.addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/user/auth")
                .successHandler(kingSuucessfuleAuthentication)
                .failureHandler(kingFailureAuthentication)
                .loginProcessingUrl("/user/login")
                .and()
                .authorizeRequests()
                .antMatchers("/user/auth",
                        securityProperty.getAppPage(),
                        securityProperty.getBrowerPage(),
                        "/code/image").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
