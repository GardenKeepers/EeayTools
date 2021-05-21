package com.gardenkeepers.security.service;

import bean.SysRole;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gardenkeepers.security.dao.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleService extends ServiceImpl<SysRoleMapper, SysRole> {
    @Autowired
    private SysRoleMapper roleMapper;

    public SysRole selectByRoleId(Integer id) {
        return roleMapper.selectByRoleId(id);
    }
}

