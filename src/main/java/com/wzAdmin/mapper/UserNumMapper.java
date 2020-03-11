package com.wzAdmin.mapper;

import com.wzAdmin.model.LoginNum;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface UserNumMapper {
    @Insert("insert into user_live_num(userid,num) values (#{userid},num+1) ")
    int addNum(String userid);

    @Select("select num from user_live_num where userid={#userid}")
    int selectNumByUid(String userid);

    @Update("update user_live_num set num = num+ 1 where userid=#{userid} ")
    int updateNum(String userid);

    @Select("select userid,num from user_live_num order by num desc  limit 10")
    List<Map<String,String>> selectNum();
}
