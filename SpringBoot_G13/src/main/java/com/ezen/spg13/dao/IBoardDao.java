package com.ezen.spg13.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.spg13.dto.BoardVO;
import com.ezen.spg13.dto.Paging;
import com.ezen.spg13.dto.ReplyVO;

@Mapper
public interface IBoardDao {

	public int getAllCount();
	public List<BoardVO> selectAll(Paging paging);
	public int increamentReadcount(int num);
	public BoardVO getBoard(int num);
	public List<ReplyVO> selectReply(int num);
	public int getCountReply( int num );
	public int insertReply(ReplyVO replyvo);
	
	public int updateBoard(BoardVO boardvo);
	
	public int deleteReplyAll(int num);
	public int deleteBoard(int num);
	
	public int insertBoard(BoardVO boardvo);
	
	public int deleteReply(int num);
}


