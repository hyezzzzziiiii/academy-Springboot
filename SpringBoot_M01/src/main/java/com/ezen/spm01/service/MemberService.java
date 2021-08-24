package com.ezen.spm01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.spm01.dao.IMemberDao;
import com.ezen.spm01.dto.AddressVO;
import com.ezen.spm01.dto.MemberVO;

@Service
public class MemberService {

	@Autowired
	IMemberDao mdao;

	public int updateMember(MemberVO mvo) {
		return mdao.updateMember(mvo);
	}
	
	public int insertMember(MemberVO mvo) {
		return mdao.insertMember( mvo);
	}
	
	public List<AddressVO> selectAddressByDong(String dong){
		return mdao.selectAddressByDong(dong);
	}
	
	public MemberVO getMember(String id) {
		return mdao.getMember(id);
	}
}
