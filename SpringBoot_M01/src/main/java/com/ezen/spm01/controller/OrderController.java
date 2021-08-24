package com.ezen.spm01.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.spm01.dto.CartVO;
import com.ezen.spm01.dto.MemberVO;
import com.ezen.spm01.dto.OrderVO;
import com.ezen.spm01.service.CartService;
import com.ezen.spm01.service.OrderService;

@Controller
public class OrderController {

	 @Autowired
	 OrderService os;
	
	 @Autowired
	 CartService cs;
	 
	 
	 
	 @RequestMapping("orderAll")  // 총주문내역
		public ModelAndView order_all(Model model, HttpServletRequest request) {
			HttpSession session = request.getSession();
			MemberVO mvo = (MemberVO) session.getAttribute("loginUser");
			ModelAndView mav = new ModelAndView();
			if (mvo == null) mav.setViewName("member/login");
			else {
				List<Integer> oseqList	= os.oseqListAll(mvo.getId());
				ArrayList<OrderVO> orderList = new ArrayList<OrderVO>();
				for (int oseq : oseqList) {
					List<OrderVO> orderListAll = os.listOrderById(mvo.getId(), "%", oseq);
					OrderVO ovo = orderListAll.get(0);
					ovo.setPname(ovo.getPname() + " 포함 " + orderListAll.size() + " 건");
					int totalPrice = 0;
					for (OrderVO ovop : orderListAll) 
				          totalPrice += ovop.getPrice2() * ovop.getQuantity();
					orderList.add(ovo);
				}			
				mav.addObject("title", "총 주문 내역");
				mav.addObject("orderList", orderList);
				mav.setViewName("mypage/mypage");
			}
			return mav;
		}
	 
	 
	 
	 
	 
	 @RequestMapping("myPage")  // 진행중인 주문 내역
		public String mypage(Model model, HttpServletRequest request) {
			HttpSession session = request.getSession();
		    MemberVO mvo = (MemberVO) session.getAttribute("loginUser");
		    if(mvo==null) return "member/login";
		    else {	
		    	ArrayList<OrderVO> orderList = new ArrayList<OrderVO>();
		    	List<Integer> oseqList = os.selectSeqOrderIng(mvo.getId());
		    	for( int oseq : oseqList ) {
		    		List<OrderVO> orderListIng	= os.listOrderById(mvo.getId(), "1", oseq);
		    		OrderVO ovo = orderListIng.get(0);
		    		ovo.setPname(ovo.getPname() + " 포함 " + orderListIng.size() + " 건");
		    		int totalPrice = 0;
		    		for (OrderVO ovo1 : orderListIng) 
			              totalPrice += ovo1.getPrice2() * ovo1.getQuantity();
		    		ovo.setPrice2(totalPrice);
		            orderList.add(ovo);
		    	}
		    	model.addAttribute("title", "진행 중인 주문 내역");
		    	model.addAttribute("orderList", orderList);
		    }
		    return "mypage/mypage";
		}
	 
	 
	 
	 
	 
	 @RequestMapping("orderList")
		public String order_list(Model model, HttpServletRequest request, 
				@RequestParam("oseq") int oseq ) {
			HttpSession session = request.getSession();
		    MemberVO mvo = (MemberVO) session.getAttribute("loginUser");
		    if (mvo == null) {
		    	return "member/login";
		    }else {
		    	List<OrderVO> list	= os.listOrderById(mvo.getId(), "1", oseq);
		    	int totalPrice = 0;
		    	for(OrderVO ovo : list)
			          totalPrice+=ovo.getPrice2() * ovo.getQuantity();
		    	model.addAttribute("orderList", list);
		    	model.addAttribute("totalPrice", totalPrice);
		    }
			return "mypage/orderList";
		}
	 
	 
	 
	 @RequestMapping("orderInsert")
	 public String order_insert(Model model, HttpServletRequest request) {
		 HttpSession session = request.getSession();
		 MemberVO mvo = (MemberVO) session.getAttribute("loginUser");
		 int oseq = 0;
		 if (mvo == null) {
			    	return "member/login";
		 }else {
			  	List<CartVO> cartList = cs.listCart(mvo.getId());
			   	oseq = os.insertOrder(cartList, mvo.getId());
		 }
			return "redirect:/orderList?oseq="+oseq;
	  }
}
