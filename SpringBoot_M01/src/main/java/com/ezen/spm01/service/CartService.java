package com.ezen.spm01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.spm01.dao.ICartDao;
import com.ezen.spm01.dto.CartVO;

@Service
public class CartService {

	@Autowired
	ICartDao cdao;
	
	public void deleteCart(String cseq) {
		cdao.deleteCart(cseq);
	}
	
	public List<CartVO> listCart(String id){
		return cdao.listCart(id);
	}
	public  void insertCart(CartVO cvo) {
		cdao.insertCart(cvo);
	}
}
