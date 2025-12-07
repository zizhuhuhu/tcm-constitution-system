package com.zizhuhuhu.tcmConstitutionSystem.module.jwt.service;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.dos.UserDO;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.dos.UserRoleDO;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.mapper.UserMapper;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
         *根据用户名去查询用户具体信息，先写死
         * 密码是犬小哈，生成的密文
         */
         UserDO userDO = userMapper.findByUsername(username);
        if(Objects.isNull(userDO)){
            throw new UsernameNotFoundException("该用户不存在");
        }
        //用户角色
        List<UserRoleDO> roleDOS = userRoleMapper.selectByUsername(username);
        String[] roleArr = null;
        //转数组
        if(!CollectionUtils.isEmpty(roleDOS)){
            List<String> roles = roleDOS.stream().map(p -> p.getRole()).collect(Collectors.toList());
            roleArr = roles.toArray(new String[roles.size()]);
        }
        return User.withUsername(userDO.getUsername())
                .password(userDO.getPassword())
                .authorities(roleArr) //角色
                .build();
    }
}
