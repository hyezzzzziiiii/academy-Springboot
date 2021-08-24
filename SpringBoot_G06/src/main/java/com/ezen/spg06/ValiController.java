package com.ezen.spg06;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ValiController {
	
	@RequestMapping("/")
    public String insert1() {
    	return "createPage";       
    }
	
	@RequestMapping("/create")
	public String insert2( @ModelAttribute("dto") ContentDto contentDto, 
			BindingResult result) {
		// BindingResult : 키값(제목)과 밸류값(내용) 으로 구성된 오류 내용 저장 클래스
		// 위 클래스 형태의 레퍼런스 변수를 선언하고 시작
		
		ContentValidator validator = new ContentValidator();
		validator.validate(contentDto, result);
		
		if (result.hasErrors()) 
    		return "createPage";
			// model.addAttribute("dto", contentDto );
		else 
			return "createDonePage";
	}
}
