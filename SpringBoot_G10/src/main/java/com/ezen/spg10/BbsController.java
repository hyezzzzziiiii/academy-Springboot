package com.ezen.spg10;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.spg10.dao.BbsDao;

@Controller
public class BbsController {

	@Autowired
	BbsDao bdao;
	
	@RequestMapping("/")
	public String root(Model model) {
		List<BbsDto> list = bdao.list();
		model.addAttribute("list", list);
		return "list";
	}
	
	@RequestMapping("writeForm")
	public String writeForm(Model model) {
		
		return "writeForm";
	}
	
	@RequestMapping("write")
	public String write(BbsDto bdto, Model model) {
		bdao.write(bdto);
		return "redirect:/";
	}
	
	
	@RequestMapping("view")
	public String view(Model model, @RequestParam("id") String id) {
		BbsDto bdto = bdao.view(id);
		model.addAttribute("dto", bdto);
		return "view";
	}
	
	@RequestMapping("delete")
	public String delete(Model model, @RequestParam("id") String id) {
		bdao.delete(id);
		return "redirect:/";
	}
}
