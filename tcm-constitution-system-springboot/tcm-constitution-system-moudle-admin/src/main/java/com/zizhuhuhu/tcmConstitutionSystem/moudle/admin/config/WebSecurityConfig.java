package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.config;

import com.zizhuhuhu.tcmConstitutionSystem.module.jwt.config.JwtAuthenticationSecurityConfig;
import com.zizhuhuhu.tcmConstitutionSystem.module.jwt.filter.TokenAuthenticationFilter;
import com.zizhuhuhu.tcmConstitutionSystem.module.jwt.handler.RestAccessDeniedHandler;
import com.zizhuhuhu.tcmConstitutionSystem.module.jwt.handler.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;
    @Autowired
    private RestAuthenticationEntryPoint authEntryPoint;
    @Autowired
    private RestAccessDeniedHandler deniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(). //禁用csrf
                formLogin().disable() //禁用表单登录
                .apply(jwtAuthenticationSecurityConfig) // 设置用户登录认证相关的配置
                .and().
                authorizeRequests()
                .mvcMatchers("/admin/**").authenticated() //认证所有以/admin为前缀的URL资源
                .anyRequest().permitAll().and() //其他放行，无需登录
                .httpBasic().authenticationEntryPoint(authEntryPoint)//处理用户未登录访问受保护的资源的情况
        .and()
        .exceptionHandling().accessDeniedHandler(deniedHandler) //处理登录成功后访问受保护的资源，但是权限不够地情况
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //前后端分离，无需创建会话
        .and()
        .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }
}
