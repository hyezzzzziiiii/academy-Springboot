package com.ezen.spg11.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.spg11.dto.UserDto;

@Mapper
public interface IUserDao {
	List<UserDto> list();
}
