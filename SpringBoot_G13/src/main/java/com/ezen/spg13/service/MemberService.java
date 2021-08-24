package com.ezen.spg13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.spg13.dao.IMemberDao;
import com.ezen.spg13.dto.MemberVO;

@Service
public class MemberService {

	@Autowired
	IMemberDao mdao;
	
	
	public int updateMember(MemberVO mvo) {
		return mdao.updateMember(mvo);
	}
	
	
	
	public int insertMember( String id, String pw, String name, String email, 
			String phone1, String phone2, String phone3 ) {
		int result = mdao.insertMember(id, pw, name, email, phone1, phone2, phone3);
		//System.out.println(result);
		return result;
	}
	
	
	
	public MemberVO getMember( String id ) {
		MemberVO mvo = mdao.getMember(id);
		return mvo;
	}
}
