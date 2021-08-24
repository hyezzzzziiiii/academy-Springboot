package com.ezen.spg13.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.spg13.dto.BoardVO;
import com.ezen.spg13.dto.Paging;
import com.ezen.spg13.dto.ReplyVO;
import com.ezen.spg13.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	BoardService bs;
	
	@RequestMapping("boadViewAfterReply")
	public String boadViewAfterReply(@RequestParam("num") int num, Model model) {
		model.addAttribute("board", bs.getBoard( num ) );
		model.addAttribute("ReplyList", bs.selectReply( num ) );
		return "board/boardView";
	}
	
	@RequestMapping("deleteReply")
	public String delete_reply(Model model, HttpServletRequest request) {
		int num = Integer.parseInt(request.getParameter("num"));
		int boardnum = Integer.parseInt(request.getParameter("boardnum"));
		bs.deleteReply(num);
		return "redirect:boadViewAfterReply?num=" + boardnum;
	}
	
	
	
	
	
	@RequestMapping(value="boardWrite", method = RequestMethod.POST)
	public String board_write(@ModelAttribute("dto") @Valid BoardVO boardvo, 
			BindingResult result, Model model,	HttpServletRequest request) {
		
		if (result.hasErrors()) {
			if(result.getFieldError("pass")!=null)
				model.addAttribute("message", result.getFieldError("pass").getDefaultMessage() );
			else if(result.getFieldError("email")!=null)
				model.addAttribute("message", result.getFieldError("email").getDefaultMessage() );
			else if(result.getFieldError("title")!=null)
				model.addAttribute("message", result.getFieldError("title").getDefaultMessage() );
			else if(result.getFieldError("content")!=null)
				model.addAttribute("message", result.getFieldError("content").getDefaultMessage() );
			
			return "board/boardWriteForm";
		}
		bs.insertBoard(boardvo);
		
		return "redirect:/main";
	}
	
	
	@RequestMapping("/boardWriteForm")
	public String write_form(Model model, HttpServletRequest request) {
		String url = "board/boardWriteForm";
		HttpSession session = request.getSession();
		if( session.getAttribute("loginUser") == null)	url="loginform";		
		return url;
	}
	
	
	
	
	
	@RequestMapping("boardDelete")
	public String board_delete(Model model, HttpServletRequest request) {
		int num = Integer.parseInt(request.getParameter("num"));

		//bdao.deleteBoard(num);
		//bdao.deleteReply(num);
		bs.removeBoard(num);
		
		return "redirect:/main";
	}	
	
	
	
	@RequestMapping("boardDeleteForm")
	public String board_delete_form(Model model, HttpServletRequest request) {
		String num = request.getParameter("num");
		model.addAttribute("num", num);
		return "board/boardCheckPassForm";
	}
	
	
	
	
	
	@RequestMapping(value="boardUpdate", method = RequestMethod.POST)
	public String board_update(@ModelAttribute("board") @Valid BoardVO boardvo, 
			BindingResult result, Model model,	HttpServletRequest request) {
				
		if (result.hasErrors()) {
			if(result.getFieldError("pass")!=null)
				model.addAttribute("message", result.getFieldError("pass").getDefaultMessage() );
			else if(result.getFieldError("email")!=null)
				model.addAttribute("message", result.getFieldError("email").getDefaultMessage() );
			else if(result.getFieldError("title")!=null)
				model.addAttribute("message", result.getFieldError("title").getDefaultMessage() );
			else if(result.getFieldError("content")!=null)
				model.addAttribute("message", result.getFieldError("content").getDefaultMessage() );
			
			return "board/boardWriteForm";
		}
		bs.updateBoard(boardvo);
		return "redirect:/boardView?num=" + boardvo.getNum();
	}
	
	
	
	@RequestMapping("/boardUpdateForm")
	public String board_update_form(Model model, HttpServletRequest request) {
		int num = Integer.parseInt(request.getParameter("num") );
		model.addAttribute("board", bs.getBoard(num));
		return "board/boardUpdateForm";
	}
	
	@RequestMapping("/boardEdit")
	public String board_edit(Model model, HttpServletRequest request) {
		int num = Integer.parseInt( request.getParameter("num") );
		String pass = request.getParameter("pass");
		
		BoardVO sb = bs.getBoard(num);
		model.addAttribute("num", num);
		if(pass.equals(sb.getPass()) ) return "board/boardCheckPass";
		else {
			model.addAttribute("message", "비밀번호가 맞지 않습니다. 확인해주세요");
			return "board/boardCheckPassForm";
		}
	}
	
	
	
	
	
	
	@RequestMapping("/boardEditForm")
	public String board_edit_form(Model model, HttpServletRequest request) {
		String num = request.getParameter("num");
		model.addAttribute("num", num);
		return "board/boardCheckPassForm";	
	}
	
	
	
		
	@RequestMapping("addReply")
	public String add_reply( @Valid ReplyVO replyvo, BindingResult result, Model model) {
		if( result.getFieldError("content")!=null ) {
			model.addAttribute("message", "내용을 입력하세요");
			return "redirect:boardView?num=" + replyvo.getBoardnum();
		}
		bs.insertReply(replyvo);
		
		return "redirect:boadViewAfterReply?num=" + replyvo.getBoardnum();
	}
	
	
	
	
	@RequestMapping("/boardView")
	public String board_view(Model model, HttpServletRequest request) {
		int num = Integer.parseInt(request.getParameter("num"));
		model.addAttribute("board", bs.readBoard( num ) );
		//List<ReplyVO> list = bs.selectReply( num );
		//model.addAttribute("ReplyList",list );
		model.addAttribute("ReplyList", bs.selectReply( num ) );
		return "board/boardView";
	}
	
	

	@RequestMapping("/main")
	public String go_main(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		int page = 1;  
		if( session.getAttribute("loginUser") == null)	
			return "loginform";
		else {
			if( request.getParameter("page") != null ) {
				page = Integer.parseInt(request.getParameter("page"));
				session.setAttribute("page", page);
			} else if( session.getAttribute("page")!= null ) {
				page = (int) session.getAttribute("page");
			} else {
				page = 1;
				session.removeAttribute("page");
			}
			Paging paging = new Paging();
			paging.setPage(page);
			int count = bs.getAllCount();
			paging.setTotalCount(count);
			paging.paging();
			model.addAttribute("boardList",	bs.selectAll(paging) );
			model.addAttribute("paging", paging);
		}
		return "main";
	}
}
