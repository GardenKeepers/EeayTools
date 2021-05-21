package com.gardenkeepers.security.dao;

import bean.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    @Select("SELECT * FROM sys_role WHERE id = #{id}")
    SysRole selectByRoleId(Integer id);
}

