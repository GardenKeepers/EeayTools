package com.gardenkeepers.security.dao;

import bean.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    @Select("SELECT * FROM sys_user WHERE id = #{id}")
    SysUser selectByUserId(Integer id);

    @Select("SELECT * FROM sys_user WHERE account = #{name}")
    SysUser selectByAccount(String name);
}

