package com.ezen.spg02;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JspController {

	@RequestMapping("/")
	public @ResponseBody String root() {
		
		return "JSP in Gradle~!!";
		//@RequestMapping 값이 "/" 이고 리턴값이 "main" 이라면
		// http://localhost:8070/ 의 결과는 main.jsp  이겠지만(별도 경로 설정 및 폴더 생성 필요)
		// 함수 이름에 @ResponseBody 가 있으면 리턴되는 문자열이 웹 브라우져에 직접 쓰여지게 됩니다
	}
	
	@RequestMapping("/test1")     // localhost:8070/test1
	public String test1() {
        return "main";          // 실제 호출 될 webapp/WEB-INF/views/main.jsp       
    }
	
	@RequestMapping("/test2")    // localhost:8070/test2
    public String test2() {
        return "sub/sub";      // 실제 호출 될 /WEB-INF/views/sub/sub.jsp       
    }
}
