package com.ezen.spm01.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.spm01.dto.Paging;
import com.ezen.spm01.dto.ProductVO;
import com.ezen.spm01.service.AdminService;
import com.ezen.spm01.service.ProductService;
import com.ezen.spm01.service.QnaService;

@Controller
public class AdminController {

	@Autowired
	AdminService as;
	
	@Autowired
	ProductService ps;
	
	@Autowired
	QnaService qs;
	
	@Autowired
	ServletContext context;
	
	
	@RequestMapping("productList")
	public ModelAndView product_list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("workId");
		int page=1;
		if(id==null)
			mav.setViewName("redirect:/admin");
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
			
			
			
			if( request.getParameter("first")!=null ) {
				session.removeAttribute("page");
				session.removeAttribute("key");
			}
			String key = "";
			if( request.getParameter("key") != null ) {
				key = request.getParameter("key");
				session.setAttribute("key", key);
			} else if( session.getAttribute("key")!= null ) {
				key = (String)session.getAttribute("key");
			} else {
				session.removeAttribute("key");
				key = "";
			} 
			
			
			Paging paging = new Paging();
			paging.setPage(page);
			int count = as.getAllCount("product", "name", key);
			paging.setTotalCount(count);
			paging.paging();
			
			List<ProductVO> productList = as.listProduct(paging, key);
			mav.addObject("productList", productList);
			mav.addObject("paging", paging);
			mav.setViewName("admin/product/productList");
		}
		return mav;
	}
	
	
	
	@RequestMapping("adminLogin")
	public ModelAndView admin_login(Model model, HttpServletRequest request,
			@RequestParam("workId") String workId,
			@RequestParam("workPwd") String workPwd) {
		ModelAndView mav = new ModelAndView();
		String msg = "";
		int result = as.workerCheck(workId, workPwd);
		if(result == 1) {
    		HttpSession session = request.getSession();
    		session.setAttribute("workId", workId);
    		mav.setViewName("redirect:/productList");
	    } else if (result == 0) {
	        	msg = "비밀번호를 확인하세요.";
	        	mav.addObject("message", msg);
	        	mav.setViewName("admin/adminLoginForm");
	    } else if (result == -1) {
	    		msg = "아이디를 확인하세요.";
	    		mav.addObject("message", msg);
	    		mav.setViewName("admin/adminLoginForm");
	    }		
		return mav;
	}
	
	
	
	@RequestMapping("/admin")
	public String admin() {
		return "admin/adminLoginForm";
	}
}
