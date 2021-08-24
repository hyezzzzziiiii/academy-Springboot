package com.ezen.spg14.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.spg14.dao.IBoardDao;
import com.ezen.spg14.dto.BoardVO;
import com.ezen.spg14.dto.Paging;
import com.ezen.spg14.dto.ReplyVO;

@Service
public class BoardService {

	@Autowired
	IBoardDao bdao;
	
	
	public void deleteReply(int num) {
		bdao.deleteReply(num);
	}
	
	
	public void insertBoard(BoardVO boardvo) {
		bdao.insertBoard(boardvo);
	}
	
	
	public void removeBoard(int num) {
		bdao.deleteReplyAll(num);
		bdao.deleteBoard(num);
	}
	
	
	
	
	public void updateBoard(BoardVO boardvo) {
		bdao.updateBoard(boardvo);
	}
	
	
	
	public BoardVO getBoard(int num) {
		return bdao.getBoard(num);
	}
	
	
	public List<ReplyVO> selectReply( int num ){
		//List<ReplyDto> list = bdao.selectReply(num);
		//return list;
		return bdao.selectReply(num);
	}
	
	
	
	
	
	public BoardVO readBoard( int num ){ 
		bdao.increamentReadcount(num);
		return bdao.getBoard(num);
	}
	
	
	
	
	public int getAllCount() {
		return bdao.getAllCount();
	}
	
	
	public int insertReply(ReplyVO replyvo) {
		return bdao.insertReply(replyvo);
	}
	
	
	public List<BoardVO> selectAll(Paging paging){
		List<BoardVO> list = bdao.selectAll(paging);
		
		// 리스트를 이용한 반복 실행문을 실행합니다
		for( BoardVO bvo : list) {
			// 각 게시물의 댓글 갯수를 조회하는 메서드를 호출하여 갯수를 얻습니다
			int count = 0;
			count = bdao.getCountReply( bvo.getNum() );
			// 그 갯수를 현재 게시물의  Replycnt 변수에 저장합니다
			bvo.setReplycnt(count);
		}
		return list;
	}
	
}
