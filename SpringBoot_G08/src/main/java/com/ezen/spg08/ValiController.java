package com.ezen.spg08;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ValiController {
	
	 @RequestMapping("/")
	 public String root() throws Exception{
		 return "createPage";      
	 }
	 
	 @RequestMapping("/create")
	 public String insert( @ModelAttribute("dto") @Valid ContentDto contentDto ,	 
			 BindingResult result , Model model) {
		 String page = "createDonePage";
		 
		 if (result.hasErrors()) {  // 어느 멤버변수인지 모르지만, 에러내용이 존재한다면
			 if( result.getFieldError("writer") != null) //writer 멤버변수에 대한 내용이 존재한다면
				 //System.out.println("1:"+result.getFieldError("writer").getDefaultMessage());
				 //model.addAttribute("msg", result.getFieldError("writer").getDefaultMessage());
				 model.addAttribute("msg", "Writer 입력은 비어있거나 3글자 미만이면 안돼요~~!!");
			 else if( result.getFieldError("content") != null) //writer 멤버변수에 대한 내용이 존재한다면
				 //System.out.println("2:"+result.getFieldError("content").getDefaultMessage());
				 model.addAttribute("msg", "Content 입력은 비어있으면 안돼요~~!!");
			 page = "createPage";
		 }
		 
		 return page;
	 }
}
