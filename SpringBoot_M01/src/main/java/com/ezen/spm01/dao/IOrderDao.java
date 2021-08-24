package com.ezen.spm01.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.spm01.dto.CartVO;
import com.ezen.spm01.dto.OrderVO;

@Mapper
public interface IOrderDao {

	public int insertOders(String id);
	public int LookupMaxOseq();
	public int insertOderDetail(CartVO cvo, int oseq);
	public int updateCart(int cseq);
	public List<OrderVO> listOrderById(String id, String result, int oseq);
	public List<Integer> selectSeqOrderIng(String id);
	public List<Integer> oseqListAll(String id);
}
