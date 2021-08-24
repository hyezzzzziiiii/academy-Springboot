package com.ezen.spm01.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.spm01.dto.AddressVO;
import com.ezen.spm01.dto.MemberVO;

@Mapper
public interface IMemberDao {

	public MemberVO getMember(String id);
	public List<AddressVO> selectAddressByDong(String dong);
	public int insertMember(MemberVO mvo);
	public int updateMember(MemberVO mvo);
}
