package com.ezen.spm01.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.spm01.dto.CartVO;
import com.ezen.spm01.dto.MemberVO;
import com.ezen.spm01.service.CartService;

@Controller
public class CartController {

	@Autowired
	CartService cs;
	
	
	@RequestMapping("cartDelete")
	public String cart_delete(Model model, @RequestParam("cseq") String[] cseqArr) {
		for(String cseq : cseqArr) {
			cs.deleteCart(cseq);
		}
		return "redirect:/cartList";
	}
	
	
	
	@RequestMapping("cartList")
	public String cart_list(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("loginUser");
		if( mvo == null) {
			return "member/login";
		}else {
			 List<CartVO> list = cs.listCart(mvo.getId());
			 int totalPrice = 0;
			 for(CartVO cvo : list)
					totalPrice += cvo.getPrice2() * cvo.getQuantity(); 
			 model.addAttribute("cartList", list);
			 model.addAttribute("totalPrice", totalPrice);
		}
		return "mypage/cartList";
	}
	
	@RequestMapping("cartInsert")
	public String cart_insert(Model model, HttpServletRequest request, 
			@RequestParam("pseq") int pseq, @RequestParam("quantity") int quantity) {
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("loginUser");
		if( mvo == null) {
			return "member/login";
		}else {
			CartVO cvo = new CartVO();
			cvo.setId(mvo.getId());
			cvo.setPseq(pseq); //Integer.parseInt(request.getParameter("pseq"))
			cvo.setQuantity(quantity);
			cs.insertCart(cvo);
		}
		return "redirect:/cartList";
	}
	
}
