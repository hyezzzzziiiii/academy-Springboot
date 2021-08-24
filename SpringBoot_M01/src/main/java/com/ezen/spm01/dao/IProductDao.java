package com.ezen.spm01.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.spm01.dto.ProductVO;

@Mapper
public interface IProductDao {

	public List<ProductVO> getNewList();
	public List<ProductVO> getBestList();
	public List<ProductVO> getKindList(String kind);
	public ProductVO getProduct(String pseq);
}
