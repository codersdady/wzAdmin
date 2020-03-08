package com.wzAdmin.mapper;


import com.wzAdmin.model.SystemUser;
import com.wzAdmin.model.User;
import org.apache.ibatis.annotations.*;


public interface UserMapper {
    @Insert("INSERT INTO User (id,birthday,password,name,role,status,email,url) VALUES (#{user.id},#{user.birthday},#{user.password},#{user.name},#{user.role},#{user.status},#{user.email},#{user.url})")
    int addUser(@Param(value = "user")SystemUser systemUser);

    @Select("select count(*) from User where name = #{name} ")
    int isExist(@Param(value = "name")String name);

    @Select("select * from User where name =#{name}")
    SystemUser selectUserByName(@Param(value = "name")String name);

    @Select("select * from User where id = #{id}")
    SystemUser selectUserById(@Param(value = "id")String id);

    @Select("select url from User where id = #{id}")
    String selectImgById(@Param(value = "id")String id);

    @Update("UPDATE User SET name = #{user.name}, password=#{user.password}, birthday=#{user.birthday}, email=#{user.email} where id=#{user.id}")
    int updateUserById(@Param(value = "user")SystemUser systemUser);
}
