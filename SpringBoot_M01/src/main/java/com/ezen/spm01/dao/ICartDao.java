package com.ezen.spm01.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.spm01.dto.CartVO;

@Mapper
public interface ICartDao {

	public List<CartVO> listCart(String id);
	public  void insertCart(CartVO cvo);
	public void deleteCart(String cseq);
}
