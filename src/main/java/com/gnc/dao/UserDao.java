package com.gnc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gnc.dto.LoginDto;

@Mapper
public interface UserDao {
	// 로그인
    public String getUserAccount(@Param("userId")String userId,@Param("userPw") String userPw);
    
    
}
