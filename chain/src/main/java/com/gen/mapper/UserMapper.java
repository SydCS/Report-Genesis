package com.gen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gen.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT NAME FROM role WHERE id IN (SELECT rid FROM user_role WHERE uid=(SELECT id FROM USER WHERE NAME=#{principal}))")
    List<String> getUserRole(@Param("principal") String principal);

    @Select({
            "<script>",
            "select info FROM permissions WHERE id IN ",
            "(SELECT pid FROM role_ps WHERE rid IN (",
            "SELECT id FROM role WHERE NAME IN ",
            "<foreach collection='roles' item='name' open='(' separator=',' close=')'>",
            "#{name}",
            "</foreach>",
            "))",
            "</script>"
    })
    List<String> getUserPermission(@Param("roles") List<String> roles);

}
