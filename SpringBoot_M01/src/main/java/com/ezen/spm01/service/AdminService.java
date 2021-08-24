package com.ezen.spm01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.spm01.dao.IAdminDao;
import com.ezen.spm01.dto.Paging;
import com.ezen.spm01.dto.ProductVO;

@Service
public class AdminService {

	@Autowired
	IAdminDao adao;
	
	
	
	public int getAllCount(String tableName, String fieldName, String key) {
		return adao.getAllCount(tableName, fieldName, key);
	}
	
	
	public List<ProductVO> listProduct(Paging paging, String key){
		List<ProductVO> list = adao.listProduct( paging, key);
		return list;
	}
	
	
	public int workerCheck(String workId, String workPwd) {
		String pwd = adao.workerCheck(workId);
		int result=0;
		if(workPwd == null) result = -1;
		else if( workPwd.equals(pwd)) result =  1;
		else if( !workPwd.equals(pwd)) result =  0;
		return result;
	}
	
}
