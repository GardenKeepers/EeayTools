package com.gardenkeepers.security.service;


import bean.SysRole;
import bean.SysUser;
import bean.SysUserRole;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gardenkeepers.security.dao.SysRoleMapper;
import com.gardenkeepers.security.dao.SysUserMapper;
import com.gardenkeepers.security.dao.SysUserRoleMapper;
import com.gardenkeepers.security.exception.CustomException;
import com.gardenkeepers.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Autowired
    private AuthenticationManager authenticationManager;


    public SysUser selectByUserId(Integer id) {
        return userMapper.selectByUserId(id);
    }

    public SysUser selectByAccount(String name) {
        return userMapper.selectByAccount(name);
    }


    /**
     * 插入一条数据
     */
    @Transactional
    public void insert(SysUser user) {
        //插入到数据库里面
        this.userMapper.insert(user);
        SysUser sysUser = this.userMapper.selectByAccount(user.getAccount());
        //插入到数据库UserRole表
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId(2);
        sysUserRole.setUserId(sysUser.getId());
        userRoleMapper.insert(sysUserRole);
        return;
    }

    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SysUser user = userMapper.selectByAccount(username);
            List<SysUserRole> userRoles = userRoleMapper.listByUserId(user.getId());
            ArrayList<SysRole> roleList = new ArrayList<>();
            for (SysUserRole userRole : userRoles) {
                SysRole role = sysRoleMapper.selectByRoleId(userRole.getRoleId());
                roleList.add(role);
            }
            return jwtTokenProvider.createToken(username, roleList);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}

