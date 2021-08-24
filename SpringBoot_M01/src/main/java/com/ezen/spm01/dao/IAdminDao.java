package com.ezen.spm01.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.spm01.dto.Paging;
import com.ezen.spm01.dto.ProductVO;

@Mapper
public interface IAdminDao {

	public String workerCheck(String workId);
	public List<ProductVO> listProduct(Paging paging,  String key);
	public int getAllCount(String tableName, String fieldName, String key);
	
}
