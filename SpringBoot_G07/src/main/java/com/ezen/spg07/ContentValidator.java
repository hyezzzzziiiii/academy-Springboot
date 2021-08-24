package com.ezen.spg07;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ContentValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return ContentDto.class.isAssignableFrom(clazz);  // 검증할 객체의 클래스 타입 정보
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ContentDto dto = (ContentDto)target;
		
		// 전달된 멤버변수가 널이거나 비어 있는지에 대한 점검
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "writer", "writer is empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "content is empty.");
		
		// 전달된 특정 멤버변수의 글자수 점검
		String sWriter = dto.getWriter();
		if (sWriter.length() < 3) {
			errors.rejectValue("writer", "writer is too short.");
		}
		
		/*String sWriter = dto.getWriter();
		String sContent = dto.getContent();
		if(sWriter == null || sWriter.trim().isEmpty()) {
			System.out.println("Writer is null or empty");
			errors.rejectValue("writer", "trouble");
		}
		if(sContent == null || sContent.trim().isEmpty()) {
			System.out.println("Content is null or empty");
			errors.rejectValue("content", "trouble");
		}*/
	}

}
