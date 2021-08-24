package com.ezen.spm01.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.spm01.dto.MemberVO;
import com.ezen.spm01.dto.QnaVO;
import com.ezen.spm01.service.QnaService;

@Controller
public class QnaController {

	@Autowired
	QnaService qs;
	
	
	
	@RequestMapping("qnaWrite")
	public ModelAndView qna_write( @ModelAttribute("dto") @Valid QnaVO qnavo,
			BindingResult result, Model model ,  HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(result.getFieldError("subject") != null ) {
			model.addAttribute("message", "제목을 입력하세요");
			mav.setViewName("qna/qnaWrite");
			return mav;
		}else if(result.getFieldError("content") != null ) {
			model.addAttribute("message", "내용을 입력하세요");
			mav.setViewName("qna/qnaWrite");
			return mav;
		}
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO) session.getAttribute("loginUser");
	    if (mvo == null) mav.setViewName("member/login");
	    else {
	    	qnavo.setId(mvo.getId());
	    	qs.insertQna(qnavo);
	    }
		mav.setViewName("redirect:/qnaList");
		return mav;
	}
	
	
	
	
	
	@RequestMapping("qnaWriteForm")
	public String qna_writre_form(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
	    MemberVO mvo= (MemberVO) session.getAttribute("loginUser");    	    
	    if (mvo == null) return "member/login";
	    return "qna/qnaWrite";
	}
	
	
	
	
	
	
	@RequestMapping("qnaView")
	public ModelAndView qna_view(Model model, HttpServletRequest request,
			@RequestParam("qseq") int qseq) {
		HttpSession session = request.getSession();
	    MemberVO mvo = (MemberVO) session.getAttribute("loginUser");
	    ModelAndView mav = new ModelAndView();
		if (mvo == null) mav.setViewName("member/login");
		else {
			QnaVO qvo = qs.getQna(qseq);
			mav.addObject("qnaVO", qvo);
			mav.setViewName("qna/qnaView");
		}
		return mav;
	}
	
	
	
	@RequestMapping("qnaList")
	public ModelAndView qna_list(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
	    MemberVO mvo = (MemberVO) session.getAttribute("loginUser");
	    ModelAndView mav = new ModelAndView();
	    if (mvo == null) mav.setViewName("member/login");
	    else {
	    	List<QnaVO> list = qs.listQna(mvo.getId());
	    	mav.addObject("qnaList", list);
	    	mav.setViewName("qna/qnaList");
	    }
		return mav;
	}
	
}
