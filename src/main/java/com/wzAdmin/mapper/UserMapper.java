package com.wzAdmin.mapper;


import com.wzAdmin.model.SystemUser;
import com.wzAdmin.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface UserMapper {
    @Insert("INSERT INTO User (id,birthday,password,name,role,status,email,url,ucreate,sex) VALUES (#{user.id},#{user.birthday},#{user.password},#{user.name},#{user.role},#{user.status},#{user.email},#{user.url}, #{user.ucreate},#{user.sex})")
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

    @Select("select count(*) from User where ucreate = #{ucreate}")
    int selectUserNumByData(@Param(value = "ucreate")String ucreate);

    @Select("select count(*) from User")
    int selectUserNum();

    @Update("update User set status = 2 where name=#{name}")
    int loginUser(String name);

    @Update("update User set status = 1 where id=#{id}")
    int logoutUser(String id);

    @Select("select count(*) from User where sex = 1")
    int selectUserSex();

    @Select("select count(*) from User where sex = #{sex} and ucreate = #{ucreate}")
    int selectUserDataSex(String ucreate,String sex);

    @Select("select * from User")
    List<SystemUser> selectAllUser();
}
