package com.ezen.spm01.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.spm01.dto.QnaVO;

@Mapper
public interface IQnaDao {

	public List<QnaVO> listQna(String id);
	public QnaVO getQna(int qseq);
	public void insertQna(QnaVO qvo);
}
