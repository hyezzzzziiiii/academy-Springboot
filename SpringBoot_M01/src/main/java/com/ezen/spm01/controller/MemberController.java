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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.spm01.dto.AddressVO;
import com.ezen.spm01.dto.MemberVO;
import com.ezen.spm01.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	MemberService ms;
	
	
	@RequestMapping(value = "memberUpdate",  method=RequestMethod.POST)
	public String memberUpdate(@ModelAttribute("member") @Valid MemberVO membervo,
			BindingResult result, Model model, HttpServletRequest request) {
		
		model.addAttribute("addr1", request.getParameter("addr1"));
		model.addAttribute("addr2", request.getParameter("addr2"));
		// 필요한 파라미터 공백 및 null 을 체크합니다
		if( result.getFieldError("pwd")!=null) {
			model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
			return "member/memberUpdateForm";
		}else if( result.getFieldError("name")!=null) {
			model.addAttribute("message", result.getFieldError("name").getDefaultMessage());
			return "member/memberUpdateForm";
		}else if( result.getFieldError("email")!=null) {
			model.addAttribute("message", result.getFieldError("email").getDefaultMessage());
			return "member/memberUpdateForm";
		}else if( !request.getParameter("pwdCheck").equals( membervo.getPwd() ) ) {
			model.addAttribute("message", "비밀번호 확인 일치하지 않습니다");
			return "member/joinForm";
		}
				
		membervo.setAddress(request.getParameter("addr1") + " " 
				+ request.getParameter("addr2"));
		ms.updateMember(membervo);
		
		HttpSession session = request.getSession();
		session.setAttribute("loginUser", membervo);
		
		return "redirect:/";
	}
	
	
	
	
	@RequestMapping(value = "memberEditForm")
	public String member_Edit_Form(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("loginUser");
		
		String addr = mvo.getAddress();  // 주소 추출
		int k1 = addr.indexOf(" ");  // 첫번째 공백의 위치 찾음
		int k2 = addr.indexOf(" ", k1+1);  // 첫번째 공백 위치의 다음위치부터 두번째 공백 위치 찾음
		int k3 = addr.indexOf(" ", k2+1);  // 두번째 공백위치 다음 위치부터 세번째 공백 위치 찾음
		// 서울시 마포구 대현동 115-15  세번째 공백 위치  k3값 -> 11 (0부터 시작)
		String addr1 = addr.substring(0, k3); // 맨앞부터 세번째 공백 위치 바로 전까지... 주소 앞부분
		String addr2 = addr.substring(k3+1);  // 세번째 공백 뒷글자부터 맨끝까지...주소 뒷부분
		
		request.setAttribute("member", mvo);
		request.setAttribute("addr1", addr1);
		request.setAttribute("addr2", addr2);
		
		return "member/memberUpdateForm";
	}
	
	
	
	
	@RequestMapping(value = "join", method=RequestMethod.POST)
	public String join(@ModelAttribute("dto") @Valid MemberVO membervo,
			BindingResult result, Model model, HttpServletRequest request) {
		
		if( result.getFieldError("id")!=null) {
			model.addAttribute("message", result.getFieldError("id").getDefaultMessage());
			model.addAttribute("reid", request.getParameter("reid"));
			return "member/joinForm";
		}else if( result.getFieldError("pwd")!=null) {
			model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
			model.addAttribute("reid", request.getParameter("reid"));
			return "member/joinForm";
		}else if( result.getFieldError("name")!=null) {
			model.addAttribute("message", result.getFieldError("name").getDefaultMessage());
			model.addAttribute("reid", request.getParameter("reid"));
			return "member/joinForm";
		}else if( result.getFieldError("email")!=null) {
			model.addAttribute("message", result.getFieldError("email").getDefaultMessage());
			model.addAttribute("reid", request.getParameter("reid"));
			return "member/joinForm";
		}else if( !request.getParameter("reid").equals(membervo.getId()) ) {
			model.addAttribute("message", "아이디 중복체크를 하지 않으셨습니다");
			model.addAttribute("reid", request.getParameter("reid"));
			return "member/joinForm";
		}else if( !request.getParameter("pwdCheck").equals( membervo.getPwd() ) ) {
			model.addAttribute("message", "비밀번호 확인 일치하지 않습니다");
			model.addAttribute("reid", request.getParameter("reid"));
			return "member/joinForm";
		}
		
		membervo.setAddress(request.getParameter("addr1") + " " 
												+ request.getParameter("addr2"));
		ms.insertMember(membervo);
		return "member/login";
	}
	
	
	@RequestMapping("findZipNum")
	public String find_zip(Model model, HttpServletRequest request) {
		String dong = request.getParameter("dong");
		if(dong!=null && dong.trim().equals("")==false){
			List<AddressVO> addressList = ms.selectAddressByDong(dong);
			model.addAttribute("addressList", addressList);
		}
		return "member/findZipNum";
	}
	
	
	
	@RequestMapping("idCheckForm")
	public String id_check_form(Model model, @RequestParam("id") String id) {
		
		MemberVO mvo = ms.getMember(id);
		int result = 0;
		if(mvo == null) result = -1;
		else result = 1;
		model.addAttribute("result", result);
		model.addAttribute("id", id);
		return "member/idcheck";
	}
	
	
	
	@RequestMapping(value="joinForm", method=RequestMethod.POST)
	public String join_form(Model model, HttpServletRequest request) {
		return "member/joinForm";
	}
	
	@RequestMapping("contract")
	public String contract(Model model, HttpServletRequest request) {
		return "member/contract";
	}
	
	
	@RequestMapping("logout")
	public String logout(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("loginUser");
		return "redirect:/";
	}
	
	
	
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@ModelAttribute("dto") @Valid MemberVO membervo,
			BindingResult result, Model model, HttpServletRequest request) {
		
		if( result.getFieldError("id")!=null) {
			model.addAttribute("message", result.getFieldError("id").getDefaultMessage());
			return "member/login";
		}else if( result.getFieldError("pwd")!=null) {
			model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
			return "member/login";
		}
			
		MemberVO mvo = ms.getMember(membervo.getId());
		if( mvo!=null) {
			if(mvo.getPwd()!=null) {
				if(mvo.getPwd().equals(membervo.getPwd() )) {
					HttpSession session = request.getSession();
					session.setAttribute("loginUser", mvo);
					return "redirect:/";
				}else {
					model.addAttribute("message", "비번이 맞지 않습니다");
					return "member/login";
				}
			}else {
				model.addAttribute("message", "비밀번호 오류. 관리자에게 문의하세요");
				return "member/login";
			}
		}else {
			model.addAttribute("message", "ID가 없습니다");
			return "member/login";
		}
	}
	
	@RequestMapping("loginForm")
	public String login_form(Model model, HttpServletRequest request) {
		return "member/login";
	}
}
