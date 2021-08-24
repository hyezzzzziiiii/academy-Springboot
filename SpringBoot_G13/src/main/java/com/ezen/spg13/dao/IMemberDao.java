package com.ezen.spg13.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.spg13.dto.MemberVO;

@Mapper
public interface IMemberDao {

	public MemberVO getMember(String id);
	public int updateMember(MemberVO mvo);
	public int insertMember(String id, String pw, String name, String email, 
											String phone1, String phone2, String phone3);
	public int deleteMember(String id);
	
}
