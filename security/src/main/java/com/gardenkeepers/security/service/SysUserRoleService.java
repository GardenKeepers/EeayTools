package com.gardenkeepers.security.service;

import bean.SysUserRole;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gardenkeepers.security.dao.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserRoleService extends ServiceImpl<SysUserRoleMapper, SysUserRole> {
    @Autowired
    private SysUserRoleMapper userRoleMapper;

    public List<SysUserRole> listByUserId(Integer userId) {
        return userRoleMapper.listByUserId(userId);
    }
}

