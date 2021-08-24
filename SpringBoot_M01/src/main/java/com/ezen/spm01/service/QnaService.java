package com.ezen.spm01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.spm01.dao.IQnaDao;
import com.ezen.spm01.dto.QnaVO;

@Service
public class QnaService {
	
	@Autowired
	IQnaDao qdao;
	
	public void insertQna(QnaVO qvo) {
		qdao.insertQna(qvo);
	}
	
	public QnaVO getQna(int qseq) {
		QnaVO qvo = qdao.getQna(qseq);
		return qvo;
	}
	
	public List<QnaVO> listQna(String id){
		List<QnaVO> list = qdao.listQna(id);
		return list;
	}
}
