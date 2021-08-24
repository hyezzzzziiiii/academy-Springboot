package com.ezen.spm01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.spm01.dto.ProductVO;
import com.ezen.spm01.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	ProductService ps;
	
	
	@RequestMapping("productDetail")
	public String product_detail(Model model,  @RequestParam("pseq") String pseq) {
		ProductVO pd = ps.getProduct(pseq);
		model.addAttribute("productVO", pd);
		return "product/productDetail";
	}
		
	
	@RequestMapping("catagory")
	public ModelAndView catagory(Model model, @RequestParam("kind") String kind) {
		List<ProductVO> list = null;
		list = ps.getKindList(kind);
		ModelAndView mav = new ModelAndView();
		mav.addObject("productKindList", list);
		mav.setViewName("product/productKind");
		return mav;  // 동시에 저장내용들고, 페이지로 이동
	}
	
	
	
	@RequestMapping("/")
	public String controll(Model model) {
		String url="index";
		
		List<ProductVO> nlist = ps.getNewList();
		List<ProductVO> blist = ps.getBestList();
		
		model.addAttribute("newProductList", nlist);
		model.addAttribute("bestProductList", blist);		
		return url;		
	}

}
