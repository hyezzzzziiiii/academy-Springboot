package com.ezen.spg12.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.spg12.dto.BbsDto;

@Mapper
public interface IBbsDao {
	public List<BbsDto> list();
	public BbsDto view(String id);
	public int write(BbsDto bdto);
	//public int write(String writer, String title, String content);
	public int update(BbsDto bdto);
	//public int update(String writer, String title, String content, String id);
	public int delete(String id);
}
