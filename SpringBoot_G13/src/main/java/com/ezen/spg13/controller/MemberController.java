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

import com.ezen.spg13.dto.MemberVO;
import com.ezen.spg13.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	MemberService ms;

	
	@RequestMapping(value="/memberEdit", method=RequestMethod.POST)
	public String mem_edit(@ModelAttribute("dto") @Valid MemberVO membervo, 
			BindingResult result, @RequestParam("pw_check") String pwchk, 
			Model model,	HttpServletRequest request) {
		if(result.getFieldError("pw")!=null) {
			model.addAttribute("message", "비밀번호는 필수 입력사항입니다");
			return "member/memberEditForm";
		}else if(result.getFieldError("name")!=null) {
			model.addAttribute("message",  result.getFieldError("name").getDefaultMessage() );
			return "member/memberEditForm";
		}else if( !membervo.getPw().equals(pwchk)) {
			model.addAttribute("message","비밀번호 확인이 일치하시 않습니다.");
			System.out.println("pwchk");
			return "member/memberEditForm";
		}else { 
			ms.updateMember(membervo);
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", membervo);
			return "redirect:/main";
		}
	}
	
	
	@RequestMapping("/memberEditForm")
	public String mem_edit_form(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if( session.getAttribute("loginUser") == null) return "loginform";
		return "member/memberEditForm";
	}	
	
	
	@RequestMapping(value="/memberJoin", method=RequestMethod.POST)
	public String mem_join(@ModelAttribute("dto") @Valid MemberVO membervo,
			BindingResult result, @RequestParam("re_id") String reid, 
			@RequestParam("pw_check") String pwchk,
			Model model, HttpServletRequest request) {
		if(result.getFieldError("id")!=null) {
			model.addAttribute("message","아이디는 필수 입력사항입니다");
			return "member/memberJoinForm";
		}else if(result.getFieldError("pw")!=null) {
			model.addAttribute("message", "비밀번호는 필수 입력사항입니다");
			return "member/memberJoinForm";
		}else if(result.getFieldError("name")!=null) {
			model.addAttribute("message",  result.getFieldError("name").getDefaultMessage() );
			return "member/memberJoinForm";
		}else if( !membervo.getId().equals(reid)){
			model.addAttribute("message","아이디 중복체크가 되지 않았습니다");
			System.out.println("reid");
			return "member/memberJoinForm";
		}else if( !membervo.getPw().equals(pwchk)) {
			model.addAttribute("message","비밀번호 확인이 일치하시 않습니다.");
			System.out.println("pwchk");
			return "member/memberJoinForm";
		}else { 
			ms.insertMember( membervo.getId(), membervo.getPw(), membervo.getName(),
					membervo.getEmail(), membervo.getPhone1(), membervo.getPhone2(),
					membervo.getPhone3());
			model.addAttribute("message", "사용자가 추가 되었습니다. 로그인 하세요");
			return "loginform";
		}
	}
	
	
	@RequestMapping("/idcheck")
	public String idcheck(Model model, HttpServletRequest request) {
		String id = request.getParameter("id");
		MemberVO svo = ms.getMember(id);
		int result = 0;
		if(svo==null) result=-1; // 아이디 사용가능
		else result  = 1;  // 아이디 사용 불가
		model.addAttribute("result", result);
		model.addAttribute("id", id);
		return "member/idcheck";
	}
	
	
	
	
	@RequestMapping("/memberJoinForm")
	public String join_form(Model model, HttpServletRequest request) {
		return "member/memberJoinForm";
	}
	
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "loginform";
	}
	
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login( @ModelAttribute("dto") @Valid MemberVO membervo, 
			BindingResult result, Model model,	HttpServletRequest request) {
		if (result.hasErrors()) {
			if(result.getFieldError("id")!=null) {
				model.addAttribute("message", "아이디를 입력하세요");
				return "loginform";
			}else if(result.getFieldError("pw")!=null) {
				model.addAttribute("message", result.getFieldError("pw").getDefaultMessage() );
				return "loginform";
			}
		}
		
		MemberVO mvo = ms.getMember( membervo.getId() );
		
		if( mvo == null) {
			model.addAttribute("message", "아이디가 없습니다");
			return "loginform";
		}else if( mvo.getPw() == null ) {
			model.addAttribute("message", "회원정보 오류입니다. 관리자에게 문의하세요");
			return "loginform";
		}else if( !mvo.getPw().equals( membervo.getPw() ) ) {
			model.addAttribute("message", "비밀번호가 맞지 않습니다");
			return "loginform";
		}else {
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", mvo );
			return "redirect:/main";
		}
	}
	
	
	
	@RequestMapping("/")
	public String index(HttpServletRequest request) {
		return "loginform";
	}
	
}
